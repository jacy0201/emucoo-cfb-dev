package com.emucoo.service.msg.impl;

import com.emucoo.dto.modules.msg.MsgSummaryModuleVo;
import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.mapper.TBusinessMsgMapper;
import com.emucoo.model.MsgReceiveSummary;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBusinessMsg;
import com.emucoo.service.msg.MsgService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 2018/5/30.
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Override
    public MsgSummaryOut msgSummary(SysUser user) {
        MsgSummaryOut msgSummaryOut = new MsgSummaryOut();
        List<MsgReceiveSummary> msgReceiveSummaryList = businessMsgMapper.findMsgSummaryByUserId(user.getId());
        if(CollectionUtils.isNotEmpty(msgReceiveSummaryList)) {
            List<MsgSummaryModuleVo> msgSummaryModuleVos = new ArrayList<>();
            for(MsgReceiveSummary msgReceiveSummary : msgReceiveSummaryList) {
                MsgSummaryModuleVo msgSummaryModuleVo = new MsgSummaryModuleVo();
                msgSummaryModuleVo.setFunctionType(msgReceiveSummary.getFunctionType());
                msgSummaryModuleVo.setMsgContent(msgReceiveSummary.getMsgContent());
                msgSummaryModuleVo.setMsgCount(msgReceiveSummary.getMsgCount());
                msgSummaryModuleVo.setMsgSendTimeStamp(msgReceiveSummary.getMsgSendTime().getTime());
                msgSummaryModuleVos.add(msgSummaryModuleVo);
            }
            msgSummaryOut.setMsgModuleList(msgSummaryModuleVos);
        }

        return msgSummaryOut;
    }

}
