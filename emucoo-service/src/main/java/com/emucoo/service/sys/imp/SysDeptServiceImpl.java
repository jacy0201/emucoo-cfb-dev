package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.sys.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept> implements SysDeptService {

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private SysDptAreaMapper sysDptAreaMapper;

	@Autowired
	private SysDptBrandMapper sysDptBrandMapper;

	@Autowired
	private SysAreaMapper sysAreaMapper;

	@Autowired
	private TBrandInfoMapper brandInfoMapper;

	@Override
	public List<SysDept> queryList(Map<String, Object> params){
		Example example=new Example(SysDept.class);
		Long brandId=null;
		Long areaId=null;
		String dptName="";
		Boolean isUse=null;
		List<SysDept> deptList=null;
		if(null!=params.get("dptName") && !"".equals(params.get("dptName").toString())){
			dptName=params.get("dptName").toString();
			example.createCriteria().andLike("dptName","%"+dptName+"%");
		}
		if(null!=params.get("isUse")){
			isUse=Boolean.valueOf(params.get("isUse")+"");
			example.createCriteria().andEqualTo("isUse",isUse);
		}

		HashMap paramMap=null;
		List listB=null;
		List listA=null;
		if(null!=params.get("brandId")){
			paramMap=new HashMap();
			brandId=Long.parseLong(params.get("brandId").toString());
			paramMap.put("brandId",brandId);
			paramMap.put("dptName",dptName);
			paramMap.put("isUse",isUse);
			listB=sysDeptMapper.listByBrand(paramMap);
		}

		if(null!=params.get("areaId")){
			paramMap=new HashMap();
			areaId=Long.parseLong(params.get("areaId").toString());
			paramMap.put("areaId",areaId);
			paramMap.put("dptName",dptName);
			paramMap.put("isUse",isUse);
			listA=sysDeptMapper.listByArea(paramMap);
		}

		if(null!=areaId  && null==brandId){ deptList=listA; }
		else if(null == listA && null!=listB){deptList=listB;}
		else if(null == listA && null ==listB){
			deptList =sysDeptMapper.selectByExample(example);
		}
		if(null!=deptList) {
			HashMap param =null;
			for (SysDept sysDeptEntity : deptList) {
				param =new HashMap();
				param.put("dptId",sysDeptEntity.getId());
				sysDeptEntity.setAreaList(sysAreaMapper.listByDpt(param));
				sysDeptEntity.setBrandList(brandInfoMapper.listByDpt(param));
				SysDept parentDeptEntity = sysDeptMapper.selectByPrimaryKey(sysDeptEntity.getParentId());
				if (parentDeptEntity != null) {
					sysDeptEntity.setParentName(parentDeptEntity.getDptName());
				}
			}
		}
			return deptList;
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public  void saveDept(SysDept sysDept){
		sysDept.setIsDel(false);
		sysDept.setIsUse(true);
		sysDept.setCreateTime(new Date());
		sysDept.setCreateUserId(1L);
		int deptId=sysDeptMapper.insertUseGeneratedKeys(sysDept);

		//添加机构地区关联信息
		List<SysArea> areaList=sysDept.getAreaList();
		SysDptArea sysDptArea=null;
		if(null!=areaList && areaList.size()>0){
			for(SysArea area :areaList){
				sysDptArea=new SysDptArea();
				sysDptArea.setAreaId(area.getId());
				sysDptArea.setDptId(Long.parseLong(deptId+""));
				sysDptArea.setCreateTime(new Date());
				sysDptArea.setCreateUserId(1L);
				sysDptArea.setIsDel(false);
				sysDptAreaMapper.insertSelective(sysDptArea);
			}
		}

		//添加机构品牌关联信息
		SysDptBrand sysDptBrand=null;
		List<TBrandInfo> brandList=sysDept.getBrandList();
		if(null!=brandList && brandList.size()>0){
			for(TBrandInfo brandInfo :brandList){
				sysDptBrand=new SysDptBrand();
				sysDptBrand.setBrandId(brandInfo.getId());
				sysDptBrand.setDptId(Long.parseLong(deptId+""));
				sysDptBrand.setCreateTime(new Date());
				sysDptBrand.setCreateUserId(1L);
				sysDptBrand.setIsDel(false);
				sysDptBrandMapper.insertSelective(sysDptBrand);
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public  void updateDept(SysDept sysDept){
		sysDept.setModifyTime(new Date());
		sysDept.setModifyUserId(1L);
		sysDeptMapper.updateByPrimaryKeySelective(sysDept);

		//添加机构地区关联信息
		List<SysArea> areaList=sysDept.getAreaList();
		SysDptArea sysDptArea=null;
		if(null!=areaList && areaList.size()>0){
			//先删除 该机构之前绑定的 地区信息
			sysDptArea=new SysDptArea();
			sysDptArea.setDptId(sysDept.getId());
			sysDptAreaMapper.delete(sysDptArea);

			//再添加机构新绑定的地区信息
			for(SysArea area :areaList){
				sysDptArea=new SysDptArea();
				sysDptArea.setAreaId(area.getId());
				sysDptArea.setDptId(sysDept.getId());
				sysDptArea.setCreateTime(new Date());
				sysDptArea.setCreateUserId(1L);
				sysDptArea.setIsDel(false);
				sysDptAreaMapper.insertSelective(sysDptArea);
			}
		}

		SysDptBrand sysDptBrand=null;
		//先删除 该机构之前绑定的 品牌信息
		sysDptBrand=new SysDptBrand();
		sysDptBrand.setDptId(sysDept.getId());
		sysDptBrandMapper.delete(sysDptBrand);

		//再添加机构品牌关联信息
		List<TBrandInfo> brandList=sysDept.getBrandList();
		if(null!=brandList && brandList.size()>0){
			for(TBrandInfo brandInfo :brandList){
				sysDptBrand=new SysDptBrand();
				sysDptBrand.setBrandId(brandInfo.getId());
				sysDptBrand.setDptId(sysDept.getId());
				sysDptBrand.setCreateTime(new Date());
				sysDptBrand.setCreateUserId(1L);
				sysDptBrand.setIsDel(false);
				sysDptBrandMapper.insertSelective(sysDptBrand);
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDept(SysDept sysDept){
		SysDptArea sysDptArea=new SysDptArea();
		sysDptArea.setDptId(sysDept.getId());
		sysDptAreaMapper.delete(sysDptArea);

		SysDptBrand sysDptBrand=new SysDptBrand();
		sysDptBrand.setDptId(sysDept.getId());
		sysDptBrandMapper.delete(sysDptBrand);

		sysDeptMapper.delete(sysDept);
	}


}
