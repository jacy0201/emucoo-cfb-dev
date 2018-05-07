package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
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
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sj on 2018/4/24.
 */
@RestController
@RequestMapping(value = "/api/form")
public class FormController extends AppBaseController {

    private final String FORM_BUFFER_PREFIX = "form:buffer:prefix:";

    @Autowired
    private FormService formService;

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
        if(shopInfo == null)
            return fail(AppExecStatus.FAIL, "该门店不存在！");

        // 因为有可能有用户通过app创建对form，所以key里要有user对信息
        String key = FORM_BUFFER_PREFIX + ":" + Long.toString(user.getId()) + ":" +  Long.toString(formIn.getChecklistID());
        FormOut formOut = redisClusterClient.getObject(key, FormOut.class);
        if(formOut == null) {
            formOut = formService.checkoutFormInfo(user, formIn.getChecklistID());
            if (formOut == null)
                return fail(AppExecStatus.FAIL, "表单不存在或者未启用！");
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
        if(formService.checkinFormResult(user, formIn)){ // 如果有app创建对机会点，要把缓存里对form信息清除
            String key = FORM_BUFFER_PREFIX + ":" + Long.toString(user.getId()) + ":" + Long.toString(formIn.getChecklistID());
            redisClusterClient.delete(key);
        };
        return success("ok");
    }


}
