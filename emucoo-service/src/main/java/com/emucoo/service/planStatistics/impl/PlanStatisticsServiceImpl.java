package com.emucoo.service.planStatistics.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.statistics.plan.GeneratePlanReportIn;
import com.emucoo.enums.FormType;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.planStatistics.PlanStatisticsService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/5/31.
 */
@Service
public class PlanStatisticsServiceImpl implements PlanStatisticsService {
    private Logger logger = LoggerFactory.getLogger(PlanStatisticsServiceImpl.class);

    @Autowired
    private TReportMapper reportMapper;

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private SysDistrictMapper sysDistrictMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TBrandInfoMapper brandInfoMapper;

    @Autowired
    private TFrontPlanMapper frontPlanMapper;

    @Autowired
    private TFormCheckResultMapper formCheckResultMapper;

    @Autowired
    private TFormTypeMapper formTypeMapper;

    @Autowired
    private TFormValueMapper formValueMapper;

    @Autowired
    private TFormScoreItemMapper formScoreItemMapper;


    @Override
    public List<RVRReportStatistics> generatePlanReport(GeneratePlanReportIn param){
        try {
            Date beginReportDate = DateUtil.strToYYMMDDDate(param.getBeginDate());
            Date endReportDate = DateUtil.strToYYMMDDDate(param.getEndDate());
            // 读取报告列表
            /*Example reportExp = new Example(TReport.class);
            reportExp.createCriteria().andEqualTo("formType", param.getFormType()).andBetween("createTime", beginReportDate, endReportDate);
            List<TReport> reportList = reportMapper.selectByExample(reportExp);*/

            List<TReport> reportList = reportMapper.findReportByFormIdAndTime(param.getFormId(), beginReportDate, endReportDate);

            if (CollectionUtils.isNotEmpty(reportList)) {
                if(param.getFormType().equals(FormType.RVR_TYPE.getCode())) {
                    SysDistrict districtParam = new SysDistrict();
                    Example formTypeValExp = new Example(TFormValue.class);
                    List<RVRReportStatistics> rvrReportStatisticses = new ArrayList<>();
                    for(TReport report : reportList) {
                        RVRReportStatistics statistics = new RVRReportStatistics();
                        // 获取店铺
                        Long shopId = report.getShopId();
                        TShopInfo shop = shopInfoMapper.selectByPrimaryKey(shopId);
                        if(shop != null) {
                            statistics.setCode(shop.getShopCode());
                            statistics.setShopName(shop.getShopName());
                            //statistics.setShopType(ShopType.getName(shop.getType()));
                        }

                        if(StringUtils.isNotBlank(shop.getCity())) {
                            // 获取店铺城市
                            districtParam.setAreaCode(shop.getCity());
                            SysDistrict district = sysDistrictMapper.selectOne(districtParam);
                            if (district != null) {
                                statistics.setCity(district.getAreaName());
                            }
                        }

                        if(shop.getBrandId() != null) {
                            //获取品牌信息
                            TBrandInfo brandInfo = brandInfoMapper.selectByPrimaryKey(shop.getBrandId());
                            statistics.setBrandName(brandInfo.getBrandName());
                        }

                        //获取区域信息
                        if (shop.getAreaId() != null) {
                            SysArea area = sysAreaMapper.selectByPrimaryKey(shop.getAreaId());
                            statistics.setAreaName(area.getAreaName());
                        }
                        // 打表人信息
                        statistics.setReporterPosition(report.getReporterPosition());
                        statistics.setReporterName(report.getReporterName());

                        TFormCheckResult result = formCheckResultMapper.selectByPrimaryKey(report.getFormResultId());
                        // 查询题项类别结果
                        formTypeValExp.clear();
                        formTypeValExp.createCriteria().andEqualTo("formResultId", report.getFormResultId());
                        List<TFormValue> formValues = formValueMapper.selectByExample(formTypeValExp);

                        // 计分项规则
                        Example formScoreExp = new Example(TFormScoreItem.class);
                        formScoreExp.createCriteria().andEqualTo("formMainId", param.getFormId());
                        formScoreExp.setOrderByClause("score_begin desc");
                        List<TFormScoreItem> tFormScoreItems = formScoreItemMapper.selectByExample(formScoreExp);
                        if (tFormScoreItems.size() > 0) {
                            int scoreSummary = 0 + 'A';
                            int scoreCnt = 0;
                            for (TFormScoreItem tFormScoreItem : tFormScoreItems) {
                                if (result.getScoreRate() >= tFormScoreItem.getScoreBegin()) {
                                    scoreSummary += scoreCnt;
                                    break;
                                }
                                scoreCnt++;
                            }
                            statistics.setLevel(String.valueOf((char)scoreSummary));
                        }

                        statistics.setOtherValueList(formValues);
                        TFrontPlan frontPlan = frontPlanMapper.selectByPrimaryKey(result.getFrontPlanId());
                        // 巡店安排
                        statistics.setPlanDate(DateUtil.dateToString1(frontPlan.getPlanDate()));
                        statistics.setActualPatrolShopDate(DateUtil.dateToString1(frontPlan.getActualExecuteTime()));
                        statistics.setActualPatrolShopAddress(frontPlan.getActualExecuteAddress());
                        if(frontPlan.getActualExecuteTime() != null) {
                            statistics.setActualPatrolShopTime(DateUtil.dateToString(frontPlan.getActualExecuteTime()));
                        }

                        statistics.setReportTime(DateUtil.dateToString(report.getCreateTime()));
                        int realTotal = result.getActualTotal();
                        statistics.setActualTotalscore(String.valueOf(realTotal));
                        statistics.setActualTotalRate(String.valueOf(Math.floor(result.getScoreRate())));

                        rvrReportStatisticses.add(statistics);
                    }

                    return rvrReportStatisticses;
                }

            }
        } catch (Exception e) {
            logger.error("生成报表失败！", e);
            throw new ApiException("生成报表失败！");
        }
        return null;
    }

    public List<TFormType> findFormTypeValues(Long formId) {
        Example formTypeExp = new Example(TFormType.class);
        formTypeExp.createCriteria().andEqualTo("formMainId", formId);
        List<TFormType> formTypes = formTypeMapper.selectByExample(formTypeExp);
        /*List<String> otherTitleList = new ArrayList<>();
        for (TFormType formType : formTypes) {
            otherTitleList.add(formType.getTypeName());
        }
        return otherTitleList;*/
        return formTypes;
    }
}
