package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.task.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.DateUtil;
import com.emucoo.utils.TaskUniqueIdUtils;
import com.emucoo.utils.WaterMarkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author river
 * @date 2018/3/14 14:19
 */
@Service
public class LoopWorkServiceImpl extends BaseServiceImpl<TLoopWork> implements LoopWorkService {

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Autowired
    private TTaskCommentMapper commentMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Autowired
    private TOperateOptionMapper operateOptionMapper;

    @Autowired
    private TOperateDataForWorkMapper operateDataForWorkMapper;

    /**
     *
     * @param submitUserId
     * @param date
     * @return
     */
    @Override
    public List<TLoopWork> listPendingExecute(Long submitUserId, Date date){
        return loopWorkMapper.listPendingExecute(submitUserId, date);
    }

    @Override
    public List<TLoopWork> listPendingReview(Long auditUserId){
        Date ldt = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        Date rdt = DateUtil.dateAddDay(ldt, 1);
        return loopWorkMapper.listPendingReview(auditUserId, ldt, rdt);
    }

    public int fetchPendingExecuteWorkNum(Long submitUserId, Date today) {
        return loopWorkMapper.countPendingExecuteWorkNum(submitUserId, today);
    }

    @Override
    public int fetchPendingReviewWorkNum(Long submitUserId) {
        return loopWorkMapper.countPendingReviewWorkNum(submitUserId);
    }

	@Override
	public void add(TLoopWork loopWork) {
		loopWorkMapper.insert(loopWork);
	}

	@Override
	public void modify(TLoopWork loopWork) {
		loopWorkMapper.updateByPrimaryKey(loopWork);
	}
	
    @Override
    public TLoopWork fetchByWorkId(String workId, String subWorkId, int workType) {
        return loopWorkMapper.fetchByWorkIdAndType(workId, subWorkId, workType);
    }

    @Override
    @Transactional
    public void submitAssignTask(AssignTaskSubmitVo_I voi) {
        TLoopWork lw = loopWorkMapper.fetchOneTaskByWorkIds(voi.getWorkID(), voi.getSubID());
        lw.setType(voi.getWorkType());
        lw.setWorkId(voi.getWorkID());
        lw.setSubWorkId(voi.getSubID());
        lw.setWorkStatus(2);
        lw.setAuditDeadline(DateUtil.addDateHours(new Date(), 12));
        lw.setModifyTime(DateUtil.currentDate());
        loopWorkMapper.updateWorkStatus(lw);

        List<String> imgids = new ArrayList<>();
        voi.getExecuteImgArr().forEach(assignTaskSubmitImgVo -> {
            com.emucoo.model.TFile timg = new com.emucoo.model.TFile();
            Date dt = new Date(assignTaskSubmitImgVo.getDate());
            timg.setCreateUserId(lw.getExcuteUserId());
            timg.setModifyUserId(lw.getExcuteUserId());
            timg.setCreateTime(DateUtil.currentDate());
            timg.setModifyTime(DateUtil.currentDate());
            timg.setImgUrl(WaterMarkUtils.genPicUrlWithWaterMark(assignTaskSubmitImgVo.getImgUrl(), assignTaskSubmitImgVo.getLocation(), DateUtil.dateToString(dt)));
            fileMapper.insert(timg);
            imgids.add(Long.toString(timg.getId()));
        });

        TOperateDataForWork toof = new TOperateDataForWork();
        toof.setImgIds(StringUtils.join(imgids, ","));
        toof.setCreateTime(DateUtil.currentDate());
        toof.setModifyTime(DateUtil.currentDate());
        toof.setLoopWorkId(lw.getId());
        toof.setWorkTxt(voi.getWorkText());
        TOperateOption too = operateOptionMapper.fetchOneByTaskId(lw.getTaskId());
        toof.setNumOptionName(too.getFeedbackNumName());
        toof.setNumOptionType(too.getFeedbackNumType());
        toof.setNumOptionValue(Double.toString(voi.getDigitalItemValue()));
        operateDataForWorkMapper.insert(toof);
    }

    @Override
    public void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai) {
        TLoopWork loopWork = loopWorkMapper.fetchOneTaskByWorkIds(atai.getWorkID(), atai.getSubID());
        loopWork.setType(atai.getWorkType());
        loopWork.setWorkResult(atai.getReviewResult());
        loopWork.setAuditUserId(user.getId());
        loopWork.setAuditUserName(user.getUsername());

        loopWorkMapper.auditLoopWork(loopWork);

        List<String> aimgs = new ArrayList<>();
        atai.getReviewImgArr().forEach(imageUrlVo -> {
            com.emucoo.model.TFile aimg = new com.emucoo.model.TFile();
            aimg.setImgUrl(imageUrlVo.getImgUrl());
            aimg.setCreateUserId(loopWork.getAuditUserId());
            aimg.setCreateTime(DateUtil.currentDate());
            aimg.setModifyTime(DateUtil.currentDate());
            aimg.setModifyUserId(loopWork.getAuditUserId());
            fileMapper.insert(aimg);
            aimgs.add(Long.toString(aimg.getId()));
        });

        TOperateDataForWork toof = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        toof.setAuditUserId(user.getId());
        toof.setAuditImgIds(StringUtils.join(aimgs, ","));
        toof.setAuditResult(atai.getReviewResult());
        toof.setModifyTime(DateUtil.currentDate());

        operateDataForWorkMapper.auditOperateData(toof);
    }

    @Override
    public AssignTaskDetailVo_O viewAssignTaskDetail(String workId, String subWorkId, int workType, SysUser loginUser) {
        AssignTaskDetailVo_O result = new AssignTaskDetailVo_O();
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(workId, subWorkId, workType);
        if(loopWork == null)
            return null;

        AssignTaskStateVo stateVo = new AssignTaskStateVo();
        stateVo.setBackTime(new Date().getTime());
        stateVo.setTaskStatus(loopWork.getWorkStatus()==null?0:loopWork.getWorkStatus());
        TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
        stateVo.setTaskTitle(task.getName()==null?"":task.getName());
        stateVo.setTaskExplain(task.getDescription()==null?"":task.getDescription());
        stateVo.setStartDate(DateUtil.simple(task.getTaskStartDate()));
        stateVo.setEndDate(DateUtil.simple(task.getTaskEndDate()));
        if(loopWork.getExecuteDeadline() != null) stateVo.setSubmitDeadline(loopWork.getExecuteDeadline().getTime());
        if(loopWork.getAuditDeadline() != null) stateVo.setReviewDeadline(loopWork.getAuditDeadline().getTime());
        stateVo.setAuditorID(loopWork.getAuditUserId()==null?0:loopWork.getAuditUserId());
        stateVo.setAuditorName(loopWork.getAuditUserName()==null?"":loopWork.getAuditUserName());
        List<String> ccps = loopWork.getSendUserIds()==null?Arrays.asList(""):Arrays.asList(loopWork.getSendUserIds().split(",")).stream().map(id -> {
            SysUser u = userMapper.selectByPrimaryKey(Long.parseLong(id));
            return u.getRealName();
        }).collect(Collectors.toList());
        stateVo.setCcPersonNames(StringUtils.join(ccps, ","));

        TOperateOption too = operateOptionMapper.fetchOneByTaskId(loopWork.getTaskId());
        stateVo.setDigitalItemName(too.getFeedbackNumName()==null?"":too.getFeedbackNumName());
        stateVo.setDigitalItemType(too.getFeedbackNumType());
        stateVo.setExecutorID(loopWork.getExcuteUserId()==null?0:loopWork.getExcuteUserId());
        stateVo.setExecutorName(loopWork.getExcuteUserName()==null?"":loopWork.getExcuteUserName());
        stateVo.setTaskRank(task.getPreinstallScore()==null?5:Integer.parseInt(task.getPreinstallScore()));
        stateVo.setTaskRepeatType(task.getLoopCycleType());
        stateVo.setTaskRepeatValue(task.getLoopCycleValue()==null?"":task.getLoopCycleValue());
        stateVo.setFeedbackImg(too.getFeedbackNumType());
        stateVo.setFeedbackText(too.getFeedbackNeedText()?0:1);

        List<TFile> timgs = fileMapper.selectByIds(task.getIllustrationImgIds());
        List<ImageUrlVo> ivs = new ArrayList<ImageUrlVo>();
        timgs.forEach(tFile -> {
            ImageUrlVo iv = new ImageUrlVo();
            iv.setImgUrl(tFile.getImgUrl());
            ivs.add(iv);
        });
        stateVo.setTaskImgArr(ivs);
        result.setTaskStatement(stateVo);

        SysUser executeUser = userMapper.selectByPrimaryKey(loopWork.getCreateUserId());
        TOperateDataForWork todw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());

        AssignTaskSubmitDetailVo executorVo = new AssignTaskSubmitDetailVo();
        executorVo.setTaskSubPerID(loopWork.getExcuteUserId());
        executorVo.setTaskSubPerHeadUrl(executeUser==null?"":executeUser.getHeadImgUrl());
        executorVo.setTaskSubTime(loopWork.getExecuteBeginDate().getTime());
        executorVo.setWorkText(todw.getWorkTxt());
        executorVo.setDigitalItemValue(Double.parseDouble(todw.getNumOptionValue()));

        List<ImageUrlVo> svs = new ArrayList<ImageUrlVo>();
        List<TFile> eimgs = fileMapper.selectByIds(todw.getImgIds());
        eimgs.forEach(tFile -> {
            ImageUrlVo sv = new ImageUrlVo();
            sv.setImgUrl(tFile.getImgUrl());
            svs.add(sv);
        });
        executorVo.setExecuteImgArr(svs);
        result.setTaskSubmit(executorVo);

        AssignTaskReviewVo reviewVo = new AssignTaskReviewVo();
        reviewVo.setReviewResult(todw.getAuditResult());
        reviewVo.setReviewID(todw.getId());
        reviewVo.setReviewOpinion(todw.getAuditContent());
        reviewVo.setReviewTime(todw.getModifyTime().getTime());
        reviewVo.setAuditorID(todw.getAuditUserId());
        SysUser u = userMapper.selectByPrimaryKey(todw.getAuditUserId());
        reviewVo.setAuditorName(u.getRealName());
        reviewVo.setAuditorHeadUrl(u.getHeadImgUrl());
        List<TFile> rimgs = fileMapper.selectByIds(todw.getAuditImgIds());
        List<ImageUrlVo> xvs = new ArrayList<ImageUrlVo>();
        rimgs.forEach(tFile -> {
            ImageUrlVo xv = new ImageUrlVo();
            xv.setImgUrl(tFile.getImgUrl());
            xvs.add(xv);
        });
        reviewVo.setReviewImgArr(xvs);
        result.setTaskReview(reviewVo);


        List<AssignTaskReplyVo> replyVos = new ArrayList<AssignTaskReplyVo>();
        List<TTaskComment> wns = commentMapper.fetchByLoopWorkId(loopWork.getId());
        if(wns != null) {
            for (TTaskComment wn : wns) {
                AssignTaskReplyVo rv = new AssignTaskReplyVo();
                rv.setReplyID(wn.getId());
                rv.setReplyContent(wn.getContent());
                if(wn.getCreateTime()!=null) rv.setReplyTime(wn.getCreateTime().getTime());
                if(wn.getUserId()!=null) {
                    rv.setAnswerID(wn.getUserId());
                    rv.setAnswerName(wn.getUserName());
                    SysUser user = userMapper.selectByPrimaryKey(rv.getAnswerID());
                    if (user != null){
                        rv.setAnswerHeadUrl(user.getHeadImgUrl());
                    }
                    if (loginUser.getId() == wn.getUserId()){
                        rv.setReplyAction(2);
                    } else {
                        rv.setReplyAction(1);
                    }
                }
                List<ImageUrlVo> ims = new ArrayList<ImageUrlVo>();
                List<TFile> cimgs = fileMapper.selectByIds(wn.getImgIds());
                cimgs.forEach(tFile -> {
                    ImageUrlVo im = new ImageUrlVo();
                    im.setImgUrl(tFile.getImgUrl());
                    ims.add(im);
                });

                rv.setReplyImgArr(ims);
                replyVos.add(rv);
            }
        }
        result.setTaskReplyArr(replyVos);
        return result;
    }

    @Override
    @Transactional
    public void createAssignTask(AssignTaskCreationVo_I voi, long userId) {
        String uniWorkId = TaskUniqueIdUtils.genUniqueId();

        List<Date> dts = genDatesByRepeatType(voi);
        TTask task = new TTask();

        task.setName(voi.getTaskTitle());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyTime(DateUtil.currentDate());
        task.setIsUse(true);
        task.setCreateUserId(userId);
        task.setModifyUserId(userId);
        task.setDescription(voi.getTaskExplain());
        task.setType(2);
        task.setWorkId(uniWorkId);
        task.setVersion(1);
        task.setPreinstallScore(Integer.toString(voi.getTaskRank()));

        task.setTaskEndDate(DateUtil.strToSimpleYYMMDDDate(voi.getEndDate()));
        task.setTaskStartDate(DateUtil.strToSimpleYYMMDDDate(voi.getStartDate()));
        task.setLoopCycleType(voi.getTaskRepeatType());
        task.setLoopCycleValue(voi.getTaskRepeatValue());

//        task.setAuditDeadline();
//        task.setAuditDptId();
        task.setAuditType(0);
        task.setAuditUserId(voi.getAuditorID());

        task.setExecuteDeadline(voi.getSubmitDeadlineRule());
//        task.setExecuteRemindTime();
//        task.setExecutorDptIds();
//        task.setExecutorPositionIds();
        task.setExecuteUserIds(StringUtils.join(voi.getExecutorArray().stream().map(executorIdVo -> Long.toString(executorIdVo.getExecutorID())).collect(Collectors.toList()),","));
//        task.setExecutorShopIds();

//        task.setCcPositionIds();
        task.setCcUserIds(String.join(",", voi.getCcPersonArray().stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList())));

        List<String> timgids = new ArrayList<String>();
        voi.getTaskImgArr().forEach(imageUrlVo -> {
            com.emucoo.model.TFile timg = new com.emucoo.model.TFile();
            timg.setImgUrl(imageUrlVo.getImgUrl());
            timg.setCreateTime(DateUtil.currentDate());
            timg.setModifyTime(DateUtil.currentDate());
            timg.setCreateUserId(userId);
//            timg.setContentType(); TODO: data structure may be changed in the future.
            fileMapper.insert(timg);
            timgids.add(Long.toString(timg.getId()));
        });
        task.setIllustrationImgIds(StringUtils.join(timgids, ","));

        taskMapper.insert(task);
        long taskId = task.getId();

        TOperateOption too = new TOperateOption();
        too.setFeedbackNeedText(voi.getFeedbackText()==0?false:true);
        too.setFeedbackImgType(voi.getFeedbackImg());
        too.setFeedbackNeedNum(StringUtils.isNotBlank(voi.getDigitalItemName())?true:false);
        too.setFeedbackNumName(voi.getDigitalItemName());
        too.setFeedbackNumType(voi.getDigitalItemType());
        too.setTaskId(taskId);
        operateOptionMapper.insert(too);

        // 根据具体执行时间生产任务实例
        for(Date dt : dts) {
            // 根据执行人产生任务实例
            List<ExecutorIdVo> exeids = voi.getExecutorArray();
            if (exeids != null && exeids.size() > 0) {
                exeids.forEach(executorIdVo -> {
                    TLoopWork lw = new TLoopWork();
                    lw.setTaskId(taskId);
                    lw.setWorkStatus(1);

                    lw.setWorkId(uniWorkId);
                    lw.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
                    lw.setType(2);

                    lw.setExecuteBeginDate(dt);
                    lw.setExecuteEndDate(dt);
                    lw.setExecuteDeadline(DateUtil.yyyyMMddHHmmssStrToDate(DateUtil.simple(dt) + voi.getSubmitDeadlineRule()));

                    lw.setCreateTime(new Date());
                    lw.setModifyTime(new Date());

                    // 存储关联的Executor Ids
                    lw.setExcuteUserId(executorIdVo.getExecutorID());
                    SysUser eUser = userMapper.selectByPrimaryKey(executorIdVo.getExecutorID());
                    if (eUser != null) {
                        lw.setExcuteUserName(eUser.getRealName());
                    }

                    // 存储关联的Auditor
                    lw.setAuditUserId(voi.getAuditorID());
                    SysUser aUser = userMapper.selectByPrimaryKey(lw.getAuditUserId());
                    if (aUser != null) {
                        lw.setAuditUserName(aUser.getRealName());
                    }

                    // 存储关联的ccPerson Ids
                    List<CCPersonIdVo> ccpids = voi.getCcPersonArray();
                    if (ccpids != null && ccpids.size() > 0) {
                        List<String> ccps = ccpids.stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList());
                        List<String> ccpnms = ccpids.stream().map(ccPersonIdVo -> {
                            SysUser u = userMapper.selectByPrimaryKey(ccPersonIdVo.getCcPersonID());
                            return u == null ? null : u.getRealName();
                        }).filter(s -> {
                            return s == null ? false : true;
                        }).collect(Collectors.toList());
                        lw.setSendUserIds(StringUtils.join(ccps, ","));
//                    lw.setSendUserName(StringUtils.join(ccpnms, ","));
                    }
                    loopWorkMapper.insert(lw);

                });
            }
        }
    }

    private List<Date> genDatesByRepeatType(AssignTaskCreationVo_I voi) {
        List<Date> dts = new ArrayList<>();
        int repeatType = voi.getTaskRepeatType();
        switch (repeatType){
            case 0:
                dts.add(DateUtil.strToSimpleYYMMDDDate(voi.getStartDate()));
                break;

            case 1:
                Date sdt = DateUtil.strToSimpleYYMMDDDate(voi.getStartDate());
                Date edt = DateUtil.strToSimpleYYMMDDDate(voi.getEndDate());
                while(DateUtil.compare(sdt, edt) <= 0) {
                    dts.add(sdt);
                    sdt = DateUtil.dateAddDay(sdt, 1);
                }
                break;

            case 2:
                List<Integer> wkdays = Arrays.asList(voi.getTaskRepeatValue().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                Date sdt1 = DateUtil.strToSimpleYYMMDDDate(voi.getStartDate());
                Date edt1 = DateUtil.strToSimpleYYMMDDDate(voi.getEndDate());
                while(DateUtil.compare(sdt1, edt1) <= 0) {
                    if(wkdays.contains(DateUtil.getDayOfWeek(sdt1))){
                        dts.add(sdt1);
                    }
                    sdt1 = DateUtil.dateAddDay(sdt1, 1);
                }
                break;

        }
        return dts;
    }

    @Override
    public ExeHistoryVo_O viewTaskExeHistory(String workType, String workId) {
        List<TLoopWork> loopWorks = loopWorkMapper.fetchTaskExeHistory(workType, workId);

        ExeHistoryVo_O voo = new ExeHistoryVo_O();
        List<ExeHistoryItemVo> items = new ArrayList<ExeHistoryItemVo>();
        for(TLoopWork lw : loopWorks) {
            ExeHistoryItemVo vo = new ExeHistoryItemVo();
            vo.setWorkId(lw.getWorkId());
            vo.setSubWorkId(lw.getSubWorkId());
            vo.setDate(lw.getExecuteDeadline().getTime());
            vo.setStatus(lw.getWorkResult());
            items.add(vo);
        }
        voo.setTotalImplementNum(loopWorks.size());
        voo.setHistoryTaskArr(items);

        return voo;
    }

    @Autowired
    private TTaskMapper taskMapper;
    @Override
    public TTask fetchTaskById(long id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public AssignTaskHistoryVo_O viewAssignTaskHistory(int workType, String workId, String subWorkId) {
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(workId, subWorkId, workType);
        TOperateDataForWork odfw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());

        List<AssignTaskHistoryItemVo> its = new ArrayList<AssignTaskHistoryItemVo>();
        AssignTaskHistoryItemVo hio = new AssignTaskHistoryItemVo();
        hio.setDate(odfw.getCreateTime().getTime());
        hio.setDigitalItemType(odfw.getNumOptionType());
        hio.setValue(Double.parseDouble(odfw.getNumOptionValue()));
        its.add(hio);

        AssignTaskHistoryVo_O result = new AssignTaskHistoryVo_O();
        result.setDigitalItemName(odfw.getNumOptionName());

        result.setHistoryDataArr(its);
        return result;
    }


}