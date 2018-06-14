package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.sys.UserBrandAreaShop;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.common.Constant;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 系统用户 服务实现类
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private SysUserPostMapper sysUserPostMapper;
	@Autowired
	private SysPostMapper sysPostMapper;
	@Autowired
	private SysUserShopMapper sysUserShopMapper;
	@Autowired
	private TShopInfoMapper shopInfoMapper;
	@Autowired
	private SysUserBrandMapper sysUserBrandMapper;
	@Autowired
	private TBrandInfoMapper brandInfoMapper;
	@Autowired
	private SysUserAreaMapper sysUserAreaMapper;
	@Autowired
	private SysAreaMapper sysAreaMapper;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}

	@Override
	public List<SysUser> listByPostId(HashMap map) { return sysUserMapper.listByPostId(map); }

	@Override
	public List<SysUser> listByShopId(HashMap map) { return sysUserMapper.listByShopId(map); }

	@Override
	public List<SysUser> queryList(UserQuery userQuery){
		List<SysUser> userList=null;
		String realName="";
		String username="";
		String mobile="";
		String email="";
		Long dptId=null;
		Integer status=null;
		Boolean isShopManager=null;
		Long shopId=null;
		Long postId=null;
		Example example=new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel",0);
		SysUser su=(SysUser)SecurityUtils.getSubject().getPrincipal();
        //如果不是 超级管理员登录，则不能查询 管理员级别的账号
		if(!Constant.SUPER_ADMIN.equals(su.getId())) {
			criteria.andNotEqualTo("isAdmin", true);
		}
        if(null!=userQuery){
			if(StringUtil.isNotEmpty(userQuery.getRealName())){
			    realName=userQuery.getRealName();
                criteria.andLike("realName","%"+realName+"%");
			}
			if(StringUtil.isNotEmpty(userQuery.getUsername())){
			    username=userQuery.getUsername();
                criteria.andLike("username","%"+username+"%");
			}
			if(StringUtil.isNotEmpty(userQuery.getMobile())){ mobile=userQuery.getMobile(); criteria.andEqualTo("mobile",mobile); }
			if(StringUtil.isNotEmpty(userQuery.getEmail())){ email=userQuery.getEmail(); criteria.andLike("email","%"+email+"%"); }
			if(null!=userQuery.getDptId()){ dptId=userQuery.getDptId(); criteria.andEqualTo("dptId",dptId);}
			if(null!=userQuery.getIsShopManager()){ isShopManager=userQuery.getIsShopManager();criteria.andEqualTo("isShopManager",isShopManager);}
			if(null!=userQuery.getStatus()){ status=userQuery.getStatus(); criteria.andEqualTo("status",status);}
			if(null!=userQuery.getShopId()){ shopId=userQuery.getShopId(); }
			if(null!=userQuery.getPostId()){ postId=userQuery.getPostId(); }
		}
		HashMap paramMap=new HashMap();
		paramMap.put("realName",realName);
		paramMap.put("username",username);
		paramMap.put("mobile",mobile);
		paramMap.put("email",email);
		paramMap.put("dptId",dptId);
		paramMap.put("isShopManager",isShopManager);
		paramMap.put("status",status);
		List<SysUser> list1=null;
		List<SysUser> list2=null;
		if(null!=shopId){
			paramMap.put("shopId",shopId);
			list1=sysUserMapper.listByShopId(paramMap);
		}
		if(null != postId){
			paramMap.put("postId",postId);
			list2=sysUserMapper.listByPostId(paramMap);
		}
		if(null!=shopId && null==postId){ userList=list1; }
		else if(null==shopId && null!=postId){userList=list2;}
        else if(null==shopId && null==postId){userList=sysUserMapper.selectByExample(example);}
        else if(null!=shopId && null!=postId){
			userList=new ArrayList<>();
			if(null!=list1 && null!=list2){
				for (SysUser sysUser1 :list1){
					for(SysUser sysUser2:list2){
						if(sysUser1.getId()==sysUser2.getId()){userList.add(sysUser1);}
					}
				}
			}
		}
		if(null!=userList && userList.size()>0){
			Example exampleUserPost=new Example(SysUserPost.class);
			Example exampleUserShop=new Example(SysUserShop.class);
			Example exampleUserBrand=new Example(SysUserBrand.class);
			Example exampleUserArea=new Example(SysUserArea.class);
			for(SysUser sysUser:userList){
				//设置机构名称
				if(null!=sysUser.getDptId()){ sysUser.setDptName(sysDeptMapper.selectByPrimaryKey(sysUser.getDptId()).getDptName()); }
				//设置岗位集合
				exampleUserPost.clear();
				exampleUserPost.createCriteria().andEqualTo("userId",sysUser.getId()).andEqualTo("isDel",false);
				List<SysUserPost> listUserPost= sysUserPostMapper.selectByExample(exampleUserPost);
				if(null!=listUserPost && listUserPost.size()>0){
					List<SysPost> postList=new ArrayList<>();
					for(SysUserPost sysUserPost :listUserPost){
						postList.add(sysPostMapper.selectByPrimaryKey(sysUserPost.getPostId()));
					}
					sysUser.setPostList(postList);
				}
				//设置店铺集合
				exampleUserShop.clear();
				exampleUserShop.createCriteria().andEqualTo("userId",sysUser.getId()).andEqualTo("isDel",false);
				List<SysUserShop> listUserShop= sysUserShopMapper.selectByExample(exampleUserShop);
				if(null!=listUserShop && listUserShop.size()>0){
					List<TShopInfo> shopList=new ArrayList<>();
					for(SysUserShop sysUserShop :listUserShop){
						shopList.add(shopInfoMapper.selectByPrimaryKey(sysUserShop.getShopId()));
					}
					sysUser.setShopList(shopList);
				}

				//设置品牌集合
				exampleUserBrand.clear();
				exampleUserBrand.createCriteria().andEqualTo("userId",sysUser.getId()).andEqualTo("isDel",false);
				List<SysUserBrand> listUserBrand= sysUserBrandMapper.selectByExample(exampleUserBrand);
				if(null!=listUserBrand && listUserBrand.size()>0){
					List<TBrandInfo> brandList=new ArrayList<>();
					for(SysUserBrand sysUserBrand :listUserBrand){
						brandList.add(brandInfoMapper.selectByPrimaryKey(sysUserBrand.getBrandId()));
					}
					sysUser.setBrandList(brandList);
				}

				//设置分区集合
				exampleUserArea.clear();
				exampleUserArea.createCriteria().andEqualTo("userId",sysUser.getId()).andEqualTo("isDel",false);
				List<SysUserArea> listUserArea= sysUserAreaMapper.selectByExample(exampleUserArea);
				if(null!=listUserArea && listUserArea.size()>0){
					List<SysArea> areaList=new ArrayList<>();
					for(SysUserArea sysUserArea :listUserArea){
						areaList.add(sysAreaMapper.selectByPrimaryKey(sysUserArea.getAreaId()));
					}
					sysUser.setAreaList(areaList);
				}
			}
		}
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUser(SysUser sysUser){
		sysUserMapper.insertUseGeneratedKeys(sysUser);
		Long id=sysUser.getId();
		//保存用户岗位
		List<SysPost> listPost=sysUser.getPostList();
		if(null!=listPost && listPost.size()>0){
			SysUserPost sysUserPost=null;
			for(SysPost sysPost:listPost){
				sysUserPost=new SysUserPost();
				sysUserPost.setCreateTime(new Date());
				sysUserPost.setCreateUserId(sysUser.getCreateUserId());
				sysUserPost.setIsDel(false);
				sysUserPost.setUserId(id);
				sysUserPost.setPostId(sysPost.getId());
				sysUserPostMapper.insertSelective(sysUserPost);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setBrandArea(UserBrandAreaShop userBrandAreaShop,Long createUserId){
		//设置用户分区
		List<Long> areaIdList=userBrandAreaShop.getListAreaId();
		SysUserArea sysUserArea=null;
		//先删除之前的分区关系
		Example exampleUserArea = new Example(SysUserArea.class);
		exampleUserArea.createCriteria().andEqualTo("userId", userBrandAreaShop.getUserId());
		sysUserAreaMapper.deleteByExample(exampleUserArea);
		if(null!=areaIdList && areaIdList.size()>0) {
			for (Long areaId : areaIdList) {
				sysUserArea = new SysUserArea();
				sysUserArea.setAreaId(areaId);
				sysUserArea.setUserId(userBrandAreaShop.getUserId());
				sysUserArea.setCreateTime(new Date());
				sysUserArea.setCreateUserId(createUserId);
				sysUserArea.setIsDel(false);
				sysUserAreaMapper.insertSelective(sysUserArea);
			}
		}
		//设置用户品牌
		List<Long> brandIdList=userBrandAreaShop.getListBrandId();
		SysUserBrand sysUserBrand=null;
		//先删除之前的品牌关系
		Example exampleUserBrand = new Example(SysUserBrand.class);
		exampleUserBrand.createCriteria().andEqualTo("userId", userBrandAreaShop.getUserId());
		sysUserBrandMapper.deleteByExample(exampleUserBrand);
		if(null!=brandIdList && brandIdList.size()>0) {
			for (Long brandId : brandIdList) {
				sysUserBrand = new SysUserBrand();
				sysUserBrand.setBrandId(brandId);
				sysUserBrand.setUserId(userBrandAreaShop.getUserId());
				sysUserBrand.setCreateTime(new Date());
				sysUserBrand.setCreateUserId(createUserId);
				sysUserBrand.setIsDel(false);
				sysUserBrandMapper.insertSelective(sysUserBrand);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setShop(UserBrandAreaShop userBrandAreaShop,Long createUserId){
		//设置用户店铺
		List<Long> shopIdList=userBrandAreaShop.getListShopId();
		SysUserShop sysUserShop=null;
		//先删除之前的shop关系
		Example exampleUserShop = new Example(SysUserShop.class);
		exampleUserShop.createCriteria().andEqualTo("userId", userBrandAreaShop.getUserId());
		sysUserShopMapper.deleteByExample(exampleUserShop);
		if(null!=shopIdList && shopIdList.size()>0) {
			for (Long shopId : shopIdList) {
				sysUserShop = new SysUserShop();
				sysUserShop.setShopId(shopId);
				sysUserShop.setUserId(userBrandAreaShop.getUserId());
				sysUserShop.setCreateTime(new Date());
				sysUserShop.setCreateUserId(createUserId);
				sysUserShop.setIsDel(false);
				sysUserShopMapper.insertSelective(sysUserShop);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void editUser(SysUser sysUser){
		//删除用户店铺关系
		if(null!=sysUser.getIsShopManager() && !sysUser.getIsShopManager()){
			shopInfoMapper.updateByManagerId(sysUser.getId());
		}
		sysUserMapper.updateByPrimaryKeySelective(sysUser);
		//先删除用户之前的岗位关系
		Example example=new Example(SysUserPost.class);
		example.createCriteria().andEqualTo("userId",sysUser.getId());
		sysUserPostMapper.deleteByExample(example);
		//保存用户岗位
		List<SysPost> listPost=sysUser.getPostList();
		if(null!=listPost && listPost.size()>0){
			SysUserPost sysUserPost=null;
			for(SysPost sysPost:listPost){
				sysUserPost=new SysUserPost();
				sysUserPost.setCreateTime(new Date());
				sysUserPost.setCreateUserId(sysUser.getCreateUserId());
				sysUserPost.setIsDel(false);
				sysUserPost.setUserId(sysUser.getId());
				sysUserPost.setPostId(sysPost.getId());
				sysUserPostMapper.insertSelective(sysUserPost);
			}
		}

	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void modifyUserBatch(List<SysUser> userList){
		for(SysUser sysUser:userList){
			sysUserMapper.updateByPrimaryKeySelective(sysUser);
		}
	}
}
