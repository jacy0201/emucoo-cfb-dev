package com.emucoo.service.sys.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.emucoo.mapper.PermissionMapper;
import com.emucoo.mapper.RolePermissionMapper;
import com.emucoo.model.Permission;
import com.emucoo.model.RolePermission;
import com.emucoo.model.TreeNode;
import com.emucoo.service.sys.PermissionService;
import com.xiaoleilu.hutool.util.StrUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by fujg on 2017/2/8.
 */
//@CacheConfig(cacheNames = "permission")
@Transactional
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(readOnly=true)
    @Override
    public PageInfo<Permission> findPage(Integer pageNum, Integer pageSize, String name) {
        Example example = new Example(Permission.class);
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(name)){
            criteria.andLike("name", "%"+name+"%");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Permission> PermissionList = this.selectByExample(example);
        return new PageInfo<Permission>(PermissionList);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Permission> findListPermissionByUserId(Long userId) {
        return permissionMapper.findListPermissionByUserId(userId);
    }

    @Cacheable(value = "menuListCache", key = "#userId")
    @Transactional(readOnly=true)
    @Override
    public List<Permission> findMenuListByUserId(Long userId) {
        return permissionMapper.findMenuListByUserId(userId);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Permission> findListByType(String type) {
        Permission permission = new Permission();
        permission.setType(type);
        return this.findListByWhere(permission);
    }

    @Transactional(readOnly=true)
    @Override
    public List<TreeNode> findTreeList() {
        return permissionMapper.findTreeList();
    }

    @Override
    public Boolean deletePermissionAndRolePermissionByPermissionId(Long permissionId) {
        //删除权限
        int count1 = this.deleteById(permissionId);

        //删除该权限和角色的关联信息
        RolePermission rolePermission = new RolePermission();
        rolePermission.setPermissionId(permissionId);
        rolePermissionMapper.delete(rolePermission);
        return count1 == 1;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Permission> findListByMenuName(String menuName) {
        Example example = new Example(Permission.class);
        Criteria criteria = example.createCriteria();

        if(StrUtil.isNotEmpty(menuName)){
            criteria.andLike("name", "%"+menuName+"%");
        }
        return this.selectByExample(example);
    }
}
