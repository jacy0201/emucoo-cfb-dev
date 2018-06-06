package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.dto.modules.shop.PlanArrangeGetFormIn;
import com.emucoo.dto.modules.shop.ShopListQuery;
import com.emucoo.model.SysArea;
import com.emucoo.model.SysUser;
import com.emucoo.model.TShopInfo;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.shop.ShopManageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jacy
 * 店面管理
 */
@RestController
@RequestMapping("/api/shop/manage")
public class ShopManageController extends AppBaseController {

    @Resource
    private ShopManageService shopManageService;

    /**
     * 获取用户的分区资源
     * @return
     */
    @PostMapping(value = "getAreaList")
    public AppResult getAreaList() {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<SysArea> list=shopManageService.getAreaList(user.getId());
        return success(list);
    }

    /**
     * 根据分区获取店面资源
     * @return
     */
    @PostMapping(value = "getShopList")
    public AppResult getShopList(@RequestBody ParamVo<ShopListQuery> base) {
        ShopListQuery shopListQuery= base.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<TShopInfo> list=shopManageService.getShopList(shopListQuery.getAreaId(),user.getId());
        return success(list);
    }

}
