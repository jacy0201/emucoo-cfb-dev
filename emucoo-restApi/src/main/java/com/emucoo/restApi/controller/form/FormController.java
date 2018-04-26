package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.form.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sj on 2018/4/24.
 */
@RestController
@RequestMapping(value = "/api/form")
public class FormController extends AppBaseController {

    @Autowired
    private FormService formService;

    @PostMapping(value = "checkoutFormInfo")
    public AppResult<FormOut> checkFormInfo(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<FormIn> params) {
        SysUser user = UserTokenManager.getInstance().currUser(userToken);
        FormIn formIn = params.getData();
        checkParam(formIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(formIn.getChecklistID(), "表单id不能为空！");
        checkParam(formIn.getShopID(), "店铺id不能为空！");
        FormOut formOut = formService.checkoutFormInfo(user, formIn);

        return success(formOut);
    }


    @PostMapping(value = "checkinFormResult")
    public AppResult<String> checkFormResult(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<FormIn> params ) {
        SysUser user = UserTokenManager.getInstance().currUser(userToken);

        FormIn formIn = params.getData();
        formService.checkinFormResult(formIn);

        return success("ok");
    }


}
