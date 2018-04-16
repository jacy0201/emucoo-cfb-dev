package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.shop.ShopPlanListVO;
import com.emucoo.dto.modules.shop.ShopPlanListVO_I;
import com.emucoo.dto.modules.shop.ShopPlanProgressVO;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author Jacy
 *
 */
@RestController
@RequestMapping("/api/shop/plan")
public class ShopPlanController extends AppBaseController {

    @Resource
    private ShopPlanService shopPlanService;

    /**
     * 获取巡店计划列表
     *@param
     */
    @PostMapping("/list")
    public AppResult<ShopPlanListVO> shopPlanList(@RequestBody ParamVo<ShopPlanListVO_I> params){
        return success(shopPlanService.getShopPlanList(params.getData().getMonth()));
    }

    /***
     *获取巡店计划进度
     */
    @PostMapping("/progress")
    public AppResult<ShopPlanProgressVO> getShopPlanProgress(){
        User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        return success(shopPlanService.getShopPlanProgress(user.getId()));
    }

}
