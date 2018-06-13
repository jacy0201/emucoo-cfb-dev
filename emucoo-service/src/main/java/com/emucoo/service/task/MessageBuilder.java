package com.emucoo.service.task;

import com.emucoo.common.msg.JiGuangProxy;
import com.emucoo.model.*;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Shayne.Wang
 * @date 2018-06-12
 *
 */

@Component
public class MessageBuilder {
    @Autowired
    private JiGuangProxy jiGuangProxy;

    public TBusinessMsg buildTaskCreationBusinessMessage(TTask task, TLoopWork loopWork, SysUser user, int msgType) {
        TBusinessMsg msg = new TBusinessMsg();
        msg.setCreateTime(loopWork.getCreateTime());
        msg.setIsRead(false);
        msg.setIsSend(true);
        msg.setReceiverId(loopWork.getExcuteUserId());
        msg.setSendTime(DateUtil.currentDate());
        msg.setUnionId(loopWork.getId());
        msg.setUnionType(task.getType().byteValue());
        msg.setTitle(String.format("%s 创建了 %s", loopWork.getExcuteUserName(), task.getName()));
        msg.setFunctionType(Integer.valueOf(msgType).byteValue());
        return msg;
    }

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
        if(user == null || user.getPushToken() == null) {
            msg.setSendFailReason("消息收件人不能为空");
            msg.setSendStatus(Integer.valueOf(2).byteValue());
            return msg;
        }
        int pushCode = jiGuangProxy.sendNotification(buildPushTitleByMessageType(msgType), msg.getTitle(), user.getPushToken(), null, JiGuangProxy.DeviceOS.OS_ALL);
        msg.setSendFailReason(jiGuangProxy.judgeFailReason(pushCode));
        msg.setSendStatus(Integer.valueOf(pushCode == 0 ? 1 : 2).byteValue());
        return msg;
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
}
