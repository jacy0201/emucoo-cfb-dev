package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.sys.DeptQuery;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.manager.shiro.ShiroUtils;
import com.emucoo.model.*;
import com.emucoo.service.sys.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * 机构管理
 */
@RestController
@RequestMapping("/sys/dept")
@Api(description="机构管理" )
public class SysDeptController extends BaseResource {
	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserRelationService sysUserRelationService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserPostService sysUserPostService;

	@Autowired
	private SysPostService sysPostService;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 查询机构列表
	 */
	@ApiOperation(value="查询机构列表")
	@PostMapping("/list")
	//@RequiresPermissions("sys:dept:list")
	@ResponseBody
	public ApiResult<List<SysDept>> list(@RequestBody DeptQuery DeptQuery){
		List<SysDept> deptList = sysDeptService.queryList(DeptQuery);
		return success(deptList);
	}


	/**
	 * 保存机构信息
	 */
	@ApiOperation(value="创建机构")
	@PostMapping("/save")
	//@RequiresPermissions("sys:dept:save")
	public ApiResult save(@RequestBody SysDept dept){
		dept.setCreateUserId(ShiroUtils.getUserId());
		sysDeptService.saveDept(dept);
		return success("success");
	}

	/**
	 * 更新机构信息
	 */
	@ApiOperation(value="更新机构")
	@PostMapping("/update")
	//@RequiresPermissions("sys:dept:update")
	public ApiResult update(@RequestBody SysDept dept){
		if(dept.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"机构 id 不能为空!");}
		dept.setCreateUserId(ShiroUtils.getUserId());
		sysDeptService.updateDept(dept);
		return success("success");
	}

	/**
	 * 启用/停用机构信息
	 */
	@ApiOperation(value="启用/停用 机构")
	@PostMapping("/modifyUse")
	// @RequiresPermissions("sys:dept:modifyUse")
	public ApiResult modifyUse(@RequestBody SysDept dept){
		if(dept.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"机构 id 不能为空!");}
		sysDeptService.updateSelective(dept);
		return success("success");
	}


	/**
	 * 删除机构信息
	 */
	@ApiOperation(value="删除机构")
	@PostMapping("/delete")
	//@RequiresPermissions("sys:dept:delete")
	public ApiResult delete(@RequestBody SysDept dept){
		if(dept.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"机构 id 不能为空!");}
		//判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(dept.getId());
		if(deptList.size() > 0){
			return fail("请先删除子部门");
		}
		sysDeptService.deleteDept(dept);
		return success("success");
	}


	/**
	 * 获取用户关系
	 * @return
	 */
	@ApiOperation(value="获取用户关系")
	@PostMapping("/listUserRelation")
	//@RequiresPermissions("sys:dept:listUserRelation")
	public ApiResult<List<SysUserRelation>> listUserRelation(@RequestBody SysUserRelation sysUserRelation){
		if(sysUserRelation.getDptId()==null){return fail(ApiExecStatus.INVALID_PARAM,"dptId 不能为空!");}
		List<SysUserRelation> list=sysUserRelationService.listUserRelation(sysUserRelation.getDptId());
		return success(list);
	}

	/**
	 * 添加下级用户
	 * @return
	 */
	@ApiOperation(value="添加下级用户")
	@PostMapping("/addChildUser")
	//@RequiresPermissions("sys:user:addChildUser")
	public ApiResult addChildUser(@RequestBody SysUserRelation sysUserRelation){
		if(sysUserRelation.getDptId()==null){return fail(ApiExecStatus.INVALID_PARAM,"dptId 不能为空!");}
		if(sysUserRelation.getUserId()==null){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
		if(sysUserRelation.getParentUserId()==null){return fail(ApiExecStatus.INVALID_PARAM,"parentUserId 不能为空!");}
		if(sysUserRelation.getPostId()==null){return fail(ApiExecStatus.INVALID_PARAM,"postId 不能为空!");}
		if(sysUserRelation.getParentPostId()==null){return fail(ApiExecStatus.INVALID_PARAM,"parentPostId 不能为空!");}
		if(sysUserRelation.getUserId()==sysUserRelation.getParentUserId() && sysUserRelation.getPostId()==sysUserRelation.getParentPostId()){
			return fail(ApiExecStatus.INVALID_PARAM," 同一用户上下级岗位不能相同!");
		}
		sysUserRelation.setIsDel(false);
		sysUserRelation.setCreateTime(new Date());
		sysUserRelation.setCreateUserId(ShiroUtils.getUserId());
		sysUserRelationService.saveSelective(sysUserRelation);
        String userIdStr=jedisCluster.get(ISystem.IUSER.USER_RECENT + sysUserRelation.getParentUserId());
        if(StringUtil.isNotEmpty(userIdStr)){
            String[] idArr = userIdStr.split(",");
            //判断是否存在该下级
            List<String> userIdList= Arrays.asList(idArr);
            if(!userIdList.contains(sysUserRelation.getUserId().toString())){
                userIdList.add(sysUserRelation.getUserId().toString());
                jedisCluster.set(ISystem.IUSER.USER_RECENT + sysUserRelation.getParentUserId(), StringUtils.join(userIdList, ","));
            }

        }
		return success("success");
	}

	/**
	 * 添加用户
	 * @return
	 */
	@ApiOperation(value="添加用户")
	@PostMapping("/addUser")
	//@RequiresPermissions("sys:user:addUser")
	public ApiResult addUser(@RequestBody SysUserRelation sysUserRelation){
		if(sysUserRelation.getDptId()==null){return fail(ApiExecStatus.INVALID_PARAM,"dptId 不能为空!");}
		if(sysUserRelation.getUserId()==null){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
		if(sysUserRelation.getPostId()==null){return fail(ApiExecStatus.INVALID_PARAM,"postId 不能为空!");}
		sysUserRelation.setIsDel(false);
		sysUserRelation.setCreateTime(new Date());
		sysUserRelation.setCreateUserId(ShiroUtils.getUserId());
		sysUserRelationService.saveSelective(sysUserRelation);
		return success("success");
	}


	/**
	 * 删除下级
	 * @return
	 */
	@ApiOperation(value="删除用户")
	@PostMapping("/deleteUser")
	//@RequiresPermissions("sys:user:deleteUser")
	public ApiResult deleteUser(@RequestBody SysUserRelation sysUserRelation){
		if(sysUserRelation.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 不能为空!");}
		if(sysUserRelation.getUserId()==null){return fail(ApiExecStatus.INVALID_PARAM,"userId 不能为空!");}
        //检查该用户是否有下级，如果有下级需先删除下级用户
        SysUserRelation sysUserRelation1=sysUserRelationService.findById(sysUserRelation.getId());
        Long userId=sysUserRelation1.getUserId();
        Long parentUserId=sysUserRelation1.getParentUserId();
		sysUserRelationService.deleteById(sysUserRelation.getId());
        String userIdStr=jedisCluster.get(ISystem.IUSER.USER_RECENT + parentUserId);
        if(StringUtil.isNotEmpty(userIdStr)){
            String[] idArr = userIdStr.split(",");
			List<String> userIdList= Arrays.asList(idArr);
            //缓存中删除该下级
			for(int i=0 ;i<userIdList.size();i++){
				if(userId.equals(userIdList.get(i))) {
					userIdList.remove(i);
					break;
				}
			}
			jedisCluster.set(ISystem.IUSER.USER_RECENT + sysUserRelation.getParentUserId(), StringUtils.join(userIdList, ","));
        }
		return success("success");
	}




	/**
	 * 获取部门人员
	 * @return
	 */
	@ApiOperation(value="选择人员")
	@PostMapping("/listUser")
	//@RequiresPermissions("sys:user:listUser")
	public ApiResult<List<SysUser>> listUser(@RequestBody ParamVo<UserQuery> param) {
		UserQuery userQuery=param.getData();
		Long deptId=userQuery.getDptId();
		String realName=userQuery.getRealName();
		Long postId=userQuery.getPostId();
		List<SysUser> list=null;
		if(deptId==null){ return fail(ApiExecStatus.INVALID_PARAM,"dptId 不能为空!"); }
		if(postId!=null){
			HashMap map=new HashMap();
			map.put("postId",postId);
			map.put("dptId",deptId);
			map.put("realName",realName);
			list= sysUserService.listByPostId(map);
		}else{
			Example example=new Example(SysUser.class);
			Example.Criteria criteria=example.createCriteria();
			criteria.andEqualTo("dptId",deptId).andEqualTo("isDel",0).andEqualTo("isAdmin",false);
			if(StringUtil.isNotEmpty(realName)){ criteria.andLike("realName","%"+realName+"%"); }
			list=sysUserService.selectByExample(example);
		}
		List<SysUserPost> userPostList=null;
		List<SysPost> postList=null;
		if(null!=list && list.size()>0){
			for(SysUser user:list){
				Example example=new Example(SysUserPost.class);
				example.createCriteria().andEqualTo("userId",user.getId()).andEqualTo("isDel",0);
				userPostList=sysUserPostService.selectByExample(example);
				if(null!=userPostList && userPostList.size()>0){
					postList=new ArrayList<>();
					for(SysUserPost sysUserPost:userPostList) {
						postList.add(sysPostService.findById(sysUserPost.getPostId()));
					}
					user.setPostList(postList);
				}
			}
		}

		return success(list);
	}
}
