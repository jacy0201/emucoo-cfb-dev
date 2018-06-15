package com.emucoo.service.task;

import com.emucoo.common.msg.JiGuangProxy;
import com.emucoo.model.*;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shayne.Wang
 * @date 2018-06-12
 */

@Component
public class MessageBuilder {
    @Autowired
    private JiGuangProxy jiGuangProxy;

    private String buildPushTitleByMessageType(int msgType) {
        switch (msgType) {
            case 1:
                return "收到消息";
            case 2:
                return "执行提醒";
            case 3:
                return "待审任务";
            default:
                return "消息";
        }
    }

    public TBusinessMsg pushMessage(TBusinessMsg msg, SysUser user, int msgType) {
        if (user == null || user.getPushToken() == null) {
            msg.setSendFailReason("消息收件人不能为空");
            msg.setSendStatus(Integer.valueOf(2).byteValue());
            return msg;
        }
        int pushCode = jiGuangProxy.sendNotification(buildPushTitleByMessageType(msgType), msg.getTitle(), user.getPushToken(), null, JiGuangProxy.DeviceOS.OS_ALL);
        msg.setSendFailReason(jiGuangProxy.judgeFailReason(pushCode));
        msg.setSendStatus(Integer.valueOf(pushCode == 0 ? 1 : 2).byteValue());
        return msg;
    }

    public TBusinessMsg buildLoopWorkCreationBusinessMessage(TTask task, TLoopWork loopWork, SysUser user, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(loopWork.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(loopWork.getExcuteUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(loopWork.getId());
        msg.setUnionType(task.getType().byteValue());
        msg.setTitle(String.format("%s 创建了 %s", loopWork.getExcuteUserName(), task.getName()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildLoopWorkExeRemindBusinessMsg(TTask task, TLoopWork loopWork, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(loopWork.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(true);
        msg.setReceiverId(loopWork.getExcuteUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(loopWork.getId());
        msg.setUnionType(task.getType().byteValue());
        msg.setTitle(String.format("%s 任务需要执行", task.getName()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildLoopWorkAuditRemindBusinessMsg(TTask task, TLoopWork loopWork, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(loopWork.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(true);
        msg.setReceiverId(loopWork.getAuditUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(loopWork.getId());
        msg.setUnionType(task.getType().byteValue());
        msg.setTitle(String.format("%s 任务需要您审核", task.getName()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildLoopWorkExpiredBusinessMsg(TTask task, TLoopWork work, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(work.getExcuteUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(task.getType()).byteValue());
        msg.setTitle(String.format("%s 任务已经超过截止时间", task.getName()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildLoopWorkAuditBusinessMsg(TTask task, TLoopWork loopWork, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(loopWork.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(loopWork.getExcuteUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(loopWork.getId());
        msg.setUnionType(Integer.valueOf(task.getType()).byteValue());
        msg.setTitle(String.format("%s 任务审核结果为 %s", task.getName(), loopWork.getWorkResult() == 1 ? "合格" : "不合格"));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }


    public List<TBusinessMsg> buildLoopWorkCCBusinessMsg(TTask task, TLoopWork loopWork, int msgType) {
        List<TBusinessMsg> msgs = new ArrayList<>();
        if (StringUtils.isNotBlank(loopWork.getSendUserIds())) {
            Arrays.asList(loopWork.getSendUserIds().split(",")).forEach(id -> {
                TBusinessMsg msg = new TBusinessMsg();
                msg.setCreateTime(loopWork.getCreateTime());
                msg.setIsRead(false);
                msg.setIsSend(false);
                msg.setReceiverId(Long.parseLong(id));
                msg.setSendTime(DateUtil.currentDate());
                msg.setUnionId(loopWork.getId());
                msg.setUnionType(Integer.valueOf(task.getType()).byteValue());
                msg.setTitle(String.format("%s 任务已完成，执行人为 %s 审核结果为 %s", task.getName(), loopWork.getExcuteUserName(), loopWork.getWorkResult() == 1 ? "合格" : "不合格"));
                msg.setFunctionType(Integer.valueOf(msgType).byteValue());
                msgs.add(msg);
            });
        }
        return msgs;
    }

    public TBusinessMsg buildRepairWorkCreationBusinessMessage(TRepairWork work, SysUser user, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getReportDate());
        msg.setIsRead(false);
        msg.setIsSend(true);
        msg.setReceiverId(work.getRepairManId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(7).byteValue());
        msg.setTitle(String.format("%s 发起了一个 %s 维修任务", work.getShopName(), work.getDeviceTitle()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildRepairWorkAuditRemindBusinessMsg(TRepairWork work, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(true);
        msg.setReceiverId(work.getReporterId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(7).byteValue());
        msg.setTitle(String.format("%s 任务需要您审核", work.getDeviceTitle()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildRepairWorkExpiredBusinessMsg(TRepairWork work, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(work.getRepairManId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(7).byteValue());
        msg.setTitle(String.format("%s 任务已经超过截止时间", work.getDeviceTitle()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }


    public TBusinessMsg buildRepairWorkAuditBusinessMsg(TRepairWork work, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(work.getRepairManId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(7).byteValue());
        msg.setTitle(String.format("%s 验收结果为 %s", work.getDeviceTitle(), work.getReviewResult() == 1 ? "合格" : "不合格" ));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

    public TBusinessMsg buildRepairWorkExpectDateBusinessMsg(TRepairWork work, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(work.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(false);
        msg.setReceiverId(work.getReporterId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(work.getId());
        msg.setUnionType(Integer.valueOf(7).byteValue());
        msg.setTitle(String.format("%s 预计维修日期为 %s", work.getDeviceTitle(), DateUtil.dateToString(work.getExpectDate())));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }
}
