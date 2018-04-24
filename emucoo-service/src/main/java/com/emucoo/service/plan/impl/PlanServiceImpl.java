package com.emucoo.service.plan.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.ServiceException;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.plan.FindPlanListIn;
import com.emucoo.dto.modules.plan.FindPlanListOut;
import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.dto.modules.plan.PatrolShopCycle;
import com.emucoo.dto.modules.plan.PlanProgressOut;
import com.emucoo.dto.modules.plan.PrecinctArr;
import com.emucoo.dto.modules.plan.ShopToPlanIn;
import com.emucoo.dto.modules.plan.ShopVo;
import com.emucoo.enums.Constant;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.ShopArrangeStatus;
import com.emucoo.mapper.SysAreaMapper;
import com.emucoo.mapper.SysDeptMapper;
import com.emucoo.mapper.SysDptBrandMapper;
import com.emucoo.mapper.TBrandInfoMapper;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.mapper.TLoopPlanMapper;
import com.emucoo.mapper.TLoopSubPlanMapper;
import com.emucoo.mapper.TPlanFormRelationMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysArea;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TLoopPlan;
import com.emucoo.model.TLoopSubPlan;
import com.emucoo.model.TPlanFormRelation;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.plan.PlanService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sj on 2018/4/17.
 */
@Service
public class PlanServiceImpl implements PlanService {

    private Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

    @Autowired
    private TShopInfoMapper tShopInfoMapper;

    @Autowired
    private TFrontPlanMapper tFrontPlanMapper;

    @Autowired
    private TLoopPlanMapper tLoopPlanMapper;

    @Autowired
    private TLoopSubPlanMapper tLoopSubPlanMapper;

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TBrandInfoMapper tBrandInfoMapper;

    @Autowired
    private TPlanFormRelationMapper tPlanFormRelationMapper;

    public FindShopListOut findShopList(SysUser user, FindShopListIn findShopListIn) {
        //查询品牌
        List<TBrandInfo> brandInfos = tBrandInfoMapper.findBrandListByUserId(user.getId());
        List<TShopInfo> shopList = tShopInfoMapper.selectShopListByUserAndAreaBrand(user.getId(), findShopListIn.getPrecinctID(), brandInfos);
        FindShopListOut findShopListOut = new FindShopListOut();
        List<ShopVo> shopVos = new ArrayList<ShopVo>();
        if(CollectionUtils.isNotEmpty(shopList)) {
            for(TShopInfo tShopInfo : shopList) {
                ShopVo shopVo = new ShopVo();
                shopVo.setShopID(tShopInfo.getId());
                shopVo.setShopName(tShopInfo.getShopName());
                shopVo.setBrandName(tShopInfo.getBrandName());
                shopVos.add(shopVo);
            }
            findShopListOut.setShopArr(shopVos);
        }

        return findShopListOut;
    }

    @Transactional
    public void addShopToPlan(SysUser user, ShopToPlanIn shopToPlanIn) {
        Long planId = shopToPlanIn.getPlanID();
        Date now = new Date();
        String year = shopToPlanIn.getPlanYearMonth().substring(0, 4);
        String month = shopToPlanIn.getPlanYearMonth().substring(4, 6);
        for(ShopVo shopVo : shopToPlanIn.getShopArr()) {
            TFrontPlan tFrontPlan = new TFrontPlan();
            TShopInfo tShopInfo = new TShopInfo();
            tShopInfo.setId(shopVo.getShopID());
            tFrontPlan.setShop(tShopInfo);
            tFrontPlan.setSubPlanId(planId);
            tFrontPlan.setStatus(ShopArrangeStatus.NOT_ARRANGE.getCode().byteValue());
            tFrontPlan.setArrangeYear(year);
            tFrontPlan.setArrangeMonth(month);
            tFrontPlan.setCreateTime(now);
            tFrontPlan.setModifyTime(now);
            tFrontPlan.setCreateUserId(user.getId());
            tFrontPlan.setModifyUserId(user.getId());
            tFrontPlan.setOrgId(Constant.orgId);
            tFrontPlan.setIsDel(DeleteStatus.COMMON.getCode());
            tFrontPlanMapper.addUnArrangeToPlan(tFrontPlan);
        }
    }

    public void deleteShopInPlan(ShopToPlanIn shopToPlanIn) {
        //String year = shopToPlanIn.getPlanYearMonth().substring(0, 4);
        //String month = shopToPlanIn.getPlanYearMonth().substring(4, 6);
        //Example example = new Example(TFrontPlan.class);
        //List<Long> shopIds = new ArrayList<>();
        List<Long> subIds = new ArrayList<>();
        for(ShopVo shopVo : shopToPlanIn.getShopArr()) {
            //shopIds.add(shopVo.getShopID());
            subIds.add(shopVo.getSubID());
        }
        //example.createCriteria().andIn("id", subIds);
        tFrontPlanMapper.deleteFrontPlanByIds(subIds);
    }

    @Transactional
    public FindPlanListOut listPlan(SysUser user, FindPlanListIn findPlanListIn) {
        FindPlanListOut findPlanListOut = new FindPlanListOut();
        try {
            String yearMonth = findPlanListIn.getMonth();
            String year = "";
            String month = "";
            // 如果不传计划月份参数,则调取当前月数据,否则取指定月数据
            if (StringUtil.isBlank(findPlanListIn.getMonth())) {
                Calendar calendar = Calendar.getInstance();
                year = String.valueOf(calendar.get(Calendar.YEAR));
                month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                if(month.length() < 2) {
                    month = "0" + month;
                }
                yearMonth = year + month;
            } else {
                year = yearMonth.substring(0,4);
                month = yearMonth.substring(4,6);
            }
            // 查询当前用户所在部门，当前时间是否有有效的巡店计划
            Date now = new Date();
            Example tLoopPlanExample = new Example(TLoopPlan.class);
            tLoopPlanExample.createCriteria().andLessThanOrEqualTo("planStartDate", yearMonth).andGreaterThanOrEqualTo("planEndDate", yearMonth)
            .andEqualTo("dptId", user.getDptId()).andEqualTo("isDel", false).andEqualTo("isUse", true);
            List<TLoopPlan> tLoopPlans = tLoopPlanMapper.selectByExample(tLoopPlanExample);
            if (tLoopPlans.size() > 1) {
                throw new ServiceException("不能有多个巡店计划！");
            } else if(tLoopPlans.size() == 0) {
                return null;
            }
            TLoopPlan tLoopPlan = tLoopPlans.get(0);
            Example tLoopSubPlanExample = new Example(TLoopSubPlan.class);
            tLoopSubPlanExample.createCriteria().andEqualTo("dptId", user.getDptId()).andLessThanOrEqualTo("cycleBegin", yearMonth)
                    .andGreaterThanOrEqualTo("cycleEnd", yearMonth).andEqualTo("isDel", false);
            List<TLoopSubPlan> tLoopSubPlans = tLoopSubPlanMapper.selectByExample(tLoopSubPlanExample);
            if(tLoopSubPlans.size() == 0) {
                TLoopSubPlan tLoopSubPlan = new TLoopSubPlan();
                // 计算当前周期月份
                int loopCycleCount = tLoopPlan.getPlanCycleCount();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
                Date planStartDate = monthSdf.parse(tLoopPlan.getPlanStartDate());
                cal.setTime(planStartDate);
                // 每个子周期数*已循环周期数+计划开始月份，等于当前新建周期开始的月份
                cal.add(Calendar.MONTH, loopCycleCount * tLoopPlan.getPlanCycle());
                String subCycyleStartDate = monthSdf.format(cal.getTime());
                if(subCycyleStartDate.compareTo(tLoopPlan.getPlanEndDate()) > 0) {
                    logger.info("计划id={}已结束！", tLoopPlan.getId());
                    return null;
                }
                tLoopSubPlan.setCycleBegin(monthSdf.format(cal.getTime()));
                // 计算当前子周期结束月份
                cal.add(Calendar.MONTH, tLoopPlan.getPlanCycle());
                String subCycleEndDate = monthSdf.format(cal.getTime()).compareTo(tLoopPlan.getPlanEndDate()) >= 0 ? tLoopPlan.getPlanEndDate() : monthSdf.format(cal.getTime());
                tLoopSubPlan.setCycleEnd(subCycleEndDate);
                // 组装月份列表给前端
                List<PatrolShopCycle> patrolShopCycles = new ArrayList<PatrolShopCycle>();
                Date cycleStartDate = monthSdf.parse(subCycyleStartDate);
                Date cycleEndDate = monthSdf.parse(subCycleEndDate);
                Calendar dd = Calendar.getInstance();//定义日期实例
                dd.setTime(cycleStartDate);//设置日期起始时间
                while (dd.getTime().before(cycleEndDate)) {//判断是否到结束日期
                    String monthStr = monthSdf.format(dd.getTime());
                    dd.add(Calendar.MONTH, 1);//进行当前日期月份加1

                    PatrolShopCycle patrolShopCycle = new PatrolShopCycle();
                    patrolShopCycle.setMonth(monthStr);
                    patrolShopCycle.setPlanID(tLoopSubPlan.getId());
                    patrolShopCycles.add(patrolShopCycle);
                }
                findPlanListOut.setPatrolShopCycle(patrolShopCycles);

                //组装
                tLoopSubPlan.setPlanId(tLoopPlan.getId());
                tLoopSubPlan.setDptId(user.getDptId());
                tLoopSubPlan.setCreateTime(now);
                tLoopSubPlan.setModifyTime(now);
                tLoopSubPlan.setIsDel(DeleteStatus.COMMON.getCode());
                // 新增子周期计划
                tLoopSubPlanMapper.addLoopSubPlan(tLoopSubPlan);
                //更新计划中的已执行周期数统计
                tLoopPlan.setPlanCycleCount(tLoopPlan.getPlanCycleCount() + 1);
                tLoopPlan.setModifyTime(now);
                tLoopPlan.setModifyUserId(user.getId());
                tLoopPlanMapper.updateByPrimaryKey(tLoopPlan);
            } else {
                TLoopSubPlan tLoopSubPlan = tLoopSubPlans.get(0);
                List<PatrolShopCycle> patrolShopCycles = new ArrayList<PatrolShopCycle>();
                Date cycleStartDate = new SimpleDateFormat("yyyyMM").parse(tLoopSubPlan.getCycleBegin());
                Date cycleEndDate = new SimpleDateFormat("yyyyMM").parse(tLoopSubPlan.getCycleEnd());
                Calendar dd = Calendar.getInstance();//定义日期实例
                dd.setTime(cycleStartDate);//设置日期起始时间
                Calendar end = Calendar.getInstance();
                end.setTime(cycleEndDate);
                end.add(Calendar.MONTH, 1);//日期结束时间
                while (dd.getTime().before(end.getTime())) {//判断是否到结束日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    String monthStr = sdf.format(dd.getTime());
                    dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
                    PatrolShopCycle patrolShopCycle = new PatrolShopCycle();
                    patrolShopCycle.setMonth(monthStr);
                    patrolShopCycle.setPlanID(tLoopSubPlan.getId());
                    patrolShopCycles.add(patrolShopCycle);
                }
                findPlanListOut.setPatrolShopCycle(patrolShopCycles);
            }

            // 获取分区
            List<SysArea> areaList = sysAreaMapper.findAreaListByUserId(user.getId());
            //查询品牌
            List<TBrandInfo> brandInfos = tBrandInfoMapper.findBrandListByUserId(user.getId());
            List<PrecinctArr> precinctArr = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            for (SysArea area : areaList) {
                PrecinctArr areaOut = new PrecinctArr();
                areaOut.setPrecinctID(area.getId());
                areaOut.setPrecinctName(area.getAreaName());
                // 获取巡店安排
                List<TFrontPlan> frontPlans = tFrontPlanMapper.findArrangeListByAreaId(area.getId(), year, month, brandInfos);
                if(CollectionUtils.isNotEmpty(frontPlans)) {
                    List<ShopVo> shopArr = new ArrayList<>();
                    for(TFrontPlan frontPlan : frontPlans) {
                        ShopVo shop = new ShopVo();
                        shop.setShopID(frontPlan.getShopId());
                        shop.setShopName(frontPlan.getShop().getShopName());
                        shop.setExPatrloShopArrangeDate(frontPlan.getPlanDate()==null?"": format.format(frontPlan.getPlanDate()));
                        shop.setShopStatus(frontPlan.getStatus().intValue());
                        shop.setSubID(frontPlan.getId());
                        shop.setPatrolShopArrangeID(frontPlan.getId());
                        shopArr.add(shop);
                    }
                    areaOut.setShopArr(shopArr);
                }
                areaOut.setShopNum(frontPlans.size());
                precinctArr.add(areaOut);
            }
            findPlanListOut.setPrecinctArr(precinctArr);
            return findPlanListOut;
        } catch (Exception e) {
            logger.error("用户:{}，查询巡店计划失败", user.getId(), e);
            throw new ApiException("查询巡店计划列表失败");
        }

    }

    public PlanProgressOut findPlanProcessByUserId(SysUser user) {
        PlanProgressOut planProgressOut = new PlanProgressOut();
        try {
            // 查询部门
            SysDept dept = sysDeptMapper.selectByPrimaryKey(user.getDptId());
            planProgressOut.setDepartmentID(dept.getId());
            planProgressOut.setDepartmentName(dept.getDptName());
            //查询品牌
            List<TBrandInfo> brandInfos = tBrandInfoMapper.findBrandListByUserId(user.getId());
            String brandName = "";
            for(TBrandInfo brandInfo : brandInfos) {
                brandName += brandInfo.getBrandName() + ",";
            }
            brandName = brandName.substring(0, brandName.length() - 1);
            planProgressOut.setBrandName(brandName);

            //计算进度
            Calendar calendar = Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            if (month.length() < 2) {
                month = "0" + month;
            }
            String yearMonth = year + month;
            Example tLoopPlanExample = new Example(TLoopPlan.class);
            tLoopPlanExample.createCriteria().andLessThanOrEqualTo("planStartDate", yearMonth).andGreaterThanOrEqualTo("planEndDate", yearMonth)
                    .andEqualTo("dptId", user.getDptId()).andEqualTo("isDel", false).andEqualTo("isUse", true);
            List<TLoopPlan> tLoopPlans = tLoopPlanMapper.selectByExample(tLoopPlanExample);
            if (tLoopPlans.size() > 1) {
                throw new ServiceException("不能有多个巡店计划！");
            } else if (tLoopPlans.size() == 0) {
                return null;
            }
            TLoopPlan tLoopPlan = tLoopPlans.get(0);

            Example tLoopSubPlanExample = new Example(TLoopSubPlan.class);
            tLoopSubPlanExample.createCriteria().andEqualTo("dptId", user.getDptId()).andLessThanOrEqualTo("cycleBegin", yearMonth)
                    .andGreaterThanOrEqualTo("cycleEnd", yearMonth).andEqualTo("isDel", false);
            List<TLoopSubPlan> tLoopSubPlans = tLoopSubPlanMapper.selectByExample(tLoopSubPlanExample);
            TLoopSubPlan tLoopSubPlan = tLoopSubPlans.get(0);

            //查询该计划需要打表总数量
            Long planId = tLoopPlans.get(0).getId();
            Example tPlanFormRelationExample = new Example(TPlanFormRelation.class);
            tPlanFormRelationExample.createCriteria().andEqualTo("planId", planId).andEqualTo("isDel", false);
            List<TPlanFormRelation> tPlanFormRelations = tPlanFormRelationMapper.selectByExample(tPlanFormRelationExample);
            int totalFormUseCount = 0;
            List<Long> formIds = new ArrayList<>();
            for(TPlanFormRelation tPlanFormRelation : tPlanFormRelations) {
                totalFormUseCount += tPlanFormRelation.getFormUseCount();
                formIds.add(tPlanFormRelation.getFormMainId());
            }
            List<TShopInfo> shopList = tShopInfoMapper.selectShopListByUserId(user.getId(), brandInfos);
            // 子计划内需要的总打表次数
            int totleCountInSubPlan = totalFormUseCount * shopList.size();
            List<HashMap<String, Long>> tFrontPlanSummary = tFrontPlanMapper.findFinishedArrangeListByForms(tLoopSubPlan.getId(), formIds);

            int actualFinishCount = 0;
            for(HashMap<String, Long> item : tFrontPlanSummary) {
                for (TPlanFormRelation tPlanFormRelation : tPlanFormRelations) {
                    if(tPlanFormRelation.getFormMainId().equals(item.get("formId"))) {
                        if(tPlanFormRelation.getFormUseCount() >= item.get("shopCount")) {
                            actualFinishCount += item.get("shopCount");
                        } else {
                            actualFinishCount += tPlanFormRelation.getFormUseCount();
                        }
                        break;
                    }
                }
            }
            float progress = 0f;
            if (totleCountInSubPlan == 0) {
                progress = 0;
            } else {
                progress = (float) actualFinishCount / totleCountInSubPlan;
            }

            planProgressOut.setProgress(progress);
            //统计进度是否正常
            float finishArrangeNumEveryMonth = (float)totleCountInSubPlan / tLoopPlan.getPlanCycle();
            int differ = calendar.get(Calendar.MONTH) + 1 - Integer.valueOf(tLoopSubPlan.getCycleBegin().substring(4, 6));
            float shouldFinishNum = finishArrangeNumEveryMonth * differ;
            if(actualFinishCount >= shouldFinishNum) {
                //返回正常
                planProgressOut.setProgressStatus(1);

            } else {
                // 异常
                planProgressOut.setProgressStatus(2);
            }
            return planProgressOut;
        } catch (Exception e){
            logger.error("用户:{}，查询巡店计划进度失败", user.getId(), e);
            throw new ApiException("查询巡店计划进度失败");
        }
    }
}
