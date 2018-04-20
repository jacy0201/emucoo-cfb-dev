package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.plan.FindPlanListIn;
import com.emucoo.dto.modules.plan.FindPlanListOut;
import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.dto.modules.plan.PlanProgressOut;
import com.emucoo.dto.modules.plan.ShopToPlanIn;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.plan.PlanService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Jacy
 *
 */
@RestController
@RequestMapping("/api/shop/plan")
public class PlanController extends AppBaseController {

    @Resource
    private PlanService planService;

    /**
     * 获取该区域内当前用户负责的店铺列表
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "findShopListInArea")
    public AppResult<FindShopListOut> findShopListInArea(@RequestBody ParamVo<FindShopListIn> params, HttpServletRequest request) {
        FindShopListIn findShopListIn = params.getData();
        checkParam(findShopListIn.getPlanID(), "计划id不能为空！");
        checkParam(findShopListIn.getPrecinctID(), "管理区域id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        FindShopListOut findShopListOut = planService.findShopList(user, findShopListIn);
        return success(findShopListOut);
    }

    /**
     * 计划中增加店铺，状态为未安排
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "addShopToPlan")
    public AppResult addShopToPlan(@RequestBody ParamVo<ShopToPlanIn> params, HttpServletRequest request) {
        ShopToPlanIn shopToPlanIn = params.getData();
        checkParam(shopToPlanIn.getPlanID(), "计划id不能为空！");
        checkParam(shopToPlanIn.getPrecinctID(), "管理区域id不能为空！");
        checkParam(shopToPlanIn.getShopArr(), "店铺列表不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        planService.addShopToPlan(user, shopToPlanIn);
        return success("success");
    }

    @PostMapping(value = "deleteShopInPlan")
    public AppResult deleteShopInPlan(@RequestBody ParamVo<ShopToPlanIn> params) {
        ShopToPlanIn shopToPlanIn = params.getData();
        checkParam(shopToPlanIn.getPlanID(), "计划id不能为空！");
        checkParam(shopToPlanIn.getPrecinctID(), "管理区域id不能为空！");
        checkParam(shopToPlanIn.getShopArr(), "店铺列表不能为空！");
        //SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        planService.deleteShopInPlan(shopToPlanIn);
        return success("success");
    }

    @PostMapping(value = "list")
    public AppResult listPlan(@RequestBody ParamVo<FindPlanListIn> params, HttpServletRequest request) {
        FindPlanListIn findPlanListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        FindPlanListOut findPlanListOut = planService.listPlan(user, findPlanListIn);
        return success(findPlanListOut);
    }

    @PostMapping(value = "progress")
    public AppResult planProcess(HttpServletRequest request) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        PlanProgressOut planProcessOut = planService.findPlanProcessByUserId(user);
        return success(planProcessOut);
    }


}
