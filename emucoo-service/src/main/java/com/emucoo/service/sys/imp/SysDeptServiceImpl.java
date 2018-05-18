package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.sys.DeptQuery;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.sys.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
	public List<SysDept> queryList(DeptQuery deptQuery){
		Example example=new Example(SysDept.class);
		Example.Criteria criteria = example.createCriteria();
		Long brandId=null;
		Long areaId=null;
		String dptName="";
		Boolean isUse=null;
		List<SysDept> deptList=null;
		criteria.andEqualTo("isDel",false);
		if(null!=deptQuery && null!=deptQuery.getDptName() && !"".equals(deptQuery.getDptName())){
			dptName=deptQuery.getDptName();
			criteria.andLike("dptName","%"+dptName+"%");
		}
		if(null!=deptQuery && null!=deptQuery.getIsUse()){
			isUse=deptQuery.getIsUse();
			criteria.andEqualTo("isUse",isUse);
		}

		HashMap paramMap=null;
		List<SysDept> listB=null;
		List<SysDept> listA=null;
		if(null!=deptQuery && null!=deptQuery.getBrandId()){
			paramMap=new HashMap();
			brandId=deptQuery.getBrandId();
			paramMap.put("brandId",brandId);
			paramMap.put("dptName",dptName);
			paramMap.put("isUse",isUse);
			listB=sysDeptMapper.listByBrand(paramMap);
		}

		if(null!=deptQuery && null!=deptQuery.getAreaId()){
			paramMap=new HashMap();
			areaId=deptQuery.getAreaId();
			paramMap.put("areaId",areaId);
			paramMap.put("dptName",dptName);
			paramMap.put("isUse",isUse);
			listA=sysDeptMapper.listByArea(paramMap);
		}

		if(null!=areaId  && null==brandId){ deptList=listA; }
		else if(null == areaId && null!=brandId){deptList=listB;}
		else if(null == areaId && null ==brandId){deptList =sysDeptMapper.selectByExample(example); }
		else if(null!=areaId && null!=brandId){
			if(null!=listA && null!=listB){
				deptList=new ArrayList<>();
				for(SysDept sysDept1 :listA){
					for (SysDept sysDept2 :listB){
						if(sysDept1.getId()==sysDept2.getId()){
							deptList.add(sysDept1);
						}
					}
				}
			}
		}
		if(null!=deptList && deptList.size()>0) {
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
			if(null!=list && list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveDept(SysDept sysDept){
		sysDept.setIsDel(false);
		sysDept.setIsUse(false);
		sysDept.setCreateTime(new Date());
		//一级机构的 父ID为0
		if(null==sysDept.getParentId()){
			sysDept.setParentId(0L);
		}
		sysDeptMapper.insertUseGeneratedKeys(sysDept);
		Long deptId=sysDept.getId();

		//添加机构地区关联信息
		List<SysArea> areaList=sysDept.getAreaList();
		SysDptArea sysDptArea=null;
		if(null!=areaList && areaList.size()>0){
			for(SysArea area :areaList){
				sysDptArea=new SysDptArea();
				sysDptArea.setAreaId(area.getId());
				sysDptArea.setDptId(deptId);
				sysDptArea.setCreateTime(new Date());
				sysDptArea.setCreateUserId(sysDept.getCreateUserId());
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
				sysDptBrand.setCreateUserId(sysDept.getCreateUserId());
				sysDptBrand.setIsDel(false);
				sysDptBrandMapper.insertSelective(sysDptBrand);
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public  void updateDept(SysDept sysDept){
		sysDept.setModifyTime(new Date());
		sysDept.setModifyUserId(sysDept.getCreateUserId());
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
				sysDptArea.setCreateUserId(sysDept.getCreateUserId());
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
				sysDptBrand.setCreateUserId(sysDept.getCreateUserId());
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

		sysDept.setIsDel(true);
		sysDeptMapper.updateByPrimaryKeySelective(sysDept);
	}


}
