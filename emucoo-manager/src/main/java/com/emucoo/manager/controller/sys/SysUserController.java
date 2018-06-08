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
import com.emucoo.manager.shiro.ShiroUtils;
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
        int result=checkUser(sysUser);
        if(0!=result){
            if(result==-1) return  fail(ApiExecStatus.INVALID_PARAM,"username已存在!");
            else if(result==-2) return  fail(ApiExecStatus.INVALID_PARAM,"手机号已存在!");
            else if(result==-3) return  fail(ApiExecStatus.INVALID_PARAM,"email已存在!");
        }
        sysUser.setIsDel(false);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateUserId(ShiroUtils.getUserId());
        sysUser.setIsAdmin(false);
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
        sysUser.setSalt(salt);
        //用户状态：0-启用；1-停用；2-锁定；
        sysUser.setStatus(1);
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
        sysUser.setModifyUserId(ShiroUtils.getUserId());
        int result=checkUser(sysUser);
        if(0!=result){
            if(result==-1) return  fail(ApiExecStatus.INVALID_PARAM,"username已存在!");
            else if(result==-2) return  fail(ApiExecStatus.INVALID_PARAM,"手机号已存在!");
            else if(result==-3) return  fail(ApiExecStatus.INVALID_PARAM,"email已存在!");
        }
        //sha256加密
        /*String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
        sysUser.setSalt(salt);*/
        sysUserService.editUser(sysUser);
        //删除redis 缓存
        redisClient.delete(ISystem.IUSER.USER + sysUser.getId());
        return success("success");
    }

    private int checkUser(SysUser sysUser){
        int result=0;
        Example example=new Example(SysUser.class);
        if(StringUtil.isNotEmpty(sysUser.getUsername())){
            example.clear();
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("isDel",false);
            if(null!=sysUser.getId()) criteria.andNotEqualTo("id",sysUser.getId());
            criteria.andEqualTo("username",sysUser.getUsername());
            List<SysUser> list=sysUserService.selectByExample(example);
            if(null!=list && list.size()>0) result=-1;
        }
        if(StringUtil.isNotEmpty(sysUser.getMobile())){
            example.clear();
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("isDel",false);
            if(null!=sysUser.getId()) criteria.andNotEqualTo("id",sysUser.getId());
            criteria.andEqualTo("mobile",sysUser.getMobile());
            List<SysUser> list=sysUserService.selectByExample(example);
            if(null!=list && list.size()>0) result=-2;
        }
        if(StringUtil.isNotEmpty(sysUser.getEmail())){
            example.clear();
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("isDel",false);
            if(null!=sysUser.getId()) criteria.andNotEqualTo("id",sysUser.getId());
            criteria.andEqualTo("email",sysUser.getEmail());
            List<SysUser> list=sysUserService.selectByExample(example);
            if(null!=list && list.size()>0) result=-3;
        }
        return  result;
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
     * 重置密码
     */
    @ApiOperation(value="重置密码")
    @PostMapping("/resetPassword")
    public ApiResult resetPassword(@RequestBody SysUser sysUser) {
        if(null==sysUser.getId()){return fail(ApiExecStatus.INVALID_PARAM,"用户 id 不能为空!");}
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        //如果前台没有传值，后台默认重置密码为"123456"
        if(StringUtil.isEmpty(sysUser.getPassword())){sysUser.setPassword("123456");}
        sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
        sysUser.setSalt(salt);
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUserId(ShiroUtils.getUserId());
        sysUserService.updateSelective(sysUser);
        //删除redis 缓存
        redisClient.delete(ISystem.IUSER.USER + sysUser.getId());
        return success("success");
    }

    /**
     * 设置用户品牌分区
     */
    @ApiOperation(value="设置品牌分区")
    @PostMapping("/setBrandArea")
    // @RequiresPermissions("sys:user:setBrandArea")
    public ApiResult setBrandArea(@RequestBody UserBrandAreaShop userBrandAreaShop){
        if(null==userBrandAreaShop.getUserId()){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
        sysUserService.setBrandArea(userBrandAreaShop,ShiroUtils.getUserId());
        return success("success");
    }

    /**
     * 设置用户店铺
     */
    @ApiOperation(value="设置用户店铺")
    @PostMapping("/setShop")
    // @RequiresPermissions("sys:user:setBrandArea")
    public ApiResult setShop(@RequestBody UserBrandAreaShop userBrandAreaShop){
        if(null==userBrandAreaShop.getUserId()){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
        sysUserService.setShop(userBrandAreaShop,ShiroUtils.getUserId());
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
                sysUser.setModifyUserId(ShiroUtils.getUserId());
                sysUser.setModifyTime(new Date());
                sysUser.setId(Long.parseLong(id));
                userList.add(sysUser);
            }
        }
        sysUserService.modifyUserBatch(userList);
        for(SysUser su:userList){
            //删除redis 缓存
            redisClient.delete(ISystem.IUSER.USER + su.getId());
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
        sysUser.setModifyUserId(ShiroUtils.getUserId());
        sysUserService.updateSelective(sysUser);
        //删除redis 缓存
        redisClient.delete(ISystem.IUSER.USER + sysUser.getId());
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
                sysUser.setModifyUserId(ShiroUtils.getUserId());
                sysUser.setModifyTime(new Date());
                sysUser.setId(Long.parseLong(id));
                userList.add(sysUser);
            }
        }
        sysUserService.modifyUserBatch(userList);
        if(status==1){
            for(SysUser su:userList){
                //删除redis 缓存
                redisClient.delete(ISystem.IUSER.USER + su.getId());
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
            redisClient.delete(ISystem.IUSER.USER + id);
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
        //0-启用
        sysUser.setStatus(0);
        List<SysUser> listUser=sysUserService.findListByWhere(sysUser);
        return success(listUser);
    }


}
