package com.emucoo.restApi.controller.form;

import com.emucoo.common.Constant;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.abilityForm.GetFormInfoIn;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TShopInfo;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.restApi.utils.RedisClusterClient;
import com.emucoo.service.form.FormService;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.service.report.ReportService;
import com.emucoo.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sj on 2018/4/24.
 */
@RestController
@RequestMapping(value = "/api/form")
public class FormController extends AppBaseController {

    @Autowired
    private FormService formService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private FormManageService formManageService;

    @Autowired
    private RedisClusterClient redisClusterClient;

    @PostMapping(value = "checkoutFormInfo")
    public AppResult<FormOut> checkFormInfo(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<FormIn> params) {
        SysUser user = UserTokenManager.getInstance().currUser(userToken);
        FormIn formIn = params.getData();
        checkParam(formIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(formIn.getChecklistID(), "表单id不能为空！");
        checkParam(formIn.getShopID(), "店铺id不能为空！");

        TShopInfo shopInfo = formService.findShopInfo(formIn);
        if(shopInfo == null) {
            return fail(AppExecStatus.FAIL, "该门店不存在！");
        }

        // 因为有可能有用户通过app创建的form机会点，所以key里要有user对信息
        String key = Constant.FORM_BUFFER_PREFIX + ":f" + Long.toString(formIn.getChecklistID()) + ":u" + Long.toString(user.getId()) ;
        FormOut formOut = redisClusterClient.getObject(key, FormOut.class);
        if(formOut == null) {
            formOut = formService.checkoutFormInfo(user, formIn.getChecklistID());
            if (formOut == null) {
                return fail(AppExecStatus.FAIL, "表单不存在或者未启用！");
            }
            redisClusterClient.buffer(key, formOut);
        }

        formOut.setShopName(shopInfo.getShopName());

        TBrandInfo brandInfo = formService.findShopBrand(shopInfo.getBrandId());
        formOut.setBrandName(brandInfo==null?"":brandInfo.getBrandName());

        formOut.setGradeDate(DateUtil.dateToString1(DateUtil.currentDate()));
        return success(formOut);
    }


    @PostMapping(value = "checkinFormResult")
    public AppResult<String> checkFormResult(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<FormIn> params ) {
        SysUser user = UserTokenManager.getInstance().currUser(userToken);

        FormIn formIn = params.getData();
        if(formService.checkinFormResult(user, formIn)){ // 如果有app创建的机会点，要把缓存里的form信息清除
            String key = Constant.FORM_BUFFER_PREFIX + ":f" + Long.toString(formIn.getChecklistID()) + ":u" + Long.toString(user.getId()) ;
            redisClusterClient.delete(key);
        };
        return success("ok");
    }

    @ApiOperation(value = "获取能力模型表单配置")
    @PostMapping(value = "/getAbilityForm")
    public AppResult<AbilityFormMain> getAbilityForm(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<GetFormInfoIn> paramVo) {
        GetFormInfoIn formIn = paramVo.getData();
        checkParam(formIn.getFormID(), "表单id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(userToken);
        AbilityFormMain form = formManageService.getAbilityForm(formIn, user);
        return success(form);
    }

    /**
     * 保存能力模型打表结果
     * @param userToken
     * @param params
     * @return
     */
    @ApiOperation(value = "保存能力模型打表结果并生成报告")
    @PostMapping(value = "saveAbilityFormResult")
    public AppResult<Map> saveAbilityFormResult(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<AbilityFormMain> params) {
        AbilityFormMain formIn = params.getData();
        checkParam(formIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(formIn.getFormID(), "表单id不能为空！");
        checkParam(formIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(userToken);
        Long reportId = formService.saveAbilityFormResult(formIn, user);
        HashMap<String, Long> map = new HashMap<>();
        map.put("reportID", reportId);
        return success(map);
    }



}
