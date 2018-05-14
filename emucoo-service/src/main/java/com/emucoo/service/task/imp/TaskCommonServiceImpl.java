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

    @Autowired
    private TLabelMapper labelMapper;

    @Autowired
    private TTaskLabelMapper taskLabelMapper;

    @Autowired
    private TTaskPersonMapper taskPersonMapper;

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

        // TODO:

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

    @Override
    public int countCommonTaskByName(String keyword) {
        return taskMapper.countCommonTaskByName(keyword);
    }

    @Override
    public List<TaskParameterVo> listCommonTaskByName(String keyword, int pageNm, int pageSz) {
        List<TTask> tasks = taskMapper.listCommonTaskByName(keyword, (pageNm-1) * pageSz, pageSz);
        List<TaskParameterVo> taskList = new ArrayList<>();
        for(TTask task : tasks) {
            TaskParameterVo vo = new TaskParameterVo();
            vo.setId(task.getId());
            vo.setName(task.getName());
            vo.setDescription(task.getDescription());
            vo.setUse(task.getIsUse());
            vo.setLabels(new ArrayList<TaskParameterVo.IdNamePair>());
            List<TLabel> labels = labelMapper.listLabelByTaskId(task.getId());
            for(TLabel label : labels){
                TaskParameterVo.IdNamePair lbl = vo.new IdNamePair();
                lbl.setId(label.getId());
                lbl.setName(label.getName());
                vo.getLabels().add(lbl);
            }
        }
        return taskList;
    }

    @Override
    public void createCommonTask(TaskParameterVo data) {
        TTask task = new TTask();
        task.setName(data.getName());
        task.setDescription(data.getDescription());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyTime(DateUtil.currentDate());
        taskMapper.insert(task);
        List<TaskParameterVo.IdNamePair> lbls = data.getLabels();
        if(lbls != null){
            for(TaskParameterVo.IdNamePair lbl : lbls) {
                TTaskLabel tl = new TTaskLabel();
                tl.setLabelId(lbl.getId());
                tl.setTaskId(task.getId());
                taskLabelMapper.insert(tl);
            }
        }
    }

    @Override
    public boolean judgeExistCommonTask(TaskParameterVo taskParameterVo) {
        if(taskMapper.judgeExistCommonTask(taskParameterVo.getName()) > 0)
            return true;
        return false;
    }

    @Override
    public void saveCommonTask(TaskParameterVo data) {
        TTask task = new TTask();
        task.setId(data.getId());
        task.setDescription(data.getDescription());
        taskMapper.updateByPrimaryKeySelective(task);

        taskLabelMapper.dropByTaskId(task.getId());
        List<TaskParameterVo.IdNamePair> lbls = data.getLabels();
        for(TaskParameterVo.IdNamePair lbl : lbls) {
            TTaskLabel tl = new TTaskLabel();
            tl.setTaskId(task.getId());
            tl.setLabelId(lbl.getId());
            taskLabelMapper.insert(tl);
        }
    }

    @Override
    public void removeCommonTask(List<Long> ids) {
        taskMapper.removeCommonTaskByIds(ids);
    }

    @Override
    public void switchCommonTask(List<Long> data, boolean b) {
        taskMapper.switchCommonTaskByIds(data, b);
    }

    @Override
    public TaskParameterVo detailCommonTask(Long taskId) {
        TaskParameterVo vo = new TaskParameterVo();
        TTask task = taskMapper.selectByPrimaryKey(taskId);
        vo.setId(task.getId());
        vo.setName(task.getName());
        vo.setUse(task.getIsUse());
        vo.setDescription(task.getDescription());
        if(task.getIllustrationImgIds() != null) {
            List<TFile> imgs = fileMapper.fetchFilesByIds(Arrays.asList(task.getIllustrationImgIds().split(",")));
            if(imgs != null && imgs.size() > 0) {
                List<ImageUrl> imgurls = new ArrayList<>();
                imgs.forEach(img -> {
                    ImageUrl imageUrl = new ImageUrl();
                    imageUrl.setImgUrl(img.getImgUrl());
                    imgurls.add(imageUrl);
                });
                vo.setImgUrls(imgurls);
            }
        }
        String[] exeTm = task.getExecuteDeadline().split(":");
        vo.setExeCloseHour(Integer.parseInt(exeTm[0]));
        vo.setExeCloseMinute(Integer.parseInt(exeTm[1]));
        String[] remTm = task.getExecuteRemindTime().split(":");
        vo.setAuditCloseHour(Integer.parseInt(remTm[0]));
        vo.setAuditCloseMinute(Integer.parseInt(remTm[1]));
        String[] audTm = task.getAuditDeadline().split(":");
        vo.setExeRemindHour(Integer.parseInt(audTm[0]));
        vo.setExeRemindMinute(Integer.parseInt(audTm[1]));

        vo.setExeUserType(task.getExecuteUserType());
        List<TTaskPerson> taskPersons = taskPersonMapper.fetchByTaskId(task.getId());
        List<TTaskPerson> exePersons = taskPersons.stream().filter(person -> person.getPersonType() == 1).collect(Collectors.toList());
        List<TTaskPerson> ccPersons = taskPersons.stream().filter(person -> person.getPersonType() == 2).collect(Collectors.toList());

        List<TaskParameterVo.DeptPosition> ccDpts = new ArrayList<>();
        for (TTaskPerson taskPerson : ccPersons) {
            TaskParameterVo.DeptPosition dept = null;
            for(TaskParameterVo.DeptPosition ccDpt : ccDpts) {
                dept = ccDpt.getDept().getId() == taskPerson.getDptId() ? ccDpt : null;
            }
            if(dept == null){
                dept = vo.new DeptPosition();
                dept.setPositions(new ArrayList<>());

                TaskParameterVo.IdNamePair dept0 = vo.new IdNamePair();
                dept0.setId(taskPerson.getDptId());
                dept0.setName(taskPerson.getDptName());
                dept.setDept(dept0);

                TaskParameterVo.IdNamePair dept1 = vo.new IdNamePair();
                dept1.setId(taskPerson.getDptId());
                dept1.setName(taskPerson.getDptName());
                dept.getPositions().add(dept1);

            } else {
                TaskParameterVo.IdNamePair dept1 = vo.new IdNamePair();
                dept1.setId(taskPerson.getDptId());
                dept1.setName(taskPerson.getDptName());
                dept.getPositions().add(dept1);
            }
        }
        vo.setCcUserPositions(ccDpts);

        if(vo.getExeUserType() == 1) {
            List<TaskParameterVo.DeptPosition> exeDpts = new ArrayList<>();
            for (TTaskPerson taskPerson : exePersons) {
                TaskParameterVo.DeptPosition dpt = null;
                for (TaskParameterVo.DeptPosition exeDpt : exeDpts) {
                    dpt = exeDpt.getDept().getId() == taskPerson.getDptId() ? exeDpt : null;
                }
                if (dpt == null) {
                    dpt = vo.new DeptPosition();
                    dpt.setPositions(new ArrayList<>());

                    TaskParameterVo.IdNamePair dpt0 = vo.new IdNamePair();
                    dpt0.setId(taskPerson.getDptId());
                    dpt0.setName(taskPerson.getDptName());
                    dpt.setDept(dpt0);

                    TaskParameterVo.IdNamePair dpt1 = vo.new IdNamePair();
                    dpt1.setId(taskPerson.getPositionId());
                    dpt1.setName(taskPerson.getPositionName());
                    dpt.getPositions().add(dpt1);

                } else {
                    TaskParameterVo.IdNamePair dpt1 = vo.new IdNamePair();
                    dpt1.setId(taskPerson.getPositionId());
                    dpt1.setName(taskPerson.getPositionName());
                    dpt.getPositions().add(dpt1);
                }
            }
            vo.setExeDeptPositions(exeDpts);

        } else {
            List<TaskParameterVo.IdNamePair> exeShps = new ArrayList<>();
            for (TTaskPerson taskPerson : exePersons) {
                TaskParameterVo.IdNamePair exeShp = vo.new IdNamePair();
                exeShp.setId(taskPerson.getShopId());
                exeShp.setName(taskPerson.getShopName());
            }
            vo.setExeUserShops(exeShps);
            vo.setAuditDeptId(task.getAuditDptId());
            vo.setAuditDeptName(task.getAuditDptName());
        }

        vo.setDurationType(task.getDurationTimeType());
        vo.setDurationBegin(DateUtil.dateToString1(task.getTaskStartDate()));
        vo.setDurationEnd(DateUtil.dateToString1(task.getTaskEndDate()));

        vo.setRepeatType(task.getLoopCycleType());
        if(task.getLoopCycleType() == 1) {
            vo.setIntervalDays(Integer.parseInt(task.getLoopCycleValue()));
        } else {
            vo.setDays(Arrays.asList(task.getLoopCycleValue().split(",")).stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList()));
        }

        vo.setScoreType(task.getScoreType().intValue());
        vo.setScoreValue(task.getPreinstallScore());
        vo.setScoreWeight(task.getPreinstallWeight());

        List<TOperateOption> options = operateOptionMapper.fetchOptionsByTaskId(task.getId());
        List<TaskParameterVo.TaskOption> taskOptions = new ArrayList<>();
        for(TOperateOption option : options){
            TaskParameterVo.TaskOption taskOption = vo.new TaskOption();
            taskOption.setId(option.getId());
            taskOption.setName(option.getName());
            taskOption.setNeedFeedbackText(option.getFeedbackNeedText());
            taskOption.setFeedbackTextName(option.getFeedbackTextName());
            taskOption.setFeedbackTextContent(option.getFeedbackTextDescription());
            taskOption.setNeedFeedbackImg(option.getFeedbackNeedNum());
            taskOption.setFeedbackNumName(option.getFeedbackNumName());
            taskOption.setFeedbackNumType(option.getFeedbackNumType());
            taskOption.setNeedFeedbackImg(option.getFeedbackImgType()==0?false:true);
            taskOption.setFeedbackImgType(option.getFeedbackImgFromAlbum()?1:0);
            taskOption.setFeedbackImgCount(option.getFeedbackImgMaxCount());
            taskOption.setFeedbackImgSampleId(option.getFeedbackImgExampleId());
            taskOption.setScore(option.getPreinstallScore());
            taskOption.setWeight(option.getPreinstallWeight());
            taskOptions.add(taskOption);
        }
        vo.setTaskOptions(taskOptions);

        return vo;
    }

    @Override
    public void configCommonTask(TaskParameterVo data) {

    }
}
