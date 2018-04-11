package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysRoleMapper;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.model.SysRole;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by fujg on 2017/1/19.
 */
@Transactional
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
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
        List<SysUser> userList = userMapper.selectByExample(example);

        for (SysUser user : userList) {
            SysRole role = roleMapper.findByUserId(user.getId());
            if (null != role) {
                user.setRealName(role.getRoleName());
            }
        }
        return new PageInfo<SysUser>(userList);
    }

}
