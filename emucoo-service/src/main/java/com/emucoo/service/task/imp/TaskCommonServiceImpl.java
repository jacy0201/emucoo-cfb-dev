package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.TaskCommonService;
import com.emucoo.utils.DateUtil;
import com.emucoo.utils.WaterMarkUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskCommonServiceImpl implements TaskCommonService {

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Autowired
    private TOperateDataForWorkMapper operateDataForWorkMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Autowired
    private TOperateOptionMapper operateOptionMapper;

    @Override
    public TaskCommonDetailOut detail(TaskCommonDetailIn voi) {
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(voi.getWorkID(), voi.getSubID(), voi.getWorkType());

        TaskCommonDetailOut out = new TaskCommonDetailOut();
        // taskStatement
        TaskCommonStatement taskStatement = loopWorkMapper.fetchCommonTaskStatement(voi.getWorkType(), voi.getWorkID(), voi.getSubID());
        if (taskStatement != null) {
            Optional.ofNullable(taskStatement.getImgUrls()).ifPresent((String imgUrls) -> {
                taskStatement.setTaskImgArr(Arrays.asList(imgUrls.split(",")).stream().map(url -> {
                    ImageUrl imgurl = new ImageUrl();
                    imgurl.setImgUrl(url);
                    return imgurl;
                }).collect(Collectors.toList()));
            });
        }

//        List<TaskCommonItemVo> list = loopWorkMapper.getTaskCommonItem(voi.getWorkID(), voi.getSubID());
//        List<TaskCommonItem> itemList = new ArrayList<TaskCommonItem>();
//        if (list != null && list.size() > 0) {
//            for (TaskCommonItemVo vo : list) {
//                TaskCommonItem item = new TaskCommonItem();
//                item.setTaskItemID(Long.toString(vo.getTaskItemID()));
//                item.setTaskItemTitle(vo.getTaskItemTitle());
//                item.setFeedbackText(vo.getFeedbackText());
//                item.setFeedbackImg(vo.getFeedbackImg());
//                item.setDigitalItemName(vo.getDigitalItemName());
//                item.setDigitalItemType(vo.getDigitalItemType());
//                item.setTaskItemImgArr(convertToList(vo.getItemImgUrls()));
//                // TaskSubmit
//                TaskCommonItem.TaskSubmit submit = new TaskCommonItem().new TaskSubmit();
//                submit.setTaskSubID(vo.getTaskSubID());
//                submit.setTaskSubHeadUrl(vo.getTaskSubHeadUrl());
//                submit.setTaskSubTime(vo.getTaskSubTime());
//                submit.setWorkText(vo.getWorkText());
//                submit.setDigitalItemValue(vo.getDigitalItemValue());
//                submit.setExecuteImgArr(convertToList(vo.getImageUrls()));
//                item.setTaskSubmit(submit);
//                // TaskReview
//                TaskCommonItem.TaskReview review = new TaskCommonItem().new TaskReview();
//                review.setReviewResult(vo.getReviewResult());
//                review.setReviewID(vo.getReviewID());
//                review.setReviewOpinion(vo.getReviewOpinion());
//                review.setReviewTime(vo.getReviewTime());
//                review.setAuditorID(vo.getAuditorID());
//                review.setAuditorName(vo.getAuditorName());
//                review.setAuditorHeadUrl(vo.getAuditorHeadUrl());
//                review.setReviewImgArr(convertToList(vo.getImgUrls()));
//                item.setTaskReview(review);
//                itemList.add(item);
//            }
//        }
//
//        WorkAudit workAudit = workAuditMapper.getWorkAudit(voi.getWorkID(), voi.getSubID());
//        Review review = new Review();
//        if (workAudit != null) {
//            review.setAuditorID(workAudit.getUserId());
//            review.setAuditorName(workAudit.getUserName());
//            review.setAuditorHeadUrl(workAudit.getUserHeadUrl());
//            review.setReviewID(workAudit.getId());
//            review.setReviewResult(workAudit.getAuditResult());
//            review.setReviewOpinion(workAudit.getContent());
//            review.setReviewTime(workAudit.getCreateTime().getTime());
//            review.setReviewImgArr(convertToList(workAudit.getImgUrls()));
//        }
//
//        List<WorkAnswer> workAnswerList = workAnswerMapper.fetchAssignWorkAnswers(voi.getWorkID(), voi.getSubID());
//        List<Answer> answerList = new ArrayList<Answer>();
//        if (workAnswerList != null && workAnswerList.size() > 0) {
//            for (WorkAnswer workAnswer : workAnswerList) {
//                Answer answer = new Answer();
//                answer.setReplyID(Integer.parseInt(String.valueOf(workAnswer.getId())));
//                answer.setReplyContent(workAnswer.getContent());
//                answer.setReplyTime(workAnswer.getCreateTime().getTime());
//                answer.setAnswerID(Integer.parseInt(String.valueOf(workAnswer.getUserId())));
//                answer.setAnswerName(workAnswer.getUserName());
//                answer.setAnswerHeadUrl(workAnswer.getUserheadurl());
//                answer.setReplyAction(workAnswer.getAnsweropt());
//                answer.setReplyImgArr(convertToList(workAnswer.getImgurls()));
//                answerList.add(answer);
//            }
//        }
//
//        out.setTaskStatement(taskStatement);
//        out.setTaskItemArr(itemList);
//        out.setAllTaskReview(review);
//        out.setTaskReplyArr(answerList);
        return out;
    }

    @Override
    public void submitTask(TaskCommonSubmitIn taskCommonSubmitIn, SysUser user) {
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(taskCommonSubmitIn.getWorkID(), taskCommonSubmitIn.getSubID(), taskCommonSubmitIn.getWorkType());
        TTask task = taskMapper.selectByPrimaryKey(loopWork.getTaskId());
        List<TOperateOption> options = operateOptionMapper.fetchOptionsByTaskId(loopWork.getTaskId());
        HashMap<Long, TOperateOption> optMaps = new HashMap<>();
        for (TOperateOption opt : options) {
            optMaps.put(opt.getId(), opt);
        }
        loopWork.setExcuteUserId(user.getId());
        loopWork.setExcuteUserName(user.getUsername());
        loopWork.setModifyTime(DateUtil.currentDate());
//        task.getAuditDeadline()
        loopWork.setWorkStatus(2);

        loopWorkMapper.updateWorkStatus(loopWork);

        List<TOperateDataForWork> odfws1 = new ArrayList<>();
        List<TOperateDataForWork> odfws2 = new ArrayList<>();

        List<TaskCommonSubmitIn.TaskItem> taskItemArr = taskCommonSubmitIn.getTaskItemArr();
        for (TaskCommonSubmitIn.TaskItem taskItem : taskItemArr) {
            List<TaskCommonSubmitIn.TaskItem.ImgUrl> executeImgArr = taskItem.getExecuteImgArr();
            List<String> imgids = new ArrayList<>();
            executeImgArr.stream().forEach(imgUrl -> {
                String nurl = WaterMarkUtils.genPicUrlWithWaterMark(imgUrl.getImgUrl(), imgUrl.getLocation(), DateUtil.dateToString(new Date(imgUrl.getDate())));
                Date dt = new Date(imgUrl.getDate());
                String loc = imgUrl.getLocation();
                TFile img = new TFile();
                img.setImgUrl(nurl);
                img.setLocation(loc);
                img.setFileDate(dt);
                img.setCreateUserId(user.getId());
                img.setModifyUserId(user.getId());
                img.setCreateTime(DateUtil.currentDate());
                img.setModifyTime(DateUtil.currentDate());
                fileMapper.insert(img);
                imgids.add(Long.toString(img.getId()));
            });

            TOperateDataForWork odfw = operateDataForWorkMapper.fetchOneByTaskItemIdAndLoopWorkId(loopWork.getId(), taskItem.getTaskItemID());
            if(odfw == null){
                odfw = new TOperateDataForWork();
                odfws1.add(odfw);
            } else {
                odfws2.add(odfw);
            }

            TOperateOption option = optMaps.get(taskItem.getTaskItemID());

            odfw.setTaskItemId(taskItem.getTaskItemID());
            odfw.setLoopWorkId(loopWork.getId());
            odfw.setWorkTxt(taskItem.getWorkText());
            odfw.setImgIds(StringUtils.join(imgids, ","));
            odfw.setNumOptionType(option.getFeedbackNumType());
            odfw.setNumOptionName(option.getFeedbackNumName());
            odfw.setNumOptionValue(String.valueOf(taskItem.getDigitalItemValue()));
            odfw.setCreateTime(DateUtil.currentDate());
            odfw.setModifyTime(DateUtil.currentDate());
        }

        operateDataForWorkMapper.insertList(odfws1);
        for(TOperateDataForWork odfw : odfws2) {
            operateDataForWorkMapper.updateByPrimaryKeySelective(odfw);
        }

    }

    @Override
    public void auditTask(TaskCommonAuditIn taskCommonAuditIn, SysUser user) {
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(taskCommonAuditIn.getWorkID(), taskCommonAuditIn.getSubID(), taskCommonAuditIn.getWorkType());

        List<TOperateOption> options = operateOptionMapper.fetchOptionsByTaskId(loopWork.getTaskId());
        HashMap<Long, TOperateOption> optMaps = new HashMap<>();
        for (TOperateOption opt : options) {
            optMaps.put(opt.getId(), opt);
        }
        loopWork.setAuditUserId(user.getId());
        loopWork.setAuditUserName(user.getUsername());
        loopWorkMapper.updateByPrimaryKeySelective(loopWork);

        List<TaskCommonAuditIn.AuditTaskItem> taskItemArr = taskCommonAuditIn.getReviewArr();
        for (TaskCommonAuditIn.AuditTaskItem taskItem : taskItemArr) {
            // 任务审核
            TOperateDataForWork odfw  = operateDataForWorkMapper.fetchOneByTaskItemIdAndLoopWorkId(loopWork.getId(), taskItem.getTaskItemID());
            odfw.setTaskItemId(taskItem.getTaskItemID());
            odfw.setLoopWorkId(loopWork.getId());
            odfw.setAuditResult(taskItem.getReviewResult());
            odfw.setAuditContent(taskItem.getReviewOpinion());
            odfw.setAuditUserId(user.getId());
            List<ImageUrl> imgarr = taskItem.getReviewImgArr();
            List<String> imgids = new ArrayList<>();
            if(imgarr != null) {
                imgarr.forEach(imga -> {
                    TFile img = new TFile();
                    img.setImgUrl(imga.getImgUrl());
                    fileMapper.insert(img);
                    imgids.add(String.valueOf(img.getId()));
                });
            }
            odfw.setAuditImgIds(StringUtils.join(imgids, ","));
            operateDataForWorkMapper.updateByPrimaryKeySelective(odfw);
        }
    }

    @Override
    public void editExcImgs(ExecuteImgIn vo, SysUser user) {
        TLoopWork loopWork = loopWorkMapper.fetchOneTaskByWorkIds(vo.getWorkID(), vo.getSubID());
        TOperateDataForWork opData = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        if (StringUtils.isBlank(opData.getImgIds()))
            return;

        fileMapper.deleteByIds(opData.getImgIds());
//        List<Long> oldids = Arrays.asList(opData.getImgIds().split(",")).stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
//        for(Long id : oldids) {
//            TFile file = fileMapper.selectByPrimaryKey(id);
//            String location = file.getLocation();
//            Date fileDate = file.getFileDate();
//        }

        List<ExecuteImgIn.ExecuteImg> inimgs = vo.getExecuteImgArr();
        List<TFile> newImgs = new ArrayList<>();
        for (ExecuteImgIn.ExecuteImg inimg : inimgs) {
            TFile nimg = new TFile();
            nimg.setImgUrl(inimg.getImgUrl());
            nimg.setModifyUserId(user.getId());
            nimg.setCreateTime(DateUtil.currentDate());
            nimg.setModifyTime(DateUtil.currentDate());
            nimg.setCreateUserId(user.getId());
            nimg.setIsDel(false);

            newImgs.add(nimg);
        }
        fileMapper.insertList(newImgs);
        String newIds = StringUtils.join(newImgs.stream().map(f -> f.getId().toString()).collect(Collectors.toList()), ",");
        opData.setImgIds(newIds);
        operateDataForWorkMapper.updateByPrimaryKey(opData);
    }
}
