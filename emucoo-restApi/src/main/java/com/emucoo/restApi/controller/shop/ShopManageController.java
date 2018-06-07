package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.center.TaskQuery;
import com.emucoo.dto.modules.shop.FormResultVO;
import com.emucoo.dto.modules.shop.ResultQuery;
import com.emucoo.dto.modules.shop.ShopListQuery;
import com.emucoo.dto.modules.shop.ShopVO;
import com.emucoo.model.SysArea;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TRepairWork;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.center.UserCenterService;
import com.emucoo.service.shop.ShopManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jacy
 * 店面管理
 */
@Api(description = "店铺管理接口")
@RestController
@RequestMapping("/api/shop/manage")
public class ShopManageController extends AppBaseController {

    @Resource
    private ShopManageService shopManageService;

    @Resource
    private UserCenterService userCenterService;


    /**
     * 获取用户的分区资源
     * @return
     */
    @PostMapping(value = "getAreaList")
    @ApiOperation(value="获取用户的分区资源")
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
    @ApiOperation(value="根据分区获取店面资源")
    public AppResult getShopList(@RequestBody ParamVo<ShopListQuery> base) {
        ShopListQuery shopListQuery= base.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<ShopVO> list=shopManageService.getShopList(shopListQuery.getAreaId(),user.getId());
        return success(list);
    }

    /**
     * 选择检查表
     */
    @PostMapping(value = "selectFormList")
    @ApiOperation(value="选择检查表")
    public AppResult selectFormList() {
        List<TFormMain> list=shopManageService.selectFormList();
        return success(list);
    }


    /**
     * 稽核结果
     */
    @PostMapping(value = "getResultList")
    @ApiOperation(value="稽核结果")
    public AppResult getResultList(@RequestBody ParamVo<ResultQuery> base) {
        ResultQuery resultQuery= base.getData();
        List<FormResultVO> list =shopManageService.getResultList(resultQuery);
        return success(list);
    }

    /**
     * 维修单
     */
    @PostMapping(value = "getRepairList")
    @ApiOperation(value="维修单")
    public AppResult getRepairList(@RequestBody ParamVo<TaskQuery> base) {
        TaskQuery taskQuery= base.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<TRepairWork> list=userCenterService.repairWorkList(taskQuery.getMonth(),currUser.getId());
        return success(list);
    }

}
