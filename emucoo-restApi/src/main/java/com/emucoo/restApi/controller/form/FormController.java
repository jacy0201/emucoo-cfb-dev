package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.form.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sj on 2018/4/24.
 */
@Controller
@RequestMapping(value = "/api/form")
public class FormController extends AppBaseController {

    @Autowired
    private FormService formService;

    @PostMapping(value = "getFormTempletInfo")
    public AppResult<FormOut> getFormTempletInfo(@RequestBody ParamVo<FormIn> params, HttpServletRequest request) {
        FormIn formIn = params.getData();
        checkParam(formIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(formIn.getChecklistID(), "表单id不能为空！");
        checkParam(formIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        //FormOut formOut = formService.getFormTempletInfo(user, formIn);
        FormOut formOut = new FormOut();
        return success(formOut);
    }



}
