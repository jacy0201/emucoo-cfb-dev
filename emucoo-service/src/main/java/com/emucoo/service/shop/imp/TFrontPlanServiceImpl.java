package com.emucoo.service.shop.imp;

import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.GetReportListOut;
import com.emucoo.dto.modules.report.GetReportOut;
import com.emucoo.dto.modules.report.ReportBaseInfoIn;
import com.emucoo.dto.modules.report.ReportBaseInfoOut;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.dto.modules.report.SaveReportOut;
import com.emucoo.mapper.DepartmentMapper;
import com.emucoo.mapper.RoleMapper;
import com.emucoo.mapper.UserMapper;
import com.emucoo.mapper.shop.TFrontReportMapper;
import com.emucoo.model.Department;
import com.emucoo.model.Role;
import com.emucoo.model.User;
import com.emucoo.model.shop.TFrontPlan;
import com.emucoo.model.shop.TFrontReport;
import com.emucoo.service.shop.TFrontPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/3/28.
 */
@Service
public class TFrontPlanServiceImpl implements TFrontPlanService{
    @Autowired
    private TFrontReportMapper tFrontReportMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Transactional
    public SaveReportOut saveReport(SaveReportIn vo) {
        TFrontReport report = new TFrontReport();
        // 查找打表人对应的部门
        Department department = tFrontReportMapper.findDptByReporterId(Long.valueOf(vo.getReporterID()));
        report.setReporterDptName(department.getDptname());
        // 查找检查表对应的店铺
        Department shop = tFrontReportMapper.findShopByArrangeId(Long.valueOf(vo.getPatrolShopArrangeID()));
        report.setShopName(shop.getDptname());
        report.setValue(vo.getReportValue());
        User reporter = new User();
        reporter.setId(Long.valueOf(vo.getReporterID()));
        report.setReporter(reporter);
        tFrontReportMapper.saveReport(report);
        TFrontPlan tFrontPlan = new TFrontPlan();
        tFrontPlan.setReportId(report.getId());
        tFrontPlan.setId(Long.valueOf(vo.getPatrolShopArrangeID()));
        tFrontReportMapper.addReportAndPlanRelation(tFrontPlan);
        SaveReportOut saveReportOut = new SaveReportOut();
        saveReportOut.setReportID(report.getId());
        return saveReportOut;
    }

    public void addReportAndPlanRelation(TFrontPlan tFrontPlan) {
        tFrontReportMapper.addReportAndPlanRelation(tFrontPlan);
    }

    public GetReportOut getReportById(GetReportIn vo) {
        TFrontReport tFrontReport = tFrontReportMapper.getReportById(Long.valueOf(vo.getReportID()));
        GetReportOut getReportOut = new GetReportOut();
        getReportOut.setReportID(tFrontReport.getId());
        getReportOut.setReportValue(tFrontReport.getValue());
        return getReportOut;
    }

    public List<GetReportListOut> getReportList() {
        List<TFrontReport> reportList = tFrontReportMapper.getReportList();
        List<GetReportListOut> reportListOutList = new ArrayList<>();
        for(TFrontReport frontReport: reportList) {
            GetReportListOut getReportListOut = new GetReportListOut();
            getReportListOut.setReportID(frontReport.getId());
            getReportListOut.setDptName(frontReport.getReporterDptName());
            getReportListOut.setReporter(frontReport.getReporter().getRealName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createDate = sdf.format(frontReport.getCreateTime());
            getReportListOut.setSheetName(frontReport.getShopName() + "评估表");
            getReportListOut.setCreateDate(createDate);
            reportListOutList.add(getReportListOut);
        }

        return reportListOutList;
    }

    public ReportBaseInfoOut getReportBaseInfo(ReportBaseInfoIn vo) {
        ReportBaseInfoOut reportBaseInfoOut = new ReportBaseInfoOut();
        // 查询店铺名
        Department department = tFrontReportMapper.findShopByArrangeId(Long.valueOf(vo.getPatrolShopArrangeID()));
        reportBaseInfoOut.setShopName(department.getDptname());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        reportBaseInfoOut.setCheckDate(date);
        // 查询打表人所属部门
        department = tFrontReportMapper.findDptByReporterId(Long.valueOf(vo.getReporterId()));
        reportBaseInfoOut.setCheckDepartmentName(department.getDptname());
        User user = userMapper.findById(Long.valueOf(vo.getReporterId()));
        reportBaseInfoOut.setInspectorName(user.getRealName());
        Role role = roleMapper.findByUserId(Long.valueOf(vo.getReporterId()));
         reportBaseInfoOut.setInspectorPosition(role.getName());
        user = userMapper.findByDptId(Long.valueOf(vo.getShopID()));
        reportBaseInfoOut.setShopownerName(user.getRealName());
        return reportBaseInfoOut;
    }
}
