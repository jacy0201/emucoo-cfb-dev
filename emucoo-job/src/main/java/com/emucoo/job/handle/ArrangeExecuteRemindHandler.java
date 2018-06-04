package com.emucoo.job.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emucoo.common.util.MsgPushing;
import com.emucoo.enums.Constant;
import com.emucoo.enums.FunctionType;
import com.emucoo.enums.ModuleType;
import com.emucoo.mapper.TBusinessMsgMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBusinessMsg;
import com.emucoo.model.TFrontPlan;
import com.emucoo.service.shop.TFrontPlanService;
import com.emucoo.service.sys.UserService;
import com.emucoo.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sj on 2018/5/28.
 */
@JobHandler(value = "arrangeExecuteRemindHandler")
@Component
public class ArrangeExecuteRemindHandler extends IJobHandler {

    @Autowired
    private TFrontPlanService frontPlanService;

    @Autowired
    private MsgPushing msgPushing;

    @Autowired
    private UserService userService;

    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Override
    public ReturnT<String> execute(String json) throws Exception {
        JSONObject jsonArgs = JSON.parseObject(json);
        int cycleMinutes = jsonArgs == null ? 10 : jsonArgs.getIntValue("cycleMinutes");
        cycleMinutes = cycleMinutes == 0 ? 10 : cycleMinutes;
        Date currentDate = DateUtil.currentDate();
        List<TFrontPlan> frontPlanList = frontPlanService.filterExecuteRemindArrange(currentDate, cycleMinutes);

        String sendContent = "您有待执行的巡店马上到时间了！请注意查看，如果已经执行，请忽略该提醒。";
        if(CollectionUtils.isNotEmpty(frontPlanList)) {
            List<String> pushTokens = new ArrayList<>();
            List<TBusinessMsg> businessMsgs = new ArrayList<>();
            for(TFrontPlan frontPlan : frontPlanList) {
                SysUser user = userService.findById(frontPlan.getArrangeeId());
                if(user != null) {
                    if(StringUtils.isNotBlank(user.getPushToken())) {
                        pushTokens.add(user.getPushToken());
                    }
                    TBusinessMsg businessMsg = new TBusinessMsg();
                    businessMsg.setCreateTime(currentDate);
                    businessMsg.setMsg(sendContent);
                    businessMsg.setContent(frontPlan.getShop().getShopName() + "巡店安排");
                    businessMsg.setFunctionType(FunctionType.EXECUTE_REMIND.getCode().byteValue());
                    businessMsg.setUnionType(ModuleType.SHOP_PLAN.getCode().byteValue());
                    businessMsg.setUnionId(frontPlan.getId());
                    businessMsg.setIsSend(true);
                    businessMsg.setIsRead(false);
                    businessMsg.setOrgId(Constant.orgId);
                    businessMsg.setReceiverId(frontPlan.getArrangeeId());
                    businessMsg.setSendTime(currentDate);
                    businessMsgs.add(businessMsg);
                }
            }
            msgPushing.pushMessage("待执行任务提醒", sendContent, null, pushTokens);
            businessMsgMapper.insertList(businessMsgs);
        }
        return ReturnT.SUCCESS;
    }
}
