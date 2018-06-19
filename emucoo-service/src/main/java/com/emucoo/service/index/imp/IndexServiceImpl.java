package com.emucoo.service.index.imp;

import com.alibaba.druid.util.StringUtils;
import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.exception.ApiException;
import com.emucoo.common.util.RegexMatcher;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.index.IndexService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("AliControlFlowStatementWithoutBraces")
@Transactional
@Service
public class IndexServiceImpl extends BaseServiceImpl<SysUser> implements IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private TReportMapper reportMapper;

    @Autowired
    private TRepairWorkMapper repairWorkMapper;


    @Override
    public UserLoginInfo authenticate(String mobile, String password, String pushToken) {

        SysUser user = null;
        if (RegexMatcher.isPhoneNumber(mobile)) {
            user = userMapper.fetchOneByMobile(mobile);
        } else if (RegexMatcher.isEmail(mobile)) {
            user = userMapper.fetchOneByEmail(mobile);
        } else {
            user = userMapper.fetchOneByUsername(mobile);
        }

        if (user == null) {
            return null;
        }

        if (0 != user.getStatus()) {
            return null;
        }

        if (!StringUtils.equalsIgnoreCase(user.getPassword(), new Sha256Hash(password, user.getSalt()).toHex())) {
            return null;
        }

        //更新用户 push token
        user.setPushToken(pushToken);
        //如果用户没传token 需要设置为null
        userMapper.updateByPrimaryKeySelective(user);

        UserLoginInfo loginInfo = new UserLoginInfo();

	/*	SysDept d = new SysDept();
		d.setId(user.getDptId());*/
        SysDept dept = sysDeptMapper.selectByPrimaryKey(user.getDptId());
        if (dept != null) {
            loginInfo.setUserType("");
            loginInfo.setDepartmentID(dept.getId());
            loginInfo.setDepartmentName(dept.getDptName());
            loginInfo.setDepartmentType(2);
            loginInfo.setUsePlan(dept.getUsePlan());
        }

        List<TShopInfo> shopInfos = shopInfoMapper.fetchShopsOfUser(user.getId());
        List<UserLoginInfo.shop> shoptList = shopInfos.stream().map(shopInfo -> {
            UserLoginInfo.shop shop = new UserLoginInfo.shop();
            shop.setShopName(shopInfo.getShopName());
            shop.setShopID(shopInfo.getId());
            return shop;
        }).collect(Collectors.toList());

        loginInfo.setShopNameArr(shoptList);
        loginInfo.setHeadImgUrl(user.getHeadImgUrl());
        loginInfo.setUserName(user.getRealName());
        loginInfo.setUserId(user.getId());
        loginInfo.setIsShopManager(user.getIsShopManager());
        loginInfo.setIsRepairMan(user.getRepairer());

        return loginInfo;
    }

    @Override
    public void resetPwd(ResetPwdVo_I vo, String vcode) {
        // 短信验证
        if (vo.getSmsCode().equals(vcode)) {
            String salt = RandomStringUtils.randomAlphanumeric(20);
            //userMapper.resetPwd(vo.getMobile(), new Sha256Hash(vo.getPassword(),salt).toHex());
            Example example = new Example(SysUser.class);
            example.createCriteria().andEqualTo("mobile", vo.getMobile());
            SysUser sysUser = new SysUser();
            sysUser.setSalt(salt);
            sysUser.setModifyTime(new Date());
            sysUser.setPassword(new Sha256Hash(vo.getPassword(), salt).toHex());
            userMapper.updateByExampleSelective(sysUser, example);
        }
    }

    @Override
    public void loginOut(String pushToken) {
        if (pushToken != null && !"".equals(pushToken)) {
            userMapper.loginOut(pushToken);
        }
    }

    @Override
    public List<ReportItemVo> fetchUnReadReports(long currUserId) {
        try {
            List<ReportItemVo> reportItemVos = new ArrayList<>();
            List<TReport> reports = reportMapper.findReportByUser(currUserId);
            for (TReport report : reports) {
                ReportItemVo reportItemVo = new ReportItemVo();
                reportItemVo.setIsRead(report.getReportUser().getIsRead());
                reportItemVo.setReportTitle(report.getShopName() + "评估表");
                reportItemVo.setReporterName(report.getReporterName());
                if(null!=report.getFormType()) {
                    reportItemVo.setReportType(report.getFormType().intValue());
                }
                reportItemVo.setReportID(report.getId());
                reportItemVo.setReporterHeadUrl(report.getReporterHeadImgUrl());
                reportItemVo.setReportSourceName(report.getReporterDptName());
                reportItemVo.setReportTime(report.getCreateTime().getTime());
                reportItemVos.add(reportItemVo);
            }
            return reportItemVos;
        } catch (Exception e) {
            logger.error("读取报告列表有误！", e);
            throw new ApiException("读取报告列表有误！");
        }

        //return reportMapper.fetchUnReadReport(currUserId);
    }

    @Override
    public Integer reportNum(Long userId, Boolean isRead) {
        return reportMapper.countUnReadReport(userId);
    }

    @Override
    public List<TRepairWork> fetchPendingRepairWorks(Long loginUserId) {
        return repairWorkMapper.filterPendingRepairWorks(loginUserId);
    }
}
