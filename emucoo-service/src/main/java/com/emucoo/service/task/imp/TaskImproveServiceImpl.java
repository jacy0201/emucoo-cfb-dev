package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.*;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn.ImgUrl;
import com.emucoo.dto.modules.task.TaskImproveVo.CCPerson;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.MessageBuilder;
import com.emucoo.service.task.TaskImproveService;
import com.emucoo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shayne.Wang
 * @date 2018-06-12
 *
 */

@Service
public class TaskImproveServiceImpl implements TaskImproveService {

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Autowired
    private TOperateOptionMapper operateOptionMapper;

    @Autowired
    private TOperateDataForWorkMapper operateDataForWorkMapper;

    @Autowired
    private TTaskCommentMapper commentMapper;

    @Autowired
    private MessageBuilder messageBuilder;

    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createImproveTask(TaskImproveVo vo, SysUser user) {
        List<String> timgids = new ArrayList<>();
        vo.getTaskImgArr().forEach(imageUrl -> {
            TFile tImg = new TFile();
            tImg.setImgUrl(imageUrl.getImgUrl());
            fileMapper.insert(tImg);
            timgids.add(Long.toString(tImg.getId()));
        });

        List<CCPerson> ccPersonlist = vo.getCcPersonArray();
        String ccpIds = StringUtils.join(ccPersonlist.stream().map(ccPerson -> ccPerson.getCcPersonID()).collect(Collectors.toList()), ",");
        String ccpNms = StringUtils.join(ccPersonlist.stream().map(ccPerson -> ccPerson.getCcPersonName()).collect(Collectors.toList()), ",");

        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
        TTask task = new TTask();
        task.setWorkId(uniWorkId);
        task.setName(vo.getTaskTitle());
        task.setDescription(vo.getTaskExplain());
        task.setType(3);
        task.setIllustrationImgIds(StringUtils.join(timgids, ","));
        task.setTaskStartDate(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
        task.setTaskEndDate(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
        task.setExecuteDeadline(vo.getSubmitDeadlineRule());
        task.setFrontPlanId(vo.getPatrolShopArrangeID());
        task.setExecutorDptId(vo.getShopID());
        task.setExecuteUserIds(StringUtils.join(vo.getExecutorArray().stream().map(executor -> Long.toString(executor.getExecutorID())).collect(Collectors.toList()), ","));
        task.setAuditType(0);
        task.setIsDel(false);
        task.setIsUse(true);
        task.setAuditUserId(vo.getAuditorID());
        task.setOpptId(vo.getChancePointID());
        task.setLoopCycleType(vo.getTaskRepeatType());
        task.setLoopCycleValue(vo.getTaskRepeatValue());
        task.setReportId(vo.getTaskReportID());
        task.setCreateUserId(user.getId());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyUserId(user.getId());
        task.setModifyTime(DateUtil.currentDate());
        if(vo.getCcPersonArray() != null && vo.getCcPersonArray().size() > 0) {
            task.setCcUserIds(StringUtils.join(ccPersonlist.stream().map(ccPerson -> ccPerson.getCcPersonID()).collect(Collectors.toList()), ","));
        }
        taskMapper.insert(task);

        long taskId = task.getId();
        TOperateOption oo = new TOperateOption();
        oo.setTaskId(taskId);
        oo.setFeedbackNumType(vo.getDigitalItemType());
        oo.setFeedbackNumName(vo.getDigitalItemName());
        oo.setFeedbackNeedNum(StringUtils.isNotBlank(vo.getDigitalItemName()) ? true : false);
        oo.setFeedbackNeedText(vo.getFeedbackText() == 0 ? false : true);
        oo.setFeedbackImgType(vo.getFeedbackImg());
        oo.setPreinstallScore(Integer.toString(vo.getTaskRank()));
        oo.setCreateTime(DateUtil.currentDate());
        oo.setModifyTime(DateUtil.currentDate());
        operateOptionMapper.insert(oo);

        // LoopWork
        List<Date> exeDates = TaskExeDateGenerator.generateExeDatesByTask(task);
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(DateUtil.currentDate()));
        if (TaskExeDateGenerator.isContainsDate(exeDates, today)) {
            createImproveWorkInstance(task, today);
        }
    }

    private void createImproveWorkInstance(TTask task, Date today) {
        // 根据执行人产生任务实例
        if (StringUtils.isBlank(task.getExecuteUserIds())) {
            return;
        }

        List<Long> executorIds = Arrays.asList(task.getExecuteUserIds().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
        for(Long executorId : executorIds) {
            int count = loopWorkMapper.isLoopWorkExist(task.getId(), today, executorId);
            if (count > 0) {
                continue;
            }

            TLoopWork loopWork = new TLoopWork();
            loopWork.setWorkId(task.getWorkId());
            loopWork.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
            loopWork.setTaskId(task.getId());
            loopWork.setExecuteBeginDate(today);
            loopWork.setExecuteEndDate(today);
            if (StringUtils.isNotBlank(task.getExecuteDeadline())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(task.getExecuteDeadline().substring(0, 2)));
                calendar.set(Calendar.MINUTE, Integer.parseInt(task.getExecuteDeadline().substring(2, 4)));
                calendar.set(Calendar.SECOND, Integer.parseInt(task.getExecuteDeadline().substring(4, 6)));
                loopWork.setExecuteDeadline(calendar.getTime());
            } else {
                loopWork.setExecuteDeadline(DateUtil.timeForward(today, 23, 30, 0));
            }
            loopWork.setExecuteRemindTime(DateUtil.timeBackward(loopWork.getExecuteDeadline(), 1, 0, 0));
            loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_1);
            loopWork.setType(ConstantsUtil.LoopWork.TYPE_THREE);
            loopWork.setExcuteUserId(executorId);
            SysUser eUser = userMapper.selectByPrimaryKey(executorId);
            if (eUser != null) {
                loopWork.setExcuteUserName(eUser.getRealName());
            }
            loopWork.setAuditUserId(task.getAuditUserId());
            SysUser aUser = userMapper.selectByPrimaryKey(task.getAuditUserId());
            if(aUser != null) {
                loopWork.setAuditUserName(aUser.getRealName());
            }
            loopWork.setSendUserIds(task.getCcUserIds());
            loopWork.setCreateUserId(task.getCreateUserId());
            SysUser cUser = userMapper.selectByPrimaryKey(task.getCreateUserId());
            if(cUser != null) {
                loopWork.setCreateUserName(cUser.getRealName());
            }
            loopWork.setCreateTime(DateUtil.currentDate());
            loopWork.setModifyTime(DateUtil.currentDate());
            loopWorkMapper.insert(loopWork);

            // 发送创建消息，不推送
            TBusinessMsg businessMsg = messageBuilder.buildLoopWorkCreationBusinessMessage(task, loopWork, eUser, 1);
            businessMsgMapper.insertUseGeneratedKeys(businessMsg);
        }
    }

    /**
     * 计算日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    private int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void submitImproveTask(TaskImproveSubmitIn submitIn, SysUser user) {
        // LoopWork
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(submitIn.getWorkID(), submitIn.getSubID(), submitIn.getWorkType());
        loopWork.setWorkId(submitIn.getWorkID());
        loopWork.setSubWorkId(submitIn.getSubID());
        loopWork.setType(submitIn.getWorkType());
        loopWork.setExcuteUserId(user.getId());
        loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_2);
        // 设置提交时间的12小时后
        loopWork.setModifyTime(new Date());
        loopWork.setAuditDeadline(DateUtil.timeForward(loopWork.getModifyTime(), 12, 0, 0));

        // WorkImgAppend
        List<ImgUrl> list = submitIn.getExecuteImgArr();
        List<TFile> imgs = new ArrayList<>();
        list.forEach(imgUrl -> {
            TFile img = new TFile();
            Date dt = new Date(imgUrl.getDate());
            img.setFileDate(dt);
            img.setLocation(imgUrl.getLocation());
            img.setImgUrl(WaterMarkUtils.genPicUrlWithWaterMark(imgUrl.getImgUrl(), imgUrl.getLocation(), DateUtil.dateToString(dt)));
            img.setCreateTime(DateUtil.currentDate());
            img.setModifyTime(DateUtil.currentDate());
            img.setCreateUserId(user.getId());
            imgs.add(img);
        });
        fileMapper.insertList(imgs);
        List<String> imgids = imgs.stream().map(img -> Long.toString(img.getId())).collect(Collectors.toList());

        TOperateOption oo = operateOptionMapper.fetchOneByTaskId(loopWork.getTaskId());
        TOperateDataForWork odw = new TOperateDataForWork();
        odw.setImgIds(StringUtils.join(imgids, ","));
        odw.setLoopWorkId(loopWork.getId());
        odw.setNumOptionName(oo.getFeedbackNumName());
        if (oo.getFeedbackNeedNum()) {
            odw.setNumOptionType(oo.getFeedbackNumType());
            odw.setNumOptionValue(Double.toString(submitIn.getDigitalItemValue()));
        }
        odw.setWorkTxt(submitIn.getWorkText());
        odw.setCreateTime(DateUtil.currentDate());
        odw.setModifyTime(DateUtil.currentDate());
        operateDataForWorkMapper.insert(odw);

        loopWorkMapper.updateByPrimaryKey(loopWork);

        // 发送任务待审核消息, 并推送
        TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
        TBusinessMsg msg = messageBuilder.buildLoopWorkAuditRemindBusinessMsg(task, loopWork, 3);
        SysUser auditUser = userMapper.selectByPrimaryKey(loopWork.getAuditUserId());
        msg = messageBuilder.pushMessage(msg, auditUser, 3);
        businessMsgMapper.insertUseGeneratedKeys(msg);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void auditImproveTask(TaskImproveAuditIn auditIn, SysUser user) {
        // LoopWork
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(auditIn.getWorkID(), auditIn.getSubID(), auditIn.getWorkType());

        List<TFile> imgs = new ArrayList<>();
        List<ImageUrl> imageUrls = auditIn.getReviewImgArr();
        if (imageUrls != null && imageUrls.size() > 0) {
            imageUrls.forEach(imageUrl -> {
                TFile img = new TFile();
                img.setImgUrl(imageUrl.getImgUrl());
                img.setCreateUserId(user.getId());
                img.setCreateTime(DateUtil.currentDate());
                img.setModifyUserId(user.getId());
                img.setModifyTime(DateUtil.currentDate());
                imgs.add(img);
            });
            fileMapper.insertList(imgs);
        }

        TOperateDataForWork odw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        odw.setAuditUserId(user.getId());
        odw.setAuditContent(auditIn.getReviewOpinion());
        odw.setAuditResult(auditIn.getReviewResult());
        if (imgs.size() > 0) {
            odw.setAuditImgIds(StringUtils.join(imgs.stream().map(img -> Long.toString(img.getId())).collect(Collectors.toList()), ","));
        }
        operateDataForWorkMapper.updateByPrimaryKey(odw);

        loopWork.setWorkId(auditIn.getWorkID());
        loopWork.setType(auditIn.getWorkType());
        loopWork.setSubWorkId(auditIn.getSubID());
        loopWork.setAuditUserId(user.getId());
        loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_4);
        loopWork.setWorkResult(auditIn.getReviewResult());
        loopWork.setAuditTime(DateUtil.currentDate());
        loopWorkMapper.updateByPrimaryKeySelective(loopWork);

        // 发送审核消息
        TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
        TBusinessMsg adtMsg = messageBuilder.buildLoopWorkAuditBusinessMsg(task, loopWork, 5);
        businessMsgMapper.insertUseGeneratedKeys(adtMsg);
        // 发送抄送消息
        List<TBusinessMsg> ccMsgs = messageBuilder.buildLoopWorkCCBusinessMsg(task, loopWork, 7);
        if (ccMsgs.size() > 0) {
            businessMsgMapper.insertList(ccMsgs);
        }
    }

    private List<String> convertImgIds2ImgUrls(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = Arrays.asList(StringUtils.split(ids, ","));
            List<TFile> imgs = fileMapper.fetchFilesByIds(idList);
            return imgs.stream().map(img -> img.getImgUrl()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public TaskImproveDetailOut viewImproveTaskDetail(TaskImproveDetailIn taskImproveDetailIn) {
        TLoopWork loopWork = loopWorkMapper.fetchOneTaskByWorkIds(taskImproveDetailIn.getWorkID(), taskImproveDetailIn.getSubID());
        TaskImproveDetailOut out = new TaskImproveDetailOut();
        // taskStatement
        TaskImproveStatement statement = loopWorkMapper.getTaskImproveStatement(loopWork.getId());
        statement.setBackTime(System.currentTimeMillis());
        statement.setStartDate(statement.getStartDate().replace("-", ""));
        statement.setEndDate(statement.getEndDate().replace("-", ""));
        statement.setSubmitDeadline(statement.getSubmitDeadlineDate().getTime());
        statement.setChancePointNum(1);
        if (statement.getReviewDeadlineDate() != null) {
            statement.setReviewDeadline(statement.getReviewDeadlineDate().getTime());
        }
        statement.setTaskImgArr(convertImgIds2ImgUrls(statement.getImgUrls()).stream().map(url -> {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImgUrl(url);
            return imageUrl;
        }).collect(Collectors.toList()));
        String ccps = statement.getCcPersonNames();
        if (StringUtils.isNotBlank(ccps)) {
            List<String> userNames = Arrays.asList(ccps.split(",")).stream().map(uid -> {
                SysUser usr = userMapper.selectByPrimaryKey(Long.valueOf(uid));
                return usr.getRealName();
            }).collect(Collectors.toList());
            statement.setCcPersonNames(StringUtils.join(userNames, ","));
        }


        // taskSubmit

        TaskImproveSubmit submit = loopWorkMapper.getTaskImproveSubmit(loopWork.getId());
        submit.setExecuteImgArr(convertImgIds2ImgUrls(submit.getImgUrls()).stream().map(url -> {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImgUrl(url);
            return imageUrl;
        }).collect(Collectors.toList()));
        if (submit.getTaskSubTimeDate() != null) {
            submit.setTaskSubTime(submit.getTaskSubTimeDate().getTime());
        }

        TOperateDataForWork odw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        // taskReview
        Review review = new Review();
        if (odw != null) {
            review.setAuditorID(odw.getAuditUserId());
            SysUser u = userMapper.selectByPrimaryKey(odw.getAuditUserId());
            if (u != null) {
                review.setAuditorName(u.getRealName());
                review.setAuditorHeadUrl(u.getHeadImgUrl());
            }
            review.setReviewID(odw.getId());
            review.setReviewResult(odw.getAuditResult());
            review.setReviewOpinion(odw.getAuditContent());
            review.setReviewImgArr(convertImgIds2ImgUrls(odw.getAuditImgIds()).stream().map(url -> {
                ImageUrl imageUrl = new ImageUrl();
                imageUrl.setImgUrl(url);
                return imageUrl;
            }).collect(Collectors.toList()));

        }

        // taskReplyArr
        List<TTaskComment> comments = commentMapper.fetchByLoopWorkId(loopWork.getId());
        List<Answer> answerList = new ArrayList<Answer>();
        comments.forEach(comment -> {
            Answer answer = new Answer();
            answer.setReplyID(Integer.parseInt(String.valueOf(comment.getId())));
            answer.setReplyContent(comment.getContent());
            answer.setReplyTime(comment.getCreateTime().getTime());
            answer.setAnswerID(comment.getUserId().intValue());
            answer.setAnswerName(comment.getUserName());
            SysUser u = userMapper.selectByPrimaryKey(comment.getUserId());
            answer.setAnswerHeadUrl(u.getHeadImgUrl());
            answer.setReplyAction(comment.getIsShow() ? 0 : 1);
            answer.setReplyImgArr(convertImgIds2ImgUrls(comment.getImgIds()).stream().map(url -> {
                ImageUrl imageUrl = new ImageUrl();
                imageUrl.setImgUrl(url);
                return imageUrl;
            }).collect(Collectors.toList()));
            answerList.add(answer);

        });

        out.setTaskStatement(statement);
        out.setTaskSubmit(submit);
        out.setTaskReview(review);
        out.setTaskReplyArr(answerList);
        return out;
    }

    // scheduler useing:

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void buildImproveTaskInstance() {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(DateUtil.currentDate()));
        List<TTask> tasks = taskMapper.filterAvailableImproveTask(today);
        for(TTask task : tasks) {
            createImproveWorkInstance(task, today);
        }
    }
}
