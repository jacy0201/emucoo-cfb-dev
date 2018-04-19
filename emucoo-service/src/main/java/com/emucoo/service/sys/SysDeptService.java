package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.sys.DeptQuery;
import com.emucoo.model.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 *
 */
public interface SysDeptService extends BaseService<SysDept> {

	List<SysDept> queryList(DeptQuery deptQuery);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);


	/**
	 * 保存机构信息
	 * @param sysDept
	 */
	void saveDept(SysDept sysDept);

	/**
	 * 更新机构信息
	 * @param sysDept
	 */
	void updateDept(SysDept sysDept);

	/**
	 * 删除机构信息
	 * @param sysDept
	 */
	void deleteDept(SysDept sysDept);

}
