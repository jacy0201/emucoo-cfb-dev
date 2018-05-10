package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysPostMapper;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.mapper.SysUserRelationMapper;
import com.emucoo.model.SysUserRelation;
import com.emucoo.service.sys.SysUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class SysUserRelationServiceImpl extends BaseServiceImpl<SysUserRelation> implements SysUserRelationService {

    @Autowired
    private SysUserRelationMapper sysUserRelationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPostMapper sysPostMapper;

    @Override
   public List<SysUserRelation> listUserRelation(Long dptId){
        Example example=new Example(SysUserRelation.class);
        example.createCriteria().andEqualTo("dptId",dptId);
       List<SysUserRelation> list=sysUserRelationMapper.selectByExample(example);
       for (SysUserRelation sysUserRelation :list){
           if(null!=sysUserRelation.getParentUserId()){
              sysUserRelation.setParentUserName(sysUserMapper.selectByPrimaryKey(sysUserRelation.getParentUserId()).getRealName());
           }
           sysUserRelation.setUserName(sysUserMapper.selectByPrimaryKey(sysUserRelation.getUserId()).getRealName());
           if(null!=sysUserRelation.getPostId()) {
               sysUserRelation.setPostName(sysPostMapper.selectByPrimaryKey(sysUserRelation.getPostId()).getPostName());
           }
           if(null!=sysUserRelation.getParentPostId()){
              sysUserRelation.setParentPostName(sysPostMapper.selectByPrimaryKey(sysUserRelation.getParentPostId()).getPostName());
           }

       }
       return list;
   }
}
