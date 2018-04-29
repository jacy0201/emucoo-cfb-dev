package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.*;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn.ImgUrl;
import com.emucoo.dto.modules.task.TaskImproveVo.CCPerson;
import com.emucoo.dto.modules.task.TaskImproveVo.Executor;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.TaskImproveService;
import com.emucoo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
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

	/*@Autowired
	private WaterMarkUtils waterMarkUtils;*/

    @Override
    public void createImproveTask(TaskImproveVo vo, SysUser user) {
        List<String> timgids = new ArrayList<>();
        vo.getTaskImgArr().forEach(imageUrl -> {
            TFile tImg = new TFile();
            tImg.setImgUrl(imageUrl.getImgUrl());
            fileMapper.insert(tImg);
            timgids.add(Long.toString(tImg.getId()));
        });

        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
        TTask task = new TTask();
        task.setWorkId(uniWorkId);
        task.setName(vo.getTaskTitle());
        task.setDescription(vo.getTaskExplain());
        task.setType(3);
        task.setIllustrationImgIds(StringUtils.join(timgids));
        task.setTaskStartDate(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
        task.setTaskEndDate(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
        task.setExecuteDeadline(vo.getSubmitDeadlineRule());
        task.setFrontPlanId(vo.getPatrolShopArrangeID());
        task.setExecutorDptId(vo.getShopID());
        task.setExecuteUserIds(StringUtils.join(vo.getExecutorArray().stream().map(executor -> Long.toString(executor.getExecutorID())).collect(Collectors.toList()), ","));
        task.setAuditType(0);
        task.setAuditUserId(vo.getAuditorID());
        task.setOpptId(vo.getChancePointID());
        task.setLoopCycleType(vo.getTaskRepeatType());
        task.setLoopCycleValue(vo.getTaskRepeatValue());
        task.setReportId(vo.getTaskReportID());
        task.setCreateUserId(user.getId());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyUserId(user.getId());
        task.setModifyTime(DateUtil.currentDate());
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
        List<TLoopWork> loopWorkList = new ArrayList<TLoopWork>();

        List<CCPerson> ccPersonlist = vo.getCcPersonArray();
        String ccpIds = StringUtils.join(ccPersonlist.stream().map(ccPerson -> ccPerson.getCcPersonID()).collect(Collectors.toList()), ",");
        String ccpNms = StringUtils.join(ccPersonlist.stream().map(ccPerson -> ccPerson.getCcPersonName()).collect(Collectors.toList()), ",");

        List<Executor> executorList = vo.getExecutorArray();

        // create task instance for each executor.
        for (Executor executor : executorList) {
            // 重复规则逻辑处理
            if (vo.getTaskRepeatType() == 0) {// 不重复

                Date beginDt = DateUtil.strToSimpleYYMMDDDate(vo.getStartDate());
                Date endDt = DateUtil.strToSimpleYYMMDDDate(vo.getEndDate());
                Date deadlineDt = DateUtil.yyyyMMddHHmmssStrToDate(vo.getEndDate() + vo.getSubmitDeadlineRule());

                TLoopWork loopWork = buildLoopWork(vo, user, uniWorkId, taskId, ccpIds, executor, beginDt, endDt, deadlineDt);
                loopWorkList.add(loopWork);
            } else if (vo.getTaskRepeatType() == 1) {// 每天
                Date startDate = DateUtil.strToSimpleYYMMDDDate(vo.getStartDate());
                Date endDate = DateUtil.strToSimpleYYMMDDDate(vo.getEndDate());
                int days = daysBetween(startDate, endDate);
                for (int i = 0; i <= days; i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DAY_OF_MONTH, i);
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vo.getSubmitDeadlineRule().substring(0, 2)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(vo.getSubmitDeadlineRule().substring(2, 4)));
                    calendar.set(Calendar.SECOND, Integer.parseInt(vo.getSubmitDeadlineRule().substring(4, 6)));

                    Date beginDt = calendar.getTime();
                    Date endDt = calendar.getTime();
                    Date deadlineDt = calendar.getTime();

                    TLoopWork loopWork = buildLoopWork(vo, user, uniWorkId, taskId, ccpIds, executor, beginDt, endDt, deadlineDt);
                    loopWorkList.add(loopWork);
                }
            } else if (vo.getTaskRepeatType() == 2) {// 每周
                // 符合条件日期集合
                List<String> list = WeekDayUtil.getDates(vo.getStartDate(), vo.getEndDate(), vo.getTaskRepeatValue());

                for (String date : list) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.strToSimpleYYMMDDDate(date));
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vo.getSubmitDeadlineRule().substring(0, 2)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(vo.getSubmitDeadlineRule().substring(2, 4)));
                    calendar.set(Calendar.SECOND, Integer.parseInt(vo.getSubmitDeadlineRule().substring(4, 6)));

                    Date beginDt = DateUtil.strToSimpleYYMMDDDate(date);
                    Date endDt = DateUtil.strToSimpleYYMMDDDate(date);
                    Date deadlineDt = calendar.getTime();

                    TLoopWork loopWork = buildLoopWork(vo, user, uniWorkId, taskId, ccpIds, executor, beginDt, endDt, deadlineDt);
                    loopWorkList.add(loopWork);
                }
            }
        }
        loopWorkMapper.insertList(loopWorkList);
    }

    private TLoopWork buildLoopWork(TaskImproveVo vo, SysUser user, String uniWorkId, long taskId, String ccpIds, Executor executor, Date beginDt, Date endDt, Date dateDeadline) {
        TLoopWork loopWork = new TLoopWork();
        loopWork.setWorkId(uniWorkId);
        loopWork.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
        loopWork.setTaskId(taskId);

        loopWork.setExecuteBeginDate(beginDt);
        loopWork.setExecuteEndDate(endDt);
        loopWork.setExecuteDeadline(dateDeadline);
// TODO:                loopWork.setExecuteRemindTime();

        loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_ONE);
        loopWork.setType(ConstantsUtil.LoopWork.TYPE_THREE);

        loopWork.setExcuteUserId(executor.getExecutorID());
        loopWork.setExcuteUserName(executor.getExecutorName());
        loopWork.setAuditUserId(vo.getAuditorID());
        loopWork.setAuditUserName(vo.getAuditorName());
        loopWork.setSendUserIds(ccpIds);

        loopWork.setCreateUserId(user.getId());
        loopWork.setCreateUserName(user.getUsername());
        loopWork.setCreateTime(DateUtil.currentDate());
        loopWork.setModifyTime(DateUtil.currentDate());
        return loopWork;
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
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public void submitImproveTask(TaskImproveSubmitIn submitIn, SysUser user) {
        // LoopWork
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(submitIn.getWorkID(), submitIn.getSubID(), submitIn.getWorkType());
        loopWork.setWorkId(submitIn.getWorkID());
        loopWork.setSubWorkId(submitIn.getSubID());
        loopWork.setType(submitIn.getWorkType());
        loopWork.setExcuteUserId(user.getId());
        loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_TWO);

        // 设置提交时间的12小时后
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.HOUR_OF_DAY, 12);
        loopWork.setAuditDeadline(cl.getTime());
        loopWork.setModifyTime(new Date());

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
        loopWorkMapper.updateByPrimaryKey(loopWork);


        List<String> imgids = imgs.stream().map(img-> Long.toString(img.getId())).collect(Collectors.toList());

        TOperateOption oo = operateOptionMapper.fetchOneByTaskId(loopWork.getTaskId());
        TOperateDataForWork odw = new TOperateDataForWork();
        odw.setImgIds(StringUtils.join(imgids, ","));
        odw.setLoopWorkId(loopWork.getId());
        odw.setNumOptionName(oo.getFeedbackNumName());
        odw.setNumOptionType(oo.getFeedbackNumType());
        odw.setNumOptionValue(Double.toString(submitIn.getDigitalItemValue()));
        odw.setWorkTxt(submitIn.getWorkText());
//        odw.setCreateUserId(user.getId());
        odw.setCreateTime(DateUtil.currentDate());
        odw.setModifyTime(DateUtil.currentDate());
//        odw.setModifyUserId(user.getId());
        operateDataForWorkMapper.insert(odw);

    }

    @Override
    public void auditImproveTask(TaskImproveAuditIn auditIn, SysUser user) {
        // LoopWork
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(auditIn.getWorkID(), auditIn.getSubID(), auditIn.getWorkType());
        loopWork.setWorkId(auditIn.getWorkID());
        loopWork.setType(auditIn.getWorkType());
        loopWork.setSubWorkId(auditIn.getSubID());
        loopWork.setAuditUserId(user.getId());
        loopWork.setWorkResult(auditIn.getReviewResult());
        loopWorkMapper.updateByPrimaryKey(loopWork);

        List<TFile> imgs = new ArrayList<>();
        auditIn.getReviewImgArr().forEach(imageUrl -> {
            TFile img = new TFile();
            img.setImgUrl(imageUrl.getImgUrl());
            img.setCreateUserId(user.getId());
            img.setCreateTime(DateUtil.currentDate());
            img.setModifyUserId(user.getId());
            img.setModifyTime(DateUtil.currentDate());
            imgs.add(img);
        });
        fileMapper.insertList(imgs);

        TOperateDataForWork odw = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        odw.setAuditUserId(user.getId());
        odw.setAuditContent(auditIn.getReviewOpinion());
        odw.setAuditResult(auditIn.getReviewResult());
        odw.setAuditImgIds(StringUtils.join(imgs, ","));
        operateDataForWorkMapper.updateByPrimaryKey(odw);
    }

    private List<String> convertImgIds2ImgUrls(String ids) {
        if(StringUtils.isNotBlank(ids)) {
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
        statement.setBackTime(new Date().getTime());
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
        if(StringUtils.isNotBlank(ccps)) {
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
            if(u != null) {
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
            answer.setReplyAction(comment.getIsShow()?0:1);
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

}
