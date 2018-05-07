package com.emucoo.manager.controller.sys;

import com.emucoo.common.Constant;
import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.util.MD5Util;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.sys.IdsVo;
import com.emucoo.dto.modules.sys.UserBrandAreaShop;
import com.emucoo.dto.modules.user.UserIsUse;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.manager.utils.RedisClusterClient;
import com.emucoo.model.SysUser;
import com.emucoo.model.SysUserShop;
import com.emucoo.service.sys.SysUserRoleService;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.service.sys.SysUserShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/sys/user")
@Api(description="用户管理")
public class SysUserController extends BaseResource {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserShopService sysUserShopService;
    @Autowired
    private RedisClusterClient redisClient;
    /**
     * 查询用户列表
     */
    @ApiOperation(value="分页查询用户")
    @PostMapping("/list")
    //@RequiresPermissions("sys:user:list")
    @ResponseBody
    public ApiResult<PageInfo<SysUser>> list(@RequestBody ParamVo<UserQuery> param){
        UserQuery userQuery=param.getData();
        PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
        List<SysUser> userList = sysUserService.queryList(userQuery);
        PageInfo<SysUser> pageInfo=new PageInfo(userList);
        return success(pageInfo);
    }

    /**
     * 创建用户
     */
    @ApiOperation(value="创建用户")
    @PostMapping("/save")
    // @RequiresPermissions("sys:user:save")
    public ApiResult save(@RequestBody SysUser sysUser){
        SysUser sysUserExample=new SysUser();
        sysUserExample.setIsDel(false);
        if(StringUtil.isNotEmpty(sysUser.getUsername())){
            sysUserExample.setUsername(sysUser.getUsername());
           if(null!=sysUserService.findOne(sysUserExample)){return  fail(ApiExecStatus.INVALID_PARAM,"username已存在!");};
        }
        if(StringUtil.isNotEmpty(sysUser.getMobile())){
            sysUserExample.setMobile(sysUser.getMobile());
            if(null!=sysUserService.findOne(sysUserExample)){return  fail(ApiExecStatus.INVALID_PARAM,"手机号已存在!");};
        }
        if(StringUtil.isNotEmpty(sysUser.getEmail())){
            sysUserExample.setEmail(sysUser.getEmail());
            if(null!=sysUserService.findOne(sysUserExample)){return  fail(ApiExecStatus.INVALID_PARAM,"Email已存在!");};
        }
        sysUser.setIsDel(false);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateUserId(1L);
        sysUser.setIsAdmin(false);
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
        sysUser.setSalt(salt);
        sysUserService.addUser(sysUser);
        return success("success");
    }

    /**
     * 编辑用户
     */
    @ApiOperation(value="编辑用户")
    @PostMapping("/edit")
    // @RequiresPermissions("sys:user:edit")
    public ApiResult edit(@RequestBody SysUser sysUser){
        if(null==sysUser.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUserId(1L);
        //sha256加密
        /*String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
        sysUser.setSalt(salt);*/
        sysUserService.editUser(sysUser);
        //删除redis 缓存
        redisClient.delete(ISystem.IUSER.USER_TOKEN + sysUser.getId());
        return success("success");
    }

    /**
     * check用户是否关联店铺
     */
    @ApiOperation(value="检查是否关联店铺",notes = "只需传用户id参数!")
    @PostMapping("/checkHasShop")
    public ApiResult checkHasShop(@RequestBody SysUser sysUser){
        if(null==sysUser.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
        Example example=new Example(SysUserShop.class);
        example.createCriteria().andEqualTo("userId",sysUser.getId()).andEqualTo("isDel","0");
        List<SysUserShop> listUserShop=sysUserShopService.selectByExample(example);
        if(null!=listUserShop && listUserShop.size()>0){return fail(ApiExecStatus.DAO_ERR,"已关联店铺");}
        else{ return success("success");}

    }

    /**
     * 设置用户品牌分区
     */
    @ApiOperation(value="设置品牌分区店铺")
    @PostMapping("/setBrandAreaShop")
    // @RequiresPermissions("sys:user:setBrandArea")
    public ApiResult setBrandAreaShop(@RequestBody UserBrandAreaShop userBrandAreaShop){
        if(null==userBrandAreaShop.getUserId()){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
        sysUserService.setBrandAreaShop(userBrandAreaShop);
        return success("success");
    }


    /**
     * 批量删除用户
     */
    @ApiOperation(value="批量删除用户",notes = "多个用户id 用 , 分隔")
    @PostMapping("/deleteByIds")
    //  @RequiresPermissions("sys:user:deleteByIds")
    // @ApiImplicitParam(name="ids",value="用户id字符串",dataType="string",required=true,paramType="query")
    public ApiResult deleteByIds(@RequestBody ParamVo<IdsVo> param){
        IdsVo idsVo=param.getData();
        String ids=idsVo.getIds();
        if(StringUtil.isNotEmpty(ids)){return fail(ApiExecStatus.INVALID_PARAM,"ids 不能为空!");}
        if(ArrayUtils.contains(ids.split(","), String.valueOf(Constant.SUPER_ADMIN))){
            return fail(ApiExecStatus.FAIL,"系统管理员不能删除");
        }
        String idArr[]=ids.split(",");
        SysUser sysUser=null;
        List<SysUser> userList=null;
        if(null!=idArr && idArr.length>0){
            userList=new ArrayList<>();
            for (String id:idArr){
                sysUser =new SysUser();
                sysUser.setIsDel(true);
                sysUser.setModifyUserId(1L);
                sysUser.setModifyTime(new Date());
                sysUser.setId(Long.parseLong(id));
                userList.add(sysUser);
            }
        }
        sysUserService.modifyUserBatch(userList);
        for(SysUser su:userList){
            //删除redis 缓存
            redisClient.delete(ISystem.IUSER.USER_TOKEN + su.getId());
        }
        return success("success");
    }

    /**
     * 删除单个用户
     */
    @PostMapping("/delete")
    // @RequiresPermissions("sys:user:delete")
    @ApiOperation(value="删除用户")
    public ApiResult delete(@RequestBody  SysUser sysUser){
        if(null==sysUser.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
        if(sysUser.getId()==Constant.SUPER_ADMIN){ return fail(ApiExecStatus.FAIL,"系统管理员不能删除"); }
        sysUser.setIsDel(true);
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUserId(1L);
        sysUserService.updateSelective(sysUser);
        //删除redis 缓存
        redisClient.delete(ISystem.IUSER.USER_TOKEN + sysUser.getId());
        return success("success");
    }

    /**
     * 批量启用/停用 用户
     */
    @PostMapping("/modifyBatchUse")
    // @RequiresPermissions("sys:user:modifyBatchUse")
    @ApiOperation(value="批量启用/停用",notes = "ids 传用户id,多个id 直接用 ,分隔; 用户状态(status):0-启用；1-停用；2-锁定；")
  /*  @ApiImplicitParams({
    @ApiImplicitParam(name="ids",value="用户id字符串",dataType="string",required=true,paramType="query"),
    @ApiImplicitParam(name="status",value="0/1",dataType="int",required=true,paramType="query")})*/
    public ApiResult modifyBatchUse(@RequestBody ParamVo<UserIsUse> param){
        UserIsUse userIsUse=param.getData();
        String ids=userIsUse.getIds();
        Integer status=userIsUse.getStatus();
        if(StringUtil.isNotEmpty(ids)){return fail(ApiExecStatus.INVALID_PARAM,"ids 不能为空!");}
        if(ArrayUtils.contains(ids.split(","), String.valueOf(Constant.SUPER_ADMIN))){
            return fail(ApiExecStatus.FAIL,"系统管理员不能删除");
        }
        if(null==status){ return fail(ApiExecStatus.INVALID_PARAM,"status 不能为空！"); }
        String idArr[]=ids.split(",");
        SysUser sysUser=null;
        List<SysUser> userList=null;
        if(null!=idArr && idArr.length>0){
            userList=new ArrayList<>();
            for (String id:idArr){
                sysUser =new SysUser();
                sysUser.setStatus(status);
                sysUser.setModifyUserId(1L);
                sysUser.setModifyTime(new Date());
                sysUser.setId(Long.parseLong(id));
                userList.add(sysUser);
            }
        }
        sysUserService.modifyUserBatch(userList);
        if(status==1){
            for(SysUser su:userList){
                //删除redis 缓存
                redisClient.delete(ISystem.IUSER.USER_TOKEN + su.getId());
            }
        }
        return success("success");
    }

    /**
     * 启用/停用 用户
     */
    @PostMapping("/modifyUse")
    // @RequiresPermissions("sys:user:modifyUse")
    @ApiOperation(value="启用/停用",notes = "用户状态(status):0-启用；1-停用；2-锁定；")
   /* @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户id",dataType="long",required=true,paramType="query"),
            @ApiImplicitParam(name="status",value="0/1",dataType="int",required=true,paramType="query")})*/
    public ApiResult modifyUse(@RequestBody  SysUser sysUser){
        Long id=sysUser.getId();
        Integer status=sysUser.getStatus();
        if(null==id){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
        if(id==Constant.SUPER_ADMIN){ return fail(ApiExecStatus.FAIL,"系统管理员不能修改"); }
        if(null==status){ return fail(ApiExecStatus.INVALID_PARAM,"status 不能为空！"); }
        sysUserService.updateSelective(sysUser);
        //停用
        if(status==1){
            //删除redis 缓存
            redisClient.delete(ISystem.IUSER.USER_TOKEN + id);
        }
        return success("success");
    }

    /**
     * 根据用户id 获取用户详情
     */
    @PostMapping("/getUserById")
    @ApiOperation(value="获取用户详情")
    @ResponseBody
    // @ApiImplicitParam(name="id",value="用户id",dataType="long",required=true,paramType="query")
    public ApiResult<SysUser> getUserById(@RequestBody SysUser sysUser){
        if(null==sysUser.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
        return success(sysUserService.findById(sysUser.getId()));
    }

    /**
     * 查询是店长的用户
     */
    @PostMapping("/getShopManagers")
    @ApiOperation(value="获取店长信息集合")
    public ApiResult<List<SysUser>> getShopManagers(){
        SysUser sysUser=new SysUser();
        sysUser.setIsShopManager(true);
        sysUser.setIsDel(false);
        List<SysUser> listUser=sysUserService.findListByWhere(sysUser);
        return success(listUser);
    }


}
