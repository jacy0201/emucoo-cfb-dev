package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
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

    /*@PostMapping(value = "getFormTempletInfo")
    public AppResult<FindShopListOut> findShopListInArea(@RequestBody ParamVo<FindShopListIn> params, HttpServletRequest request) {
        FindShopListIn findShopListIn = params.getData();
        checkParam(findShopListIn.getPlanID(), "计划id不能为空！");
        checkParam(findShopListIn.getPrecinctID(), "管理区域id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        FindShopListOut findShopListOut = planService.findShopList(user, findShopListIn);
        return success(findShopListOut);
    }*/



}
