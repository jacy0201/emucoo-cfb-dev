package com.emucoo.model;

import java.util.Date;

/**
 * Created by sj on 2018/5/30.
 */
public class MsgReceiveSummary {
    private String msgTitle;
    private Integer msgCount;
    private Date msgSendTime;
    private Integer functionType;

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public Date getMsgSendTime() {
        return msgSendTime;
    }

    public void setMsgSendTime(Date msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }
}
