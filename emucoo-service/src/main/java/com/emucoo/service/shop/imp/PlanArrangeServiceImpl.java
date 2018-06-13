package com.emucoo.service.shop.imp;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.dto.modules.shop.*;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.ReportFinishStatus;
import com.emucoo.enums.ShopArrangeStatus;
import com.emucoo.mapper.SysUserShopMapper;
import com.emucoo.mapper.TFormMainMapper;
import com.emucoo.mapper.TFrontPlanFormMapper;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.mapper.TLoopSubPlanMapper;
import com.emucoo.mapper.TRemindMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.SysUserShop;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TFrontPlanForm;
import com.emucoo.model.TLoopSubPlan;
import com.emucoo.model.TRemind;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.shop.PlanArrangeService;
import com.emucoo.utils.TimeTaskUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

@Service
public class PlanArrangeServiceImpl implements PlanArrangeService {

    private Logger logger = LoggerFactory.getLogger(PlanArrangeServiceImpl.class);

    @Autowired
    private TFrontPlanMapper tFrontPlanMapper;

    @Autowired
    private SysUserShopMapper sysUserShopMapper;

    @Autowired
    private TFrontPlanFormMapper tFrontPlanFormMapper;

    @Autowired
    private TShopInfoMapper tShopInfoMapper;

    @Autowired
    private TFormMainMapper tFormMainMapper;

    @Autowired
    private TRemindMapper tRemindMapper;

    @Autowired
    private TLoopSubPlanMapper tLoopSubPlanMapper;

    @Override
    public List<TRemind> listRemind() {
        List<TRemind> tReminds = tRemindMapper.selectAll();
        return tReminds;
    }

    @Override
    @Transactional
    public void save(PlanArrangeAddIn planArrangeAddIn, SysUser user) {
        try {
            List<Shop> shopList = planArrangeAddIn.getShopArr();
            Date now = new Date();
            for (Shop shop : shopList) {
                TFrontPlan tFrontPlan = new TFrontPlan();
                tFrontPlan.setId(shop.getSubID());
                if(shop.getSubID() == null) {
                    throw new BaseException("subID不能为空！");
                }
                tFrontPlan.setShopId(shop.getShopID());
                Date date = new Date(shop.getExPatrloShopArrangeTime());
                if(date.before(now)) {
                    throw new BaseException("安排日期不能小于当前！");
                }
                tFrontPlan.setPlanPreciseTime(date);
                tFrontPlan.setRemindType(shop.getRemindType().byteValue());
                Date remindDate = TimeTaskUtil.calActualRemindTime(date, shop.getRemindType());
                tFrontPlan.setActualRemindTime(remindDate);
                tFrontPlan.setRemark(planArrangeAddIn.getPostscript());
                tFrontPlan.setStatus(ShopArrangeStatus.NOT_CHECK.getCode().byteValue());
                List<CheckList> checkList = shop.getChecklistArr();
                List<TFrontPlanForm> frontPlanForms = new ArrayList<>();
                for (int i = 0; i < checkList.size(); i++) {
                    TFrontPlanForm frontPlanForm = new TFrontPlanForm();
                    frontPlanForm.setFormMainId(checkList.get(i).getChecklistID());
                    frontPlanForm.setFrontPlanId(shop.getSubID());
                    frontPlanForm.setIsDel(DeleteStatus.COMMON.getCode());
                    frontPlanForm.setReportStatus(ReportFinishStatus.NOT_FINISH.getCode().byteValue());
                    frontPlanForms.add(frontPlanForm);
                }
                String planDateStr = planArrangeAddIn.getExPatrloShopArrangeDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                tFrontPlan.setPlanDate(sdf.parse(planDateStr));
                tFrontPlan.setModifyTime(now);
                tFrontPlan.setModifyUserId(user.getId());
                tFrontPlan.setArrangerId(user.getId());
                // 根据店铺id查询负责人
                TLoopSubPlan tLoopSubPlan = tLoopSubPlanMapper.selectByPrimaryKey(planArrangeAddIn.getPlanID());
                List<SysUserShop> sysUserShops = sysUserShopMapper.findResponsiblePersonOfShop(shop.getShopID(), tLoopSubPlan.getDptId());
                if(CollectionUtils.isEmpty(sysUserShops)) {
                    throw new BaseException("无人负责该店铺！");
                }
                if(sysUserShops != null && sysUserShops.size() > 0) {
                    tFrontPlan.setArrangeeId(sysUserShops.get(0).getUserId());
                }
                /*Example example = new Example(SysUserShop.class);
                example.createCriteria().andEqualTo("shopId", shop.getShopID()).andEqualTo("isDel", false);
                List<SysUserShop> userShops = sysUserShopMapper.selectByExample(example);*/
                String userIds = "";
                for (SysUserShop userShop : sysUserShops) {
                    userIds += userShop.getUserId() + ",";
                }
                if(StringUtils.isNotBlank(userIds)) {
                    userIds = userIds.substring(0, userIds.length() - 1);
                }
                tFrontPlan.setNoticeUserId(userIds);

                // 更新巡店安排
                tFrontPlanMapper.updateFrontPlan(tFrontPlan);
                // 更新巡店安排和表单对应关系表
                tFrontPlanFormMapper.addRelationToArrangeAndForm(frontPlanForms);
            }

        } catch (Exception e) {
            logger.error("创建巡店安排失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("创建巡店安排失败");
        }

    }

    @Override
    @Transactional
    public void edit(PlanArrangeEditIn planArrangeEditIn, SysUser user) {
        try {
            List<Shop> shopList = planArrangeEditIn.getShopArr();
            for (Shop shop : shopList) {
                TFrontPlan plan = tFrontPlanMapper.selectByPrimaryKey(planArrangeEditIn.getPatrolShopArrangeID());
                if(plan != null && plan.getIsDel().equals(false) && plan.getStatus() >= ShopArrangeStatus.CHECKING.getCode()) {
                    throw new BaseException("巡店不可修改！");
                }
                TFrontPlan tFrontPlan = new TFrontPlan();
                tFrontPlan.setId(planArrangeEditIn.getPatrolShopArrangeID());
                tFrontPlan.setShopId(shop.getShopID());
                Date date = new Date(shop.getExPatrloShopArrangeTime());
                tFrontPlan.setPlanPreciseTime(date);
                tFrontPlan.setRemindType(shop.getRemindType().byteValue());
                Date remindDate = TimeTaskUtil.calActualRemindTime(date, shop.getRemindType());
                tFrontPlan.setActualRemindTime(remindDate);
                tFrontPlan.setRemark(planArrangeEditIn.getPostscript());
                tFrontPlan.setStatus(ShopArrangeStatus.NOT_CHECK.getCode().byteValue());
                List<CheckList> checkList = shop.getChecklistArr();
                List<TFrontPlanForm> frontPlanForms = new ArrayList<>();
                for (int i = 0; i < checkList.size(); i++) {
                    TFrontPlanForm frontPlanForm = new TFrontPlanForm();
                    frontPlanForm.setFormMainId(checkList.get(i).getChecklistID());
                    frontPlanForm.setFrontPlanId(planArrangeEditIn.getPatrolShopArrangeID());
                    frontPlanForm.setIsDel(DeleteStatus.COMMON.getCode());
                    frontPlanForm.setReportStatus(ReportFinishStatus.NOT_FINISH.getCode().byteValue());
                    frontPlanForms.add(frontPlanForm);
                }
                String planDateStr = planArrangeEditIn.getExPatrloShopArrangeDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                tFrontPlan.setPlanDate(sdf.parse(planDateStr));
                Date now = new Date();
                tFrontPlan.setModifyTime(now);
                tFrontPlan.setModifyUserId(user.getId());
                tFrontPlan.setArrangerId(user.getId());
                // 根据店铺id查询负责人
                TLoopSubPlan tLoopSubPlan = tLoopSubPlanMapper.selectByPrimaryKey(plan.getSubPlanId());
                List<SysUserShop> sysUserShops = sysUserShopMapper.findResponsiblePersonOfShop(shop.getShopID(), tLoopSubPlan.getDptId());
                if (sysUserShops != null && sysUserShops.size() > 1) {
                    throw new BaseException("一个店铺不能被同一个部门内的多个用户管理！");
                } else if (sysUserShops != null && sysUserShops.size() > 0) {
                    tFrontPlan.setArrangeeId(sysUserShops.get(0).getUserId());
                }

                Example example = new Example(SysUserShop.class);
                example.createCriteria().andEqualTo("shopId", shop.getShopID()).andEqualTo("isDel", false);
                List<SysUserShop> userShops = sysUserShopMapper.selectByExample(example);
                String userIds = "";
                for (SysUserShop userShop : userShops) {
                    userIds += userShop.getUserId() + ",";
                }
                if (StringUtils.isNotBlank(userIds)) {
                    userIds = userIds.substring(0, userIds.length() - 1);
                }
                tFrontPlan.setNoticeUserId(userIds);

                // 更新巡店安排
                tFrontPlanMapper.updateFrontPlan(tFrontPlan);
                // 删除旧的关系
                TFrontPlanForm tFrontPlanForm = new TFrontPlanForm();
                tFrontPlanForm.setIsDel(DeleteStatus.DELETED.getCode());
                tFrontPlanForm.setFrontPlanId(planArrangeEditIn.getPatrolShopArrangeID());
                tFrontPlanFormMapper.updateById(tFrontPlanForm);
                // 更新巡店安排和表单对应关系表
                tFrontPlanFormMapper.addRelationToArrangeAndForm(frontPlanForms);
            }
        } catch (Exception e) {
            logger.error("編輯巡店计划失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("编辑巡店计划失败");
        }
    }

    @Override
    @Transactional
    public void delete(PlanArrangeDeleteIn planArrangeDeleteIn, SysUser user) {
        try {
            TFrontPlan plan = tFrontPlanMapper.selectByPrimaryKey(planArrangeDeleteIn.getPatrolShopArrangeID());
            if (plan != null && plan.getIsDel().equals(false) && plan.getStatus() >= ShopArrangeStatus.CHECKING.getCode()) {
                throw new BaseException("巡店不可删除！");
            }
            List<Long> ids = new ArrayList<>();
            ids.add(planArrangeDeleteIn.getPatrolShopArrangeID());
            // 删除计划
            tFrontPlanMapper.deleteFrontPlanByIds(ids);

            TFrontPlanForm tFrontPlanForm = new TFrontPlanForm();
            tFrontPlanForm.setIsDel(DeleteStatus.DELETED.getCode());
            tFrontPlanForm.setFrontPlanId(planArrangeDeleteIn.getPatrolShopArrangeID());
            tFrontPlanFormMapper.updateById(tFrontPlanForm);
        } catch (Exception e) {
            logger.error("删除巡店安排失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("删除巡店安排失败");
        }
    }

    public PatrolShopArrangeDetailVO detail(PatrolShopArrangeDetailIn vo) {
        try {
            PatrolShopArrangeDetailVO patrolShopArrangeDetailVO = new PatrolShopArrangeDetailVO();
            TFrontPlan frontPlan = tFrontPlanMapper.selectByPrimaryKey(vo.getPatrolShopArrangeID());
            List<ShopArrVO> shopArrList = new ArrayList<ShopArrVO>();
            // 如果在巡店安排表里没找到，返回空的VO
            if (frontPlan == null) {
                patrolShopArrangeDetailVO.setShopArr(shopArrList);
                throw new BaseException("不存在安排");
                //return patrolShopArrangeDetailVO;
            }
            // 计划到店时间
            if (frontPlan.getPlanDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                patrolShopArrangeDetailVO.setExPatrloShopArrangeDate(sdf.format(frontPlan.getPlanDate()));
            }
            TShopInfo shop = tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
            // 如果没找到相应店，也返回空的VO
            if (shop == null) {
                patrolShopArrangeDetailVO.setShopArr(shopArrList);
                throw new BaseException("不存在对应店铺");
                //return patrolShopArrangeDetailVO;
            }
            ShopArrVO shopArr = new ShopArrVO();
            shopArr.setShopID(shop.getId());
            shopArr.setShopName(shop.getShopName());
            shopArr.setPostscript(frontPlan.getRemark());
            if (frontPlan.getPlanPreciseTime() != null) {
                shopArr.setExPatrloShopArrangeTime(frontPlan.getPlanPreciseTime().getTime());
            }

            shopArr.setShopStatus(frontPlan.getStatus().intValue());
            // 查询巡店安排关联的检查表
            Example frontPlanFormExample = new Example(TFrontPlanForm.class);
            frontPlanFormExample.createCriteria().andEqualTo("frontPlanId", vo.getPatrolShopArrangeID())
                    .andEqualTo("isDel", false);
            List<TFrontPlanForm> tFrontPlanForms = tFrontPlanFormMapper.selectByExample(frontPlanFormExample);
            // 如果检查表不为空
            if (tFrontPlanForms != null) {
                List<ChecklistArrVO> checklistArr = new ArrayList<ChecklistArrVO>();
                for (TFrontPlanForm tFrontPlanForm : tFrontPlanForms) {
                    TFormMain form = tFormMainMapper.selectByPrimaryKey(tFrontPlanForm.getFormMainId());
                    // 如果检查点合法，能查到
                    if (form != null) {
                        ChecklistArrVO checklistArrVO = new ChecklistArrVO();
                        checklistArrVO.setChecklistID(form.getId());
                        checklistArrVO.setChecklistType(form.getFormType());
                        checklistArrVO.setChecklistName(form.getName());
                        checklistArrVO.setChecklistStatus(tFrontPlanForm.getReportStatus().longValue());
                        checklistArrVO.setReportID(tFrontPlanForm.getReportId());
                        checklistArr.add(checklistArrVO);
                    }
                }
                shopArr.setChecklistArr(checklistArr);
            }

            if (frontPlan.getActualExecuteTime() != null) {
                shopArr.setPatrolShopStartTime(frontPlan.getActualExecuteTime().getTime());
            }
            shopArr.setPatrolShopLocation(frontPlan.getActualExecuteAddress());
            shopArr.setLatitude(frontPlan.getLatitude());
            shopArr.setLongitude(frontPlan.getLongitude());

            // 提醒 Remind_type and Remind_name
            TRemind remind = new TRemind();
            remind.setRemindType(frontPlan.getRemindType().intValue());
            TRemind tRemind = tRemindMapper.selectOne(remind);
            if (tRemind != null) {
                shopArr.setRemindType(tRemind.getRemindType().longValue());
                shopArr.setRemindName(tRemind.getRemindName());
            }

            shopArrList.add(shopArr);
            patrolShopArrangeDetailVO.setShopArr(shopArrList);

            return patrolShopArrangeDetailVO;
        } catch (Exception e) {
            logger.error("查询巡店详情失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("查询巡店详情失败！");
        }
    }
    @Override
    public void patrolShop(PatrolShopInfoIn patrolShopInfoIn, SysUser user) {
        try {
            TFrontPlan tFrontPlan = new TFrontPlan();
            tFrontPlan.setId(patrolShopInfoIn.getPatrolShopArrangeID());
            tFrontPlan.setActualExecuteAddress(patrolShopInfoIn.getPatrolShopLocation());
            tFrontPlan.setLongitude(patrolShopInfoIn.getLongitude());
            tFrontPlan.setLatitude(patrolShopInfoIn.getLatitude());
            tFrontPlan.setActualExecuteTime(new Date(patrolShopInfoIn.getPatrolShopStartTime()));
            tFrontPlan.setStatus(ShopArrangeStatus.CHECKING.getCode().byteValue());
            Date now = new Date();
            tFrontPlan.setModifyUserId(user.getId());
            tFrontPlan.setModifyTime(now);
            tFrontPlanMapper.uploadArrangeProcess(tFrontPlan);
        } catch (Exception e) {
            logger.error("上传巡店位置失败！", e);
            throw new ApiException("上传巡店位置失败");
        }
    }
}
