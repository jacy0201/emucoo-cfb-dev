package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysRoleMapper;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.mapper.SysUserRoleMapper;
import com.emucoo.model.SysRole;
import com.emucoo.model.SysUser;
import com.emucoo.model.SysUserRole;
import com.emucoo.service.sys.UserService;
import com.emucoo.utils.SHA1Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 *
 * Created by fujg on 2017/1/19.
 */
@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<SysUser> implements UserService{

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysRoleMapper roleMapper;

    @Transactional(readOnly=true)
    @Override
    public PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime) throws Exception {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(username)) {
            criteria.andLike("username", "%" + username + "%");
        }
        if (startTime != null && endTime != null) {
            criteria.andBetween("createTime", DateUtil.beginOfDay(DateUtil.parse(startTime)), DateUtil.endOfDay(DateUtil.parse(endTime)));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> userList = this.selectByExample(example);

        for (SysUser user : userList) {
            SysRole role = roleMapper.findByUserId(user.getId());
            if (null != role) {
//                user.setRealName(role.getName()); 把角色名当做真实姓名有什么意思？
            }
        }
        return new PageInfo<SysUser>(userList);
    }

    @Override
    public List<SysUser> listUser(String username, String realName,
                               String mobile, String email, List<Long> labels,
                               Integer status) {
        return userMapper.listUserVo(username, realName, mobile, email, labels, status);
    }

    @Transactional(readOnly=true)
    @Override
    public SysUser findByUserName(String username) {
        SysUser user = new SysUser();
        user.setUsername(username);
        return this.findOne(user);
    }

    @Override
    public Boolean saveUserAndUserRole(SysUser user, Long roleId) throws Exception{
        int count = 0;
        //加密
        user.setPassword(SecureUtil.md5().digestHex(user.getPassword()));
        user.setIsLock(false);
        user.setIsDel(false);
        SysRole role = roleMapper.selectByPrimaryKey(roleId);
//        if(Role.ROLE_TYPE.equalsIgnoreCase(role.getPerms())){
//            user.setIsAdmin(true);
//        }else {
//            user.setIsAdmin(false);
//        }
        count = this.save(user);

        //关联用户和角色信息
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(user.getId());
        userRole.setCreateTime(user.getCreateTime());
        userRole.setModifyTime(user.getCreateTime());
        count = userRoleMapper.insert(userRole);

        return count == 1;
    }

    @Override
    public Boolean updateUserAndUserRole(SysUser user, Long oldRoleId, Long roleId) throws Exception {
        int count = 0;
        //加密
        user.setPassword(SecureUtil.md5().digestHex(user.getPassword()));
//        if(!oldRoleId.equals(roleId)){
//            Role role = roleMapper.selectByPrimaryKey(roleId);
//            if(Role.ROLE_TYPE.equalsIgnoreCase(role.getPerms())){
//                user.setIsAdmin(true);
//            }else {
//                user.setIsAdmin(false);
//            }
//        }
        count = this.updateSelective(user);

        //更新用户角色信息
        if(!oldRoleId.equals(roleId)){
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(oldRoleId);
            userRole.setUserId(user.getId());
            SysUserRole ur = userRoleMapper.selectOne(userRole);
            ur.setRoleId(roleId);
            ur.setModifyTime(user.getModifyTime());
            count = userRoleMapper.updateByPrimaryKeySelective(ur);
        }
        return count == 1;
    }

    @Override
    public int updateLabels(Long id , String labels){
        SysUser user = new SysUser();
        user.setId(id);
//        user.setLbIds(labels);
        return userMapper.updateByPrimaryKeySelective(user);
    }
    @Override
    public Integer updateByExampleSelective(SysUser record,Example example){
        return userMapper.updateByExampleSelective(record,example);
    }
    @Override
    public SysUser findUserByMobile(String mobile){
        if(StringUtils.isBlank(mobile)){
            return null;
        }
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("mobile",mobile);
        List<SysUser> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
    @Override
    public String encryPasswor(String password) {
        return SHA1Util.encodeBase64(password.toLowerCase ().substring(4, 28) + "emucoo").substring(4, 24);
    }

    @Override
    public SysUser findByEmail(String email) {
        if(StringUtils.isBlank(email)){
            return null;
        }
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("email", email);
        List<SysUser> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
}
