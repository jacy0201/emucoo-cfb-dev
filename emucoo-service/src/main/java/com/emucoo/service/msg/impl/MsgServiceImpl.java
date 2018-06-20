package com.emucoo.service.msg.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.msg.MsgDetailIn;
import com.emucoo.dto.modules.msg.MsgListIn;
import com.emucoo.dto.modules.msg.MsgListOut;
import com.emucoo.dto.modules.msg.MsgListVo;
import com.emucoo.dto.modules.msg.MsgSummaryModuleVo;
import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.dto.modules.msg.UpdateMsgReadedIn;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailIn;
import com.emucoo.dto.modules.task.AssignTaskDetailVo_I;
import com.emucoo.dto.modules.task.IdVo;
import com.emucoo.dto.modules.task.MemoDetailVo_I;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskImproveDetailIn;
import com.emucoo.enums.FunctionType;
import com.emucoo.enums.ModuleType;
import com.emucoo.mapper.TBusinessMsgMapper;
import com.emucoo.mapper.TLoopWorkMapper;
import com.emucoo.model.MsgReceiveSummary;
import com.emucoo.model.SysUser;
import com.emucoo.model.TBusinessMsg;
import com.emucoo.model.TLoopWork;
import com.emucoo.service.msg.MsgService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sj on 2018/5/30.
 */
@Service
public class MsgServiceImpl implements MsgService {
    private Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Override
    public MsgSummaryOut msgSummary(SysUser user) {
        try{
            MsgSummaryOut msgSummaryOut = new MsgSummaryOut();
            List<MsgReceiveSummary> msgReceiveSummaryList = businessMsgMapper.findMsgSummaryByUserId(user.getId());
            if (CollectionUtils.isNotEmpty(msgReceiveSummaryList)) {
                List<MsgSummaryModuleVo> msgSummaryModuleVos = new ArrayList<>();
                for (FunctionType functionTypeEnum : FunctionType.values()) {
                    boolean hasMsg = false;
                    for (MsgReceiveSummary msgReceiveSummary : msgReceiveSummaryList) {
                        if (functionTypeEnum.getCode().equals(msgReceiveSummary.getFunctionType())) {
                            hasMsg = true;
                            MsgSummaryModuleVo msgSummaryModuleVo = new MsgSummaryModuleVo();
                            msgSummaryModuleVo.setFunctionType(msgReceiveSummary.getFunctionType());
                            msgSummaryModuleVo.setMsgTitle(msgReceiveSummary.getMsgTitle());
                            msgSummaryModuleVo.setMsgCount(msgReceiveSummary.getMsgCount());
                            msgSummaryModuleVo.setMsgSendTimeStamp(msgReceiveSummary.getMsgSendTime().getTime());
                            msgSummaryModuleVo.setMsgTitle(FunctionType.getName(msgReceiveSummary.getFunctionType()));
                            msgSummaryModuleVos.add(msgSummaryModuleVo);
                            break;
                        }
                    }
                    if(!hasMsg) {
                        MsgSummaryModuleVo msgSummaryModuleVo = new MsgSummaryModuleVo();
                        msgSummaryModuleVo.setFunctionType(functionTypeEnum.getCode());
                        msgSummaryModuleVo.setFunctionTitle(FunctionType.getName(functionTypeEnum.getCode()));
                        MsgReceiveSummary msgReceiveSummary = businessMsgMapper.findNewestReadedMsgByFunctionType(functionTypeEnum.getCode());
                        if(msgReceiveSummary == null) {
                            msgSummaryModuleVo.setMsgCount(0);
                            msgSummaryModuleVo.setMsgTitle("");
                        } else {
                            msgSummaryModuleVo.setMsgCount(0);
                            msgSummaryModuleVo.setMsgTitle(msgReceiveSummary.getMsgTitle());
                            msgSummaryModuleVo.setMsgSendTimeStamp(msgReceiveSummary.getMsgSendTime().getTime());
                        }

                        msgSummaryModuleVos.add(msgSummaryModuleVo);
                    }
                    msgSummaryOut.setMsgModuleList(msgSummaryModuleVos);


                }

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
                msg.setWorkType(businessMsg.getUnionType().intValue());
                msg.setSendTime(businessMsg.getSendTime().getTime());
                msg.setIsRead(businessMsg.getIsRead());
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

    @Override
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

    @Override
    public Map<String, Object> msgDetail(MsgDetailIn msgDetailIn) {
        try {
            Map<String, Object> forwardParam = new HashMap<>();
            // 如果是评论消息
            if(msgDetailIn.getFunctionType().equals(FunctionType.COMMENT_REMIND.getCode())) {
                ParamVo<CommentSelectIn> paramVo = new ParamVo<CommentSelectIn>();
                CommentSelectIn detailParam = new CommentSelectIn();
                if(!msgDetailIn.getWorkType().equals(ModuleType.COMMENT.getCode())) {
                    if (msgDetailIn.getWorkType().equals(ModuleType.COMMON_TASK.getCode()) || msgDetailIn.getWorkType().equals(ModuleType.ARRANGE_TASK.getCode())
                            || msgDetailIn.getWorkType().equals(ModuleType.IMPROVE_TASK.getCode()) || msgDetailIn.getWorkType().equals(ModuleType.WORK_MEMORANDUM.getCode())) {//任务和工作备忘
                        TLoopWork loopWork = loopWorkMapper.selectByPrimaryKey(msgDetailIn.getUnionID());
                        detailParam.setSubID(loopWork.getSubWorkId());
                        detailParam.setWorkID(loopWork.getWorkId());
                        detailParam.setWorkType(loopWork.getType());
                        paramVo.setData(detailParam);
                    } else if (msgDetailIn.getWorkType().equals(ModuleType.SHOP_PLAN.getCode())) {//巡店安排
                        detailParam.setReportID(msgDetailIn.getUnionID());
                        detailParam.setWorkType(ModuleType.SHOP_PLAN.getCode());
                    } else if (msgDetailIn.getWorkType().equals(ModuleType.REPAIR_TASK.getCode())) {//维修任务
                        detailParam.setRepairID(msgDetailIn.getUnionID());
                        detailParam.setWorkType(ModuleType.REPAIR_TASK.getCode());
                    } else {
                        throw new BaseException("消息内容不存在！");
                    }
                    paramVo.setData(detailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/assign/getCommentList");
                } else {
                    detailParam.setCommentID(msgDetailIn.getUnionID());
                    detailParam.setWorkType(ModuleType.COMMENT.getCode());
                    paramVo.setData(detailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/assign/getReplyList");
                }

            } else {// 如果是回复消息
                if (msgDetailIn.getWorkType().equals(ModuleType.COMMON_TASK.getCode())) {//常规任务
                    ParamVo<TaskCommonDetailIn> paramVo = new ParamVo<TaskCommonDetailIn>();
                    TaskCommonDetailIn taskDetailParam = new TaskCommonDetailIn();
                    TLoopWork loopWork = loopWorkMapper.selectByPrimaryKey(msgDetailIn.getUnionID());
                    taskDetailParam.setSubID(loopWork.getSubWorkId());
                    taskDetailParam.setWorkID(loopWork.getWorkId());
                    taskDetailParam.setWorkType(loopWork.getType());
                    paramVo.setData(taskDetailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/common/taskDetail");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.ARRANGE_TASK.getCode())) {//指派任务
                    ParamVo<AssignTaskDetailVo_I> paramVo = new ParamVo<AssignTaskDetailVo_I>();
                    AssignTaskDetailVo_I taskDetailParam = new AssignTaskDetailVo_I();
                    TLoopWork loopWork = loopWorkMapper.selectByPrimaryKey(msgDetailIn.getUnionID());
                    taskDetailParam.setSubID(loopWork.getSubWorkId());
                    taskDetailParam.setWorkID(loopWork.getWorkId());
                    taskDetailParam.setWorkType(loopWork.getType());
                    paramVo.setData(taskDetailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/assign/taskDetail");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.IMPROVE_TASK.getCode())) {//改善任务
                    ParamVo<TaskImproveDetailIn> paramVo = new ParamVo<TaskImproveDetailIn>();
                    TaskImproveDetailIn taskDetailParam = new TaskImproveDetailIn();
                    TLoopWork loopWork = loopWorkMapper.selectByPrimaryKey(msgDetailIn.getUnionID());
                    taskDetailParam.setSubID(loopWork.getSubWorkId());
                    taskDetailParam.setWorkID(loopWork.getWorkId());
                    taskDetailParam.setWorkType(loopWork.getType());
                    paramVo.setData(taskDetailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/improve/detail");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.SHOP_PLAN.getCode())) {//巡店安排
                    ParamVo<PatrolShopArrangeDetailIn> paramVo = new ParamVo<PatrolShopArrangeDetailIn>();
                    PatrolShopArrangeDetailIn detailParam = new PatrolShopArrangeDetailIn();
                    detailParam.setPatrolShopArrangeID(msgDetailIn.getUnionID());
                    paramVo.setData(detailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/shop/arrange/detail");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.WORK_MEMORANDUM.getCode())) {//工作备忘
                    ParamVo<MemoDetailVo_I> paramVo = new ParamVo<MemoDetailVo_I>();
                    MemoDetailVo_I detailParam = new MemoDetailVo_I();
                    TLoopWork loopWork = loopWorkMapper.selectByPrimaryKey(msgDetailIn.getUnionID());
                    detailParam.setSubID(loopWork.getSubWorkId());
                    detailParam.setWorkID(loopWork.getWorkId());
                    detailParam.setWorkType(loopWork.getType());
                    paramVo.setData(detailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/memo/memoDetail");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.COMMENT.getCode())) {//评论
                    throw new BaseException("消息内容不存在！");
                } else if (msgDetailIn.getWorkType().equals(ModuleType.REPAIR_TASK.getCode())) {//维修任务
                    ParamVo<IdVo> paramVo = new ParamVo<IdVo>();
                    IdVo detailParam = new IdVo();
                    detailParam.setId(msgDetailIn.getUnionID());
                    paramVo.setData(detailParam);
                    forwardParam.put("param", paramVo);
                    forwardParam.put("url", "/api/task/repair/detail");
                } else {
                    throw new BaseException("消息内容不存在！");
                }
            }

            return forwardParam;
        } catch (Exception e) {
            logger.error("组装消息传递信息错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("组装消息传递信息错误！");
        }
    }

}
