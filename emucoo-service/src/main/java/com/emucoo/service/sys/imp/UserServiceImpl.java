package com.emucoo.service.sys.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.utils.SHA1Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.emucoo.service.sys.UserService;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.date.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by fujg on 2017/1/19.
 */
@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Transactional(readOnly=true)
    @Override
    public PageInfo<User> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime) throws Exception {
        Example example = new Example(User.class);
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(username)) {
            criteria.andLike("username", "%" + username + "%");
        }
        if (startTime != null && endTime != null) {
            criteria.andBetween("createTime", DateUtil.beginOfDay(DateUtil.parse(startTime)), DateUtil.endOfDay(DateUtil.parse(endTime)));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = this.selectByExample(example);

        for (User user : userList) {
            Role role = roleMapper.findByUserId(user.getId());
            if (null != role) {
                user.setRealName(role.getName());
            }
        }
        return new PageInfo<User>(userList);
    }

    @Override
    public List<User> listUser(String username, String realName,
                               String mobile, String email, List<Long> labels,
                               Integer status) {
        return userMapper.listUserVo(username, realName, mobile, email, labels, status);
    }

    @Transactional(readOnly=true)
    @Override
    public User findByUserName(String username) {
        User user = new User();
        user.setUsername(username);
        return this.findOne(user);
    }

    @Override
    public Boolean saveUserAndUserRole(User user, Long roleId) throws Exception{
        int count = 0;
        //加密
        user.setPassword(SecureUtil.md5().digestHex(user.getPassword()));
        user.setIsLock(false);
        user.setIsDel(false);
        Role role = roleMapper.selectByPrimaryKey(roleId);
//        if(Role.ROLE_TYPE.equalsIgnoreCase(role.getPerms())){
//            user.setIsAdmin(true);
//        }else {
//            user.setIsAdmin(false);
//        }
        count = this.save(user);

        //关联用户和角色信息
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(user.getId());
        userRole.setCreateTime(user.getCreateTime());
        userRole.setModifyTime(user.getCreateTime());
        count = userRoleMapper.insert(userRole);

        return count == 1;
    }

    @Override
    public Boolean updateUserAndUserRole(User user, Long oldRoleId, Long roleId) throws Exception {
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
            UserRole userRole = new UserRole();
            userRole.setRoleId(oldRoleId);
            userRole.setUserId(user.getId());
            UserRole ur = userRoleMapper.selectOne(userRole);
            ur.setRoleId(roleId);
            ur.setModifyTime(user.getModifyTime());
            count = userRoleMapper.updateByPrimaryKeySelective(ur);
        }
        return count == 1;
    }

    @Override
    public int updateLabels(Long id , String labels){
        User user = new User();
        user.setId(id);
        user.setLbIds(labels);
        return userMapper.updateByPrimaryKeySelective(user);
    }
    @Override
    public Integer updateByExampleSelective(User record,Example example){
        return userMapper.updateByExampleSelective(record,example);
    }
    @Override
    public User findUserByMobile(String mobile){
        if(StringUtils.isBlank(mobile)){
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("mobile",mobile);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
    @Override
    public String encryPasswor(String password) {
        return SHA1Util.encodeBase64(password.toLowerCase ().substring(4, 28) + "emucoo").substring(4, 24);
    }
}
