package com.emucoo.service.msg.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.dto.modules.msg.MsgListIn;
import com.emucoo.dto.modules.msg.MsgListOut;
import com.emucoo.dto.modules.msg.MsgListVo;
import com.emucoo.dto.modules.msg.MsgSummaryModuleVo;
import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.dto.modules.msg.UpdateMsgReadedIn;
import com.emucoo.mapper.TBusinessMsgMapper;
import com.emucoo.model.MsgReceiveSummary;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBusinessMsg;
import com.emucoo.service.msg.MsgService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 2018/5/30.
 */
@Service
public class MsgServiceImpl implements MsgService {
    private Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Override
    public MsgSummaryOut msgSummary(SysUser user) {
        try{
            MsgSummaryOut msgSummaryOut = new MsgSummaryOut();
            List<MsgReceiveSummary> msgReceiveSummaryList = businessMsgMapper.findMsgSummaryByUserId(user.getId());
            if (CollectionUtils.isNotEmpty(msgReceiveSummaryList)) {
                List<MsgSummaryModuleVo> msgSummaryModuleVos = new ArrayList<>();
                for (MsgReceiveSummary msgReceiveSummary : msgReceiveSummaryList) {
                    MsgSummaryModuleVo msgSummaryModuleVo = new MsgSummaryModuleVo();
                    msgSummaryModuleVo.setFunctionType(msgReceiveSummary.getFunctionType());
                    msgSummaryModuleVo.setMsgTitle(msgReceiveSummary.getMsgTitle());
                    msgSummaryModuleVo.setMsgCount(msgReceiveSummary.getMsgCount());
                    msgSummaryModuleVo.setMsgSendTimeStamp(msgReceiveSummary.getMsgSendTime().getTime());
                    msgSummaryModuleVos.add(msgSummaryModuleVo);
                }
                msgSummaryOut.setMsgModuleList(msgSummaryModuleVos);
            }

            return msgSummaryOut;
        } catch (Exception e){
            logger.error("查询消息汇总错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("查询消息汇总错误！");
        }

    }
    @Override
    public MsgListOut getMsgList(SysUser user, MsgListIn msgListIn) {
        try {
            MsgListOut msgListOut = new MsgListOut();
            Example msgExp = new Example(TBusinessMsg.class);
            msgExp.createCriteria().andEqualTo("functionType", msgListIn.getFunctionType()).andEqualTo("receiverId", user.getId());
            List<TBusinessMsg> businessMsgList = businessMsgMapper.selectByExample(msgExp);
            List<MsgListVo> msgArray = new ArrayList<>();
            for(TBusinessMsg businessMsg : businessMsgList) {
                MsgListVo msg = new MsgListVo();
                msg.setMsgID(businessMsg.getId());
                msg.setMsgTitle(businessMsg.getTitle());
                msg.setUnionID(businessMsg.getUnionId());
                msg.setUnionType(businessMsg.getUnionType().intValue());
                msg.setSendTime(DateUtil.dateToString(businessMsg.getSendTime()));
                msgArray.add(msg);
            }
            msgListOut.setMsgArray(msgArray);
            return msgListOut;
        } catch (Exception e) {
            logger.error("查询消息列表错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("查询消息列表错误！");
        }
    }

    public void updateMsgListReaded(UpdateMsgReadedIn updateMsgIn) {
        try{
            List<Long> ids = updateMsgIn.getMsgIDs();
            businessMsgMapper.updateReadStatusByIds(ids);
        } catch (Exception e) {
            logger.error("更新消息错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("更新消息错误！");
        }
    }

}
