package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.util.StringUtil;
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
import tk.mybatis.mapper.entity.Example;

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
    private TTaskMapper taskMapper;

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
     * @param submitUserId
     * @param date
     * @return
     */
    @Override
    public List<TLoopWork> listPendingExecute(Long submitUserId, Date date) {
        return loopWorkMapper.listPendingExecute(submitUserId, date);
    }

    @Override
    public List<TLoopWork> listPendingReview(Long auditUserId) {
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
        if (voi.getExecuteImgArr() != null && voi.getExecuteImgArr().size() > 0) {
            voi.getExecuteImgArr().forEach(assignTaskSubmitImgVo -> {
                TFile timg = new TFile();
                Date dt = new Date(assignTaskSubmitImgVo.getDate());
                timg.setCreateUserId(lw.getExcuteUserId());
                timg.setModifyUserId(lw.getExcuteUserId());
                timg.setCreateTime(DateUtil.currentDate());
                timg.setModifyTime(DateUtil.currentDate());
                timg.setImgUrl(WaterMarkUtils.genPicUrlWithWaterMark(assignTaskSubmitImgVo.getImgUrl(), assignTaskSubmitImgVo.getLocation(), DateUtil.dateToString(dt)));
                timg.setLocation(assignTaskSubmitImgVo.getLocation());
                timg.setFileDate(dt);
                fileMapper.insert(timg);
                imgids.add(Long.toString(timg.getId()));
            });
        }

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
    @Transactional
    public void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai) {
        TLoopWork loopWork = loopWorkMapper.fetchOneTaskByWorkIds(atai.getWorkID(), atai.getSubID());
        if (loopWork == null)
            return;
        loopWork.setType(atai.getWorkType());
        loopWork.setWorkResult(atai.getReviewResult());
        loopWork.setAuditUserId(user.getId());
        loopWork.setAuditUserName(user.getUsername());

        loopWorkMapper.auditLoopWork(loopWork);

        List<String> aimgs = new ArrayList<>();
        if (atai.getReviewImgArr() != null && atai.getReviewImgArr().size() > 0) {
            atai.getReviewImgArr().forEach(imageUrlVo -> {
                TFile aimg = new TFile();
                aimg.setImgUrl(imageUrlVo.getImgUrl());
                aimg.setCreateUserId(loopWork.getAuditUserId());
                aimg.setCreateTime(DateUtil.currentDate());
                aimg.setModifyTime(DateUtil.currentDate());
                aimg.setModifyUserId(loopWork.getAuditUserId());
                fileMapper.insert(aimg);
                aimgs.add(Long.toString(aimg.getId()));
            });
        }

        TOperateDataForWork toof = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        toof.setAuditUserId(user.getId());
        toof.setAuditImgIds(StringUtils.join(aimgs, ","));
        toof.setAuditResult(atai.getReviewResult());
        toof.setModifyTime(DateUtil.currentDate());
        toof.setAuditContent(atai.getReviewOpinion());

        operateDataForWorkMapper.auditOperateData(toof);
    }

    @Override
    public AssignTaskDetailVo_O viewAssignTaskDetail(String workId, String subWorkId, int workType, SysUser loginUser) {
        AssignTaskDetailVo_O result = new AssignTaskDetailVo_O();
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(workId, subWorkId, workType);
        if (loopWork == null)
            return null;

        AssignTaskStateVo stateVo = new AssignTaskStateVo();
        stateVo.setBackTime(new Date().getTime());
        stateVo.setTaskStatus(loopWork.getWorkStatus() == null ? 0 : loopWork.getWorkStatus());
        TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
        stateVo.setTaskTitle(task.getName() == null ? "" : task.getName());
        stateVo.setTaskExplain(task.getDescription() == null ? "" : task.getDescription());
        stateVo.setStartDate(DateUtil.simple(task.getTaskStartDate()));
        stateVo.setEndDate(DateUtil.simple(task.getTaskEndDate()));
        if (loopWork.getExecuteDeadline() != null) stateVo.setSubmitDeadline(loopWork.getExecuteDeadline().getTime());
        if (loopWork.getAuditDeadline() != null) stateVo.setReviewDeadline(loopWork.getAuditDeadline().getTime());
        stateVo.setAuditorID(loopWork.getAuditUserId() == null ? 0 : loopWork.getAuditUserId());
        stateVo.setAuditorName(loopWork.getAuditUserName() == null ? "" : loopWork.getAuditUserName());
        List<String> ccps = loopWork.getSendUserIds() == null ? Arrays.asList("") : Arrays.asList(loopWork.getSendUserIds().split(",")).stream().map(id -> {
            SysUser u = userMapper.selectByPrimaryKey(Long.parseLong(id));
            return u.getRealName();
        }).collect(Collectors.toList());
        stateVo.setCcPersonNames(StringUtils.join(ccps, ","));

        TOperateOption too = operateOptionMapper.fetchOneByTaskId(loopWork.getTaskId());
        stateVo.setDigitalItemName(too.getFeedbackNumName() == null ? "" : too.getFeedbackNumName());
        stateVo.setDigitalItemType(too.getFeedbackNumType());
        stateVo.setExecutorID(loopWork.getExcuteUserId() == null ? 0 : loopWork.getExcuteUserId());
        stateVo.setExecutorName(loopWork.getExcuteUserName() == null ? "" : loopWork.getExcuteUserName());
        stateVo.setTaskRank(task.getPreinstallScore() == null ? 5 : Integer.parseInt(task.getPreinstallScore()));
        stateVo.setTaskRepeatType(task.getLoopCycleType());
        stateVo.setTaskRepeatValue(task.getLoopCycleValue() == null ? "" : task.getLoopCycleValue());
        stateVo.setFeedbackImg(too.getFeedbackImgType());
        stateVo.setFeedbackText(too.getFeedbackNeedText() ? 2 : 1);

        List<ImageUrlVo> ivs = new ArrayList<ImageUrlVo>();
        String timgIds = task.getIllustrationImgIds();
        if (StringUtils.isNotBlank(timgIds)) {
            List<TFile> timgs = fileMapper.selectByIds(timgIds);
            timgs.forEach(tFile -> {
                ImageUrlVo iv = new ImageUrlVo();
                iv.setImgUrl(tFile.getImgUrl());
                ivs.add(iv);
            });
        }
        stateVo.setTaskImgArr(ivs);
        result.setTaskStatement(stateVo);

        SysUser executeUser = userMapper.selectByPrimaryKey(loopWork.getCreateUserId());
        TOperateDataForWork todw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());

        AssignTaskSubmitDetailVo executorVo = new AssignTaskSubmitDetailVo();
        executorVo.setTaskSubPerID(loopWork.getExcuteUserId());
        executorVo.setTaskSubPerHeadUrl(executeUser == null ? "" : executeUser.getHeadImgUrl());
        executorVo.setTaskSubTime(loopWork.getModifyTime().getTime());
        executorVo.setWorkText(todw == null ? "" : todw.getWorkTxt());
        executorVo.setDigitalItemValue(Double.parseDouble(todw == null ? "0.0" : todw.getNumOptionValue()));

        List<ImageUrlVo> svs = new ArrayList<ImageUrlVo>();
        String eimgIds = todw == null ? "" : todw.getImgIds();
        if (StringUtils.isNotBlank(eimgIds)) {
            List<TFile> eimgs = fileMapper.selectByIds(eimgIds);
            eimgs.forEach(tFile -> {
                ImageUrlVo sv = new ImageUrlVo();
                sv.setImgUrl(tFile.getImgUrl());
                svs.add(sv);
            });
        }
        executorVo.setExecuteImgArr(svs);
        result.setTaskSubmit(executorVo);

        AssignTaskReviewVo reviewVo = new AssignTaskReviewVo();
        if (todw != null) {
            reviewVo.setReviewResult(todw.getAuditResult() == null ? 0 : todw.getAuditResult());
            reviewVo.setReviewID(todw.getId());
            reviewVo.setReviewOpinion(todw.getAuditContent());
            reviewVo.setReviewTime(todw.getModifyTime().getTime());
            reviewVo.setAuditorID(todw.getAuditUserId() == null ? 0 : todw.getAuditUserId());
            SysUser u = userMapper.selectByPrimaryKey(reviewVo.getAuditorID());
            if (u != null) {
                reviewVo.setAuditorName(u.getRealName());
                reviewVo.setAuditorHeadUrl(u.getHeadImgUrl());
            }
            List<ImageUrlVo> xvs = new ArrayList<ImageUrlVo>();
            if (StringUtils.isNotBlank(todw.getAuditImgIds())) {
                List<TFile> rimgs = fileMapper.selectByIds(todw.getAuditImgIds());
                rimgs.forEach(tFile -> {
                    ImageUrlVo xv = new ImageUrlVo();
                    xv.setImgUrl(tFile.getImgUrl());
                    xvs.add(xv);
                });
                reviewVo.setReviewImgArr(xvs);
            }
        }
        result.setTaskReview(reviewVo);


        List<AssignTaskReplyVo> replyVos = new ArrayList<AssignTaskReplyVo>();
        List<TTaskComment> wns = commentMapper.fetchByLoopWorkId(loopWork.getId());
        if (wns != null) {
            for (TTaskComment wn : wns) {
                AssignTaskReplyVo rv = new AssignTaskReplyVo();
                rv.setReplyID(wn.getId());
                rv.setReplyContent(wn.getContent());
                if (wn.getCreateTime() != null) rv.setReplyTime(wn.getCreateTime().getTime());
                if (wn.getUserId() != null) {
                    rv.setAnswerID(wn.getUserId());
                    rv.setAnswerName(wn.getUserName());
                    SysUser user = userMapper.selectByPrimaryKey(rv.getAnswerID());
                    if (user != null) {
                        rv.setAnswerHeadUrl(user.getHeadImgUrl());
                    }
                    if (loginUser.getId() == wn.getUserId()) {
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

    /**
     * 工作备忘详情查询
     * @param workId
     * @param subWorkId
     * @param workType
     * @param loginUser
     * @return
     */
    @Override
    public MemoDetailVo_O viewMemoDetail(String workId, String subWorkId, Integer workType, SysUser loginUser){

        MemoDetailVo_O memoDetailVo=null;
        TLoopWork loopWork=loopWorkMapper.fetchByWorkIdAndType(workId,subWorkId,workType);
        if(null!=loopWork){
            memoDetailVo=new MemoDetailVo_O();
            memoDetailVo.setWorkID(workId);
            memoDetailVo.setSubID(subWorkId);
            memoDetailVo.setWorkType(workType);
            memoDetailVo.setStartDateTime(loopWork.getExecuteBeginDate().getTime());
            memoDetailVo.setEndDateTime(loopWork.getExecuteEndDate().getTime());
            memoDetailVo.setIsSign(loopWork.getIsSign());
            memoDetailVo.setRemindType(loopWork.getExecuteRemindType());
            memoDetailVo.setMemoStatus(loopWork.getWorkStatus());

            List<MemoDetailVo_O.CCPerson> ccList = new ArrayList<>();
            String [] userIds=loopWork.getSendUserIds().split(",");
            if(null!=userIds && userIds.length>0){
                MemoDetailVo_O.CCPerson ccPerson=null;
                for (String id:userIds) {
                    ccPerson=new  MemoDetailVo_O.CCPerson();
                    SysUser u = userMapper.selectByPrimaryKey(Long.parseLong(id));
                    if(u!=null) {
                        ccPerson.setCcPersonID(u.getId());
                        ccPerson.setCcPersonName(u.getRealName());
                        ccPerson.setHeadImgUrl(u.getHeadImgUrl());
                    }
                    ccList.add(ccPerson);
                }
                memoDetailVo.setCcPersonList(ccList);
            }
            TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
            memoDetailVo.setTaskTitle(task.getName());
            memoDetailVo.setTaskExplain(task.getDescription());
            memoDetailVo.setTaskRepeatType(task.getLoopCycleType());
            memoDetailVo.setTaskRepeatValue(task.getLoopCycleValue());
            if(StringUtil.isNotEmpty(task.getIllustrationImgIds())) {
                List<ImageUrlVo> ims = new ArrayList<ImageUrlVo>();
                List<TFile> cimgs = fileMapper.selectByIds(task.getIllustrationImgIds());
                if(null!=cimgs && cimgs.size()>0) {
                    cimgs.forEach(tFile -> {
                        ImageUrlVo im = new ImageUrlVo();
                        im.setImgUrl(tFile.getImgUrl());
                        ims.add(im);
                    });
                    memoDetailVo.setTaskImgArr(ims);
                }
            }

        }
        return  memoDetailVo;

    }


    @Override
    @Transactional
    public void createAssignTask(AssignTaskCreationVo_I voi, long userId) {
        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
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
        task.setIsDel(false);
        task.setIsUse(true);
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
        task.setExecuteUserIds(StringUtils.join(voi.getExecutorArray().stream().map(executorIdVo -> Long.toString(executorIdVo.getExecutorID())).collect(Collectors.toList()), ","));
//        task.setExecutorShopIds();

//        task.setCcPositionIds();
        if (voi.getCcPersonArray() != null && voi.getCcPersonArray().size() > 0)
            task.setCcUserIds(String.join(",", voi.getCcPersonArray().stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList())));

        List<String> timgids = new ArrayList<String>();
        voi.getTaskImgArr().forEach(imageUrlVo -> {
            TFile timg = new TFile();
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
        too.setFeedbackNeedText(voi.getFeedbackText() == 0 ? false : true);
        too.setFeedbackImgType(voi.getFeedbackImg());
        too.setFeedbackNeedNum(StringUtils.isNotBlank(voi.getDigitalItemName()) ? true : false);
        too.setFeedbackNumName(voi.getDigitalItemName());
        too.setFeedbackNumType(voi.getDigitalItemType());
        too.setTaskId(taskId);
        operateOptionMapper.insert(too);

//         根据执行时间生产任务实例,先生成当天的任务实例，其他的任务实例由定时任务产生。
        Date today = DateUtil.currentDate();
        List<Date> dts = genDatesByRepeatType(task);
        if(isContainsDate(dts, today)) {
            buildLoopWorkInstance(task, today);
        }

    }

    private void buildLoopWorkInstance(TTask task, Date dt) {
        // 根据执行人产生任务实例
        if (StringUtils.isBlank(task.getExecuteUserIds())) {
            return;
        }

        List<Long> exeids = Arrays.asList(task.getExecuteUserIds().split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
        if (exeids != null && exeids.size() > 0) {
            return;
        }

        for (Long executorId : exeids) {
            // 判断数据库里是否已经有这条数据了。
            int count = loopWorkMapper.isLoopWorkExist(task.getId(), dt, executorId);
            if (count > 0)
                continue;

            TLoopWork lw = new TLoopWork();
            lw.setTaskId(task.getId());
            lw.setWorkStatus(1);

            lw.setWorkId(task.getWorkId());
            lw.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
            lw.setType(2);

            lw.setExecuteBeginDate(dt);
            lw.setExecuteEndDate(dt);
            lw.setExecuteDeadline(DateUtil.yyyyMMddHHmmssStrToDate(DateUtil.simple(dt) + task.getExecuteDeadline()));

            lw.setCreateTime(new Date());
            lw.setModifyTime(new Date());

            // 存储关联的Executor Ids
            lw.setExcuteUserId(executorId);
            SysUser eUser = userMapper.selectByPrimaryKey(executorId);
            if (eUser != null) {
                lw.setExcuteUserName(eUser.getRealName());
            }

            // 存储关联的Auditor
            lw.setAuditUserId(task.getAuditUserId());
            SysUser aUser = userMapper.selectByPrimaryKey(lw.getAuditUserId());
            if (aUser != null) {
                lw.setAuditUserName(aUser.getRealName());
            }

            // 存储关联的ccPerson Ids
            if (StringUtils.isNotBlank(task.getCcUserIds())) {
                lw.setSendUserIds(task.getCcUserIds());
//                        List<String> ccpids = Arrays.asList(task.getCcUserIds().split(","));
//                        if (ccpids != null && ccpids.size() > 0) {
//                            List<String> ccpnms = ccpids.stream().map(ccPersonId -> {
//                                SysUser u = userMapper.selectByPrimaryKey(Long.parseLong(ccPersonId));
//                                return u == null ? null : u.getRealName();
//                            }).filter(s -> {
//                                return s == null ? false : true;
//                            }).collect(Collectors.toList());
//                            lw.setSenderUserNames(task.getCcUserIds());
//                        }
            }
            loopWorkMapper.insert(lw);

        }
    }

    private Date getRemindTime(Integer type,Date dt){
        Date date=null;
        switch (type){
            case 2:
                date=DateUtil.getDateMinusMinutes(dt,15);
                break;
            case 3:
                date= DateUtil.getDateMinusMinutes(dt,30);
                break;
            case 4:
                date= DateUtil.getDateMinusMinutes(dt,60);
                break;
            case 5:
                date= DateUtil.getDateMinusMinutes(dt,120);
                break;
            case 6:
                date= DateUtil.getDateMinusMinutes(dt,60*24);
                break;
            case 7:
                break;
        }
        return date;
    }

    /**
     * 创建工作备忘
     * @param voi
     * @param userId
     */
    @Override
    @Transactional
    public void createMemo(MemoCreationVo_I voi, Long userId) {
        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
        TTask task = new TTask();
        task.setName(voi.getTaskTitle());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyTime(DateUtil.currentDate());
        task.setCreateUserId(userId);
        task.setModifyUserId(userId);
        task.setDescription(voi.getTaskExplain());
        task.setType(5);
        task.setWorkId(uniWorkId);
        task.setVersion(1);
        task.setIsDel(false);
        task.setIsUse(true);
        task.setTaskEndDate(DateUtil.strToYYMMDDDate(voi.getEndDate()));
        task.setTaskStartDate(DateUtil.strToYYMMDDDate(voi.getStartDate()));
        task.setLoopCycleType(voi.getTaskRepeatType());
        task.setLoopCycleValue(voi.getTaskRepeatValue());
        task.setExecuteUserIds(userId.toString());

        if(voi.getCcPersonArray() != null && voi.getCcPersonArray().size() > 0)
            task.setCcUserIds(String.join(",", voi.getCcPersonArray().stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList())));

        if(null!=voi.getTaskImgArr()) {
            List<String> timgids = new ArrayList<String>();
            voi.getTaskImgArr().forEach(imageUrlVo -> {
                TFile timg = new TFile();
                timg.setImgUrl(imageUrlVo.getImgUrl());
                timg.setCreateTime(DateUtil.currentDate());
                timg.setModifyTime(DateUtil.currentDate());
                timg.setCreateUserId(userId);
                fileMapper.insert(timg);
                timgids.add(Long.toString(timg.getId()));
            });
            task.setIllustrationImgIds(StringUtils.join(timgids, ","));
        }
        taskMapper.insertUseGeneratedKeys(task);
        List<Date> dts = genDatesByRepeatType(task);
        Long taskId = task.getId();

        // 根据具体执行时间生产任务实例
        for(Date dt : dts) {
            // 根据执行人产生任务实例
            TLoopWork lw = new TLoopWork();
            lw.setTaskId(taskId);
            lw.setWorkId(uniWorkId);
            lw.setIsSign(voi.getIsSign());
            lw.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
            lw.setType(5);
            lw.setWorkStatus(1);
            lw.setCreateUserId(userId);
            lw.setExecuteBeginDate(DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+voi.getStartTime()));
            lw.setExecuteEndDate(DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+"23:59"));
            lw.setExecuteRemindType(voi.getRemindType());
            Date RemindTime=null;
            //type=1 ，提醒时间为 日程开始时间
            if(null!=voi.getRemindType() && voi.getRemindType().equals(1)){
                RemindTime=lw.getExecuteBeginDate();
            }else{
                RemindTime=getRemindTime(voi.getRemindType(),DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+voi.getStartTime()));
            }
            lw.setExecuteRemindTime(RemindTime);
            lw.setCreateTime(new Date());
            lw.setModifyTime(new Date());
            lw.setExcuteUserId(userId);
            // 存储关联的Executor Ids
            SysUser eUser = userMapper.selectByPrimaryKey(userId);
            if (eUser != null) {
                lw.setExcuteUserName(eUser.getRealName());
            }
            // 存储关联的ccPerson Ids
            List<CCPersonIdVo> ccpids = voi.getCcPersonArray();
            if (ccpids != null && ccpids.size() > 0) {
                List<String> ccps = ccpids.stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList());
                lw.setSendUserIds(StringUtils.join(ccps, ","));
            }
            loopWorkMapper.insertSelective(lw);
        }

    }

    /**
     * 编辑工作备忘
     * @param voi
     * @param userId
     */
    @Override
    @Transactional
    public void editMemo(MemoEditVo_I voi, Long userId) {
        Example example=new Example(TTask.class);
        example.createCriteria().andEqualTo("workId",voi.getWorkID()).andEqualTo("isDel",false);
        //更新工作备忘主表 t_task
        TTask tTask =taskMapper.selectOneByExample(example);
        Long taskId=tTask.getId();
        TTask task=new TTask();
        task.setId(taskId);
        task.setName(voi.getTaskTitle());
        task.setModifyTime(DateUtil.currentDate());
        task.setModifyUserId(userId);
        task.setDescription(voi.getTaskExplain());
        task.setType(5);
        task.setWorkId(voi.getWorkID());
        task.setTaskEndDate(DateUtil.strToYYMMDDDate(voi.getEndDate()));
        task.setTaskStartDate(DateUtil.strToYYMMDDDate(voi.getStartDate()));
        task.setLoopCycleType(voi.getTaskRepeatType());
        task.setLoopCycleValue(voi.getTaskRepeatValue());
        task.setExecuteUserIds(userId.toString());
        if(voi.getCcPersonArray() != null && voi.getCcPersonArray().size() > 0)
            task.setCcUserIds(String.join(",", voi.getCcPersonArray().stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList())));

        List<ImageUrlVo> imgList=voi.getTaskImgArr();
        if(null!=imgList && imgList.size()>0){
            List<String> timgids = new ArrayList<String>();
            imgList.forEach(imageUrlVo -> {
                TFile timg = new TFile();
                timg.setImgUrl(imageUrlVo.getImgUrl());
                timg.setCreateTime(DateUtil.currentDate());
                timg.setModifyTime(DateUtil.currentDate());
                timg.setCreateUserId(userId);
                fileMapper.insert(timg);
                timgids.add(Long.toString(timg.getId()));
            });
            task.setIllustrationImgIds(StringUtils.join(timgids, ","));
        }
        taskMapper.updateByExampleSelective(task,example);

        //先删除之前创建的备忘实例
        Example exampleTLoopWork=new Example(TLoopWork.class);
        exampleTLoopWork.createCriteria().andEqualTo("workId",voi.getWorkID()).andEqualTo("type",5);
        loopWorkMapper.deleteByExample(exampleTLoopWork);

        List<Date> dts = genDatesByRepeatType(task);
        //再重新创建备忘实例
        for(Date dt : dts) {
            // 根据执行人产生任务实例
            TLoopWork lw = new TLoopWork();
            lw.setTaskId(taskId);
            lw.setWorkId(voi.getWorkID());
            lw.setIsSign(voi.getIsSign());
            lw.setSubWorkId(voi.getSubWorkID());
            lw.setType(5);
            lw.setWorkStatus(1);
            lw.setCreateUserId(userId);
            lw.setExecuteBeginDate(DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+voi.getStartTime()));
            lw.setExecuteEndDate(DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+"23:59"));
            lw.setExecuteRemindType(voi.getRemindType());
            Date RemindTime=null;
            //type=1 ，提醒时间为 日程开始时间
            if(null!=voi.getRemindType() && voi.getRemindType().equals(1)){
                RemindTime=lw.getExecuteBeginDate();
            }else{
                RemindTime=getRemindTime(voi.getRemindType(),DateUtil.toDateYYYYMMDDHHMM(DateUtil.dateToString1(dt)+" "+voi.getStartTime()));
            }
            lw.setExecuteRemindTime(RemindTime);
            lw.setCreateTime(new Date());
            lw.setModifyTime(new Date());
            lw.setExcuteUserId(userId);
            // 存储关联的Executor Ids
            SysUser eUser = userMapper.selectByPrimaryKey(userId);
            if (eUser != null) {
                lw.setExcuteUserName(eUser.getRealName());
            }
            // 存储关联的ccPerson Ids
            List<CCPersonIdVo> ccpids = voi.getCcPersonArray();
            if (ccpids != null && ccpids.size() > 0) {
                List<String> ccps = ccpids.stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList());
                lw.setSendUserIds(StringUtils.join(ccps, ","));
            }
            loopWorkMapper.insertSelective(lw);
        }

    }

    /**
     * 删除工作备忘
     * @param voi
     * @param userId
     */
    @Override
    @Transactional
    public void deleteMemo(MemoDeleteVo_I voi, Long userId) {
        //删除工作备忘主表 t_task
        Example example=new Example(TTask.class);
        example.createCriteria().andEqualTo("workId",voi.getWorkID());
        taskMapper.deleteByExample(example);
        //删除工作备忘实例
        Example exampleTLoopWork=new Example(TLoopWork.class);
        exampleTLoopWork.createCriteria().andEqualTo("workId",voi.getWorkID()).andEqualTo("type",5);
        loopWorkMapper.deleteByExample(exampleTLoopWork);
    }

    private List<Date> genDatesByRepeatType(TTask task) {
        List<Date> dts = new ArrayList<>();
        int repeatType = task.getLoopCycleType();
        switch (repeatType) {
            case 0:
                dts.add(task.getTaskStartDate());
                break;

            case 1:
                Date sdt = task.getTaskStartDate();
                Date edt = task.getTaskEndDate();
                while (DateUtil.compare(sdt, edt) <= 0) {
                    dts.add(sdt);
                    sdt = DateUtil.dateAddDay(sdt, 1);
                }
                break;

            case 2:
                List<Integer> wkdays = Arrays.asList(task.getLoopCycleValue().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                Date sdt1 = task.getTaskStartDate();
                Date edt1 = task.getTaskEndDate();
                while (DateUtil.compare(sdt1, edt1) <= 0) {
                    if (wkdays.contains(DateUtil.getDayOfWeek(sdt1))) {
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
        for (TLoopWork lw : loopWorks) {
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

    @Override
    public void finishMemo(MemoFinishVo_I  memoFinishVo_I, Long userId){
        Example example=new Example(TLoopWork.class);
        example.createCriteria().andEqualTo("workId",memoFinishVo_I.getWorkID()).andEqualTo("subWorkId",memoFinishVo_I.getSubWorkID());
        TLoopWork tLoopWork=new TLoopWork();
        tLoopWork.setWorkStatus(memoFinishVo_I.getMemoStatus());
        tLoopWork.setModifyTime(new Date());
        loopWorkMapper.updateByExampleSelective(tLoopWork,example);
    }


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

    // begin：以下方法是给定时任务使用的

    @Override
    @Transactional
    public void markExpiredWorks() {
        Date dt = DateUtil.currentDate();
        loopWorkMapper.markExpiredExecutionWorks(dt);
        loopWorkMapper.markExpiredAuditWorks(dt);
    }

    @Override
    public List<TLoopWork> filterNeedExecuteRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes) {
        Date deadTimeLeft = DateUtil.timeForward(currentDate, 0, aheadMinutes);
        Date deadTimeRight = DateUtil.timeForward(currentDate, 0, aheadMinutes + cycleMinutes);
        Date remindTimeLeft = currentDate;
        Date remindTimeRight = DateUtil.timeForward(currentDate, 0, cycleMinutes);
        return loopWorkMapper.filterExecuteRemindWorks(deadTimeLeft, deadTimeRight, remindTimeLeft, remindTimeRight);
    }

    @Override
    public List<TLoopWork> filterNeedAuditRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes) {
        Date deadTimeLeft = DateUtil.timeForward(currentDate, 0, aheadMinutes);
        Date deadTimeRight = DateUtil.timeForward(currentDate, 0, aheadMinutes + cycleMinutes);
        Date remindTimeLeft = currentDate;
        Date remindTimeRight = DateUtil.timeForward(currentDate, 0, cycleMinutes);
        return loopWorkMapper.filterAuditRemindWorks(deadTimeLeft, deadTimeRight, remindTimeLeft, remindTimeRight);
    }

    private boolean isContainsDate(List<Date> dates, Date dt) {
        for (Date date : dates) {
            if (DateUtil.simple(date).equals(DateUtil.simple(dt)))
                return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void buildAssingTaskInstance() {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(DateUtil.currentDate()));
        List<TTask> assignTasks = taskMapper.filterAvailableAssignTask(today);
        for (TTask task : assignTasks) {
            List<Date> dts = genDatesByRepeatType(task);
            if (isContainsDate(dts, today)) {
                buildLoopWorkInstance(task, today);
            }
        }
    }
    // end：定时任务方法完
}
