package com.emucoo.service.sys.imp;

import com.google.common.collect.Lists;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.modules.dept.UserDeptVo;
import com.emucoo.dto.modules.sys.UserRoleVo;
import com.emucoo.mapper.DeptRoleUnionMapper;
import com.emucoo.model.DeptRoleUnion;
import com.emucoo.service.sys.DeptRoleUnionService;
import com.emucoo.utils.DateUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.emucoo.dto.base.ISystem.UN_MATCH_ROLE;
import static com.emucoo.dto.base.ISystem.UN_MATCH_USER;

@Service
public class DeptRoleUnionServiceImpl extends BaseServiceImpl<DeptRoleUnion> implements DeptRoleUnionService {

    @Resource
    private DeptRoleUnionMapper deptRoleUnionMapper;

    @Override
    public void updateUserRoles(Long userId , List<UserRoleVo> roleList) {
        //delet old user - role
        Example delExam = new Example(DeptRoleUnion.class);
        delExam.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("dptId", ISystem.UN_MATCH_DPT)
                .andNotEqualTo("roleId", UN_MATCH_ROLE);
        deptRoleUnionMapper.deleteByExample(delExam);

        if(CollectionUtils.isEmpty(roleList)){
            return;
        }
        // add new user - role
        Date currentDate = DateUtil.currentDate();
        List<DeptRoleUnion> deptRoleUnionList = roleList.stream()
                .map(userRoleVo -> {
                    DeptRoleUnion deptRoleUnion = new DeptRoleUnion();
                    deptRoleUnion.setDptId( ISystem.UN_MATCH_DPT);
                    deptRoleUnion.setUserId(userId);
                    deptRoleUnion.setRoleId(userRoleVo.getRoleId());
                    deptRoleUnion.setRoleName(userRoleVo.getRoleName());
                    deptRoleUnion.setCreateTime(currentDate);
                    deptRoleUnion.setModifyTime(currentDate);
                    return deptRoleUnion;
                }).collect(Collectors.toList());
        deptRoleUnionMapper.insertList(deptRoleUnionList);
    }

    @Override
    public List<String> listUserRoleName(Long userId){
        Example example = new Example(DeptRoleUnion.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<DeptRoleUnion> deptRoleUnionList = deptRoleUnionMapper.selectByExample(example);
        return deptRoleUnionList.stream().map(DeptRoleUnion::getRoleName).collect(Collectors.toList());
    }

    @Override
    public Boolean updateDeptRoleRlat(Long deptId, List<String> addRoles) {

        Example example = new Example (DeptRoleUnion.class);
        example.createCriteria().andEqualTo ("userId",UN_MATCH_USER)
                .andEqualTo ("dptId",deptId);
        deptRoleUnionMapper.deleteByExample (example);

        Date today = new Date ();
        List<DeptRoleUnion> deptRoleUnions = Lists.newArrayList ();
        addRoles.forEach (role->{
            DeptRoleUnion deptRoleUnion = new DeptRoleUnion ();
            deptRoleUnion.setDptId (deptId);
            deptRoleUnion.setRoleId (Long.valueOf (role));
            deptRoleUnion.setModifyTime (today);
            deptRoleUnion.setCreateTime (today);
            deptRoleUnion.setUserId (UN_MATCH_USER);
            deptRoleUnions.add (deptRoleUnion);
        });
        if(CollectionUtils.isNotEmpty (deptRoleUnions)){
            deptRoleUnionMapper.insertList (deptRoleUnions);
        }
        return true;
    }

    @Override
    public List<UserDeptVo> listUserDeptVo(Long userId){
        return deptRoleUnionMapper.listUserDeptVo(userId);
    }
}
