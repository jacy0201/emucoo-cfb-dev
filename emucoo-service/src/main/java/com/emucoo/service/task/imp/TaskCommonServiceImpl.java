package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.TaskCommonService;
import com.emucoo.utils.DateUtil;
import com.emucoo.utils.TaskUniqueIdUtils;
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

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TTaskCommentMapper commentMapper;

    @Autowired
    private SysUserRelationMapper userRelationMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private SysUserShopMapper userShopMapper;


    private List<ImageUrl> convertImgIds2Urls(String ids) {
        if (ids == null)
            return new ArrayList<>();
        return Arrays.asList(ids.split(",")).stream().filter(iid -> StringUtils.isNotBlank(iid)).map(iid -> {
            TFile img = fileMapper.selectByPrimaryKey(Long.parseLong(iid));
            if (img == null) {
                return null;
            } else {
                ImageUrl url = new ImageUrl();
                url.setImgUrl(img.getImgUrl());
                return url;
            }
        }).filter(url -> url != null).collect(Collectors.toList());
    }

    @Override
    public TaskCommonDetailOut detail(TaskCommonDetailIn voi) {
        TLoopWork loopWork = loopWorkMapper.fetchByWorkIdAndType(voi.getWorkID(), voi.getSubID(), voi.getWorkType());
        TaskCommonDetailOut out = new TaskCommonDetailOut();
        // taskStatement
        TaskCommonStatement taskStatement = loopWorkMapper.fetchCommonTaskStatement(loopWork.getId());
        if (taskStatement != null) {
            Optional.ofNullable(taskStatement.getImgUrls()).ifPresent((String imgUrls) -> {
                taskStatement.setTaskImgArr(Arrays.asList(imgUrls).stream().map(s -> {
                    ImageUrl imageUrl = new ImageUrl();
                    imageUrl.setImgUrl(s);
                    return imageUrl;
                }).collect(Collectors.toList()));
            });
        }


        List<TaskCommonItemVo> list = loopWorkMapper.fetchTaskCommonItem(loopWork.getId());
        List<TaskCommonItem> itemList = new ArrayList<TaskCommonItem>();
        if (list != null && list.size() > 0) {
            for (TaskCommonItemVo vo : list) {
                TaskCommonItem item = new TaskCommonItem();
                item.setTaskItemID(Long.toString(vo.getTaskItemID()));
                item.setTaskItemTitle(vo.getTaskItemTitle());
                item.setFeedbackText(vo.getFeedbackText());
                item.setFeedbackImg(vo.getFeedbackImg());
                item.setDigitalItemName(vo.getDigitalItemName());
                item.setDigitalItemType(vo.getDigitalItemType());
                item.setTaskItemImgArr(convertImgIds2Urls(vo.getItemImgUrls()));

                // TaskSubmit
                TaskCommonItem.TaskSubmit submit = new TaskCommonItem().new TaskSubmit();
                submit.setTaskSubID(vo.getTaskSubID());
                submit.setTaskSubHeadUrl(vo.getTaskSubHeadUrl());
                submit.setTaskSubTime(vo.getTaskSubTime());
                submit.setWorkText(vo.getWorkText());
                submit.setDigitalItemValue(vo.getDigitalItemValue());
                submit.setExecuteImgArr(convertImgIds2Urls(vo.getImageUrls()));
                item.setTaskSubmit(submit);
                // TaskReview
                TaskCommonItem.TaskReview review = new TaskCommonItem().new TaskReview();
                review.setReviewResult(vo.getReviewResult());
                review.setReviewID(vo.getReviewID());
                review.setReviewOpinion(vo.getReviewOpinion());
                review.setReviewTime(vo.getReviewTime());
                review.setAuditorID(vo.getAuditorID());
                review.setAuditorName(vo.getAuditorName());
                review.setAuditorHeadUrl(vo.getAuditorHeadUrl());
                review.setReviewImgArr(convertImgIds2Urls(vo.getImgUrls()));
                item.setTaskReview(review);
                itemList.add(item);
            }
        }

//        WorkAudit workAudit = workAuditMapper.getWorkAudit(voi.getWorkID(), voi.getSubID());
        Review review = new Review();

        review.setReviewID(loopWork.getId());
        review.setReviewResult(loopWork.getWorkResult());
        review.setReviewTime(loopWork.getAuditTime() == null ? 0 : loopWork.getAuditTime().getTime());
        review.setAuditorID(loopWork.getAuditUserId() == null ? 0 : loopWork.getAuditUserId());
        if (loopWork.getAuditUserId() != null) {
            SysUser auditUser = userMapper.selectByPrimaryKey(loopWork.getAuditUserId());
            if (auditUser != null) {
                review.setAuditorName(auditUser.getRealName());
                review.setAuditorHeadUrl(auditUser.getHeadImgUrl());
            }
        }
//        review.setReviewOpinion(workAudit.getContent());
//        review.setReviewImgArr(convertToList(workAudit.getImgUrls()));


        List<TTaskComment> comments = commentMapper.fetchByLoopWorkId(loopWork.getId());
        List<Answer> answerList = new ArrayList<Answer>();
        if (comments != null && comments.size() > 0) {
            for (TTaskComment comment : comments) {
                Answer answer = new Answer();
                answer.setReplyID(comment.getId().intValue());
                answer.setReplyContent(comment.getContent());
                answer.setReplyTime(comment.getCreateTime().getTime());
                answer.setAnswerID(comment.getCreateUserId().intValue());
                if (comment.getCreateUserId() != null) {
                    SysUser commentUser = userMapper.selectByPrimaryKey(comment.getCreateUserId());
                    if (commentUser != null)
                        answer.setAnswerName(commentUser.getRealName());
                    answer.setAnswerHeadUrl(commentUser.getHeadImgUrl());
                }
                answer.setReplyAction(1);
                answer.setReplyImgArr(new ArrayList<>());
                answerList.add(answer);
            }
        }
//        List<WorkAnswer> workAnswerList = workAnswerMapper.fetchAssignWorkAnswers(voi.getWorkID(), voi.getSubID());
//        if (workAnswerList != null && workAnswerList.size() > 0) {
//            for (WorkAnswer workAnswer : workAnswerList) {
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

        out.setTaskStatement(taskStatement);
        out.setTaskItemArr(itemList);
        out.setAllTaskReview(review);
        out.setTaskReplyArr(answerList);
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
        if (StringUtils.isNotBlank(task.getAuditDeadline())) {
            String[] tms = task.getAuditDeadline().split(":");
            if(tms.length  == 1){
                int mi = Integer.parseInt(tms[0]);
                loopWork.setAuditTime(DateUtil.timeForward(loopWork.getModifyTime(), 0, mi));
            }
            if(tms.length == 2) {
                int hr = Integer.parseInt(tms[0]);
                int mi = Integer.parseInt(tms[1]);
                loopWork.setAuditTime(DateUtil.timeForward(loopWork.getModifyTime(), hr, mi));
            }
        }
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
            if (odfw == null) {
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
        for (TOperateDataForWork odfw : odfws2) {
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
            TOperateDataForWork odfw = operateDataForWorkMapper.fetchOneByTaskItemIdAndLoopWorkId(loopWork.getId(), taskItem.getTaskItemID());
            odfw.setTaskItemId(taskItem.getTaskItemID());
            odfw.setLoopWorkId(loopWork.getId());
            odfw.setAuditResult(taskItem.getReviewResult());
            odfw.setAuditContent(taskItem.getReviewOpinion());
            odfw.setAuditUserId(user.getId());
            List<ImageUrl> imgarr = taskItem.getReviewImgArr();
            List<String> imgids = new ArrayList<>();
            if (imgarr != null) {
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
        List<TTask> tasks = taskMapper.listCommonTaskByName(keyword, (pageNm - 1) * pageSz, pageSz);
        List<TaskParameterVo> taskList = new ArrayList<>();
        for (TTask task : tasks) {
            TaskParameterVo vo = new TaskParameterVo();
            vo.setId(task.getId());
            vo.setName(task.getName());
            vo.setDescription(task.getDescription());
            vo.setIsUse(task.getIsUse());
            vo.setLabels(new ArrayList<TaskParameterVo.IdNamePair>());
            List<TLabel> labels = labelMapper.listLabelByTaskId(task.getId());
            for (TLabel label : labels) {
                TaskParameterVo.IdNamePair lbl = new TaskParameterVo.IdNamePair();
                lbl.setId(label.getId());
                lbl.setName(label.getName());
                vo.getLabels().add(lbl);
            }
            taskList.add(vo);
        }
        return taskList;
    }

    @Override
    public void createCommonTask(TaskParameterVo data) {
        TTask task = new TTask();
        task.setName(data.getName());
        task.setType(1);
        task.setDescription(data.getDescription());
        task.setCreateTime(DateUtil.currentDate());
        task.setModifyTime(DateUtil.currentDate());
        taskMapper.insert(task);
        List<TaskParameterVo.IdNamePair> lbls = data.getLabels();
        if (lbls != null) {
            for (TaskParameterVo.IdNamePair lbl : lbls) {
                TTaskLabel tl = new TTaskLabel();
                tl.setLabelId(lbl.getId());
                tl.setTaskId(task.getId());
                taskLabelMapper.insert(tl);
            }
        }
    }

    @Override
    public boolean judgeExistCommonTask(TaskParameterVo taskParameterVo) {
        if (taskMapper.judgeExistCommonTask(taskParameterVo.getName()) > 0)
            return true;
        return false;
    }

    @Override
    public void saveCommonTask(TaskParameterVo data) {
        TTask task = new TTask();
        task.setId(data.getId());
        task.setName(data.getName());
        task.setDescription(data.getDescription());
        taskMapper.updateByPrimaryKeySelective(task);

        List<TaskParameterVo.IdNamePair> lbls = data.getLabels();
        if (lbls != null) {
            taskLabelMapper.dropByTaskId(task.getId());
            for (TaskParameterVo.IdNamePair lbl : lbls) {
                TTaskLabel tl = new TTaskLabel();
                tl.setTaskId(task.getId());
                tl.setLabelId(lbl.getId());
                tl.setCreateTime(DateUtil.currentDate());
                tl.setModifyTime(DateUtil.currentDate());
                taskLabelMapper.insert(tl);
            }
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
        vo.setIsUse(task.getIsUse());
        vo.setDescription(task.getDescription());
        if (task.getIllustrationImgIds() != null) {
            List<TFile> imgs = fileMapper.fetchFilesByIds(Arrays.asList(task.getIllustrationImgIds().split(",")));
            if (imgs != null && imgs.size() > 0) {
                List<ImageUrl> imgurls = new ArrayList<>();
                imgs.forEach(img -> {
                    ImageUrl imageUrl = new ImageUrl();
                    imageUrl.setImgUrl(img.getImgUrl());
                    imgurls.add(imageUrl);
                });
                vo.setImgUrls(imgurls);
            }
        }
        if (StringUtils.isNotBlank(task.getExecuteDeadline())) {
            String[] exeTm = task.getExecuteDeadline().split(":");
            vo.setExeCloseHour(Integer.parseInt(exeTm[0] == null ? "0" : exeTm[0]));
            if (exeTm.length == 2)
                vo.setExeCloseMinute(Integer.parseInt(exeTm[1] == null ? "0" : exeTm[1]));
        }
        if (StringUtils.isNotBlank(task.getExecuteRemindTime())) {
            String[] remTm = task.getExecuteRemindTime().split(":");
            vo.setAuditCloseHour(Integer.parseInt(remTm[0] == null ? "0" : remTm[0]));
            if (remTm.length == 2)
                vo.setAuditCloseMinute(Integer.parseInt(remTm[1] == null ? "0" : remTm[1]));
        }
        if (StringUtils.isNotBlank(task.getAuditDeadline())) {
            String[] audTm = task.getAuditDeadline().split(":");
            vo.setExeRemindHour(Integer.parseInt(audTm[0] == null ? "0" : audTm[0]));
            if (audTm.length == 2)
                vo.setExeRemindMinute(Integer.parseInt(audTm[1] == null ? "0" : audTm[1]));
        }

        vo.setExeUserType(task.getExecuteUserType());
        List<TTaskPerson> taskPersons = taskPersonMapper.fetchByTaskId(task.getId());
        List<TTaskPerson> exePersons = taskPersons.stream().filter(person -> person.getPersonType() == 1).collect(Collectors.toList());
        List<TTaskPerson> ccPersons = taskPersons.stream().filter(person -> person.getPersonType() == 2).collect(Collectors.toList());

        List<TaskParameterVo.DeptPosition> ccDpts = new ArrayList<>();
        for (TTaskPerson taskPerson : ccPersons) {
            TaskParameterVo.DeptPosition dept = null;
            for (TaskParameterVo.DeptPosition ccDpt : ccDpts) {
                if (ccDpt.getDept().getId() == taskPerson.getDptId()) {
                    dept = ccDpt;
                    break;
                }
            }
            if (dept == null) {
                dept = new TaskParameterVo.DeptPosition();
                ccDpts.add(dept);
                dept.setPositions(new ArrayList<>());

                TaskParameterVo.IdNamePair dept0 = new TaskParameterVo.IdNamePair();
                dept0.setId(taskPerson.getDptId());
                dept0.setName(taskPerson.getDptName());
                dept.setDept(dept0);

                TaskParameterVo.IdNamePair dept1 = new TaskParameterVo.IdNamePair();
                dept1.setId(taskPerson.getPositionId());
                dept1.setName(taskPerson.getPositionName());
                dept.getPositions().add(dept1);

            } else {
                TaskParameterVo.IdNamePair dept1 = new TaskParameterVo.IdNamePair();
                dept1.setId(taskPerson.getPositionId());
                dept1.setName(taskPerson.getPositionName());
                dept.getPositions().add(dept1);
            }
        }
        vo.setCcUserPositions(ccDpts);
        if (vo.getExeUserType() != null) {
            if (vo.getExeUserType() == 1) { // 1: 按部门
                List<TaskParameterVo.DeptPosition> exeDpts = new ArrayList<>();
                for (TTaskPerson taskPerson : exePersons) {
                    // 这段代码是把list里的，按照dept id分组了。
                    TaskParameterVo.DeptPosition dpt = null;
                    for (TaskParameterVo.DeptPosition exeDpt : exeDpts) {
                        if (exeDpt.getDept().getId() == taskPerson.getDptId()) {
                            dpt = exeDpt;
                            break;
                        }
                    }
                    if (dpt == null) {
                        dpt = new TaskParameterVo.DeptPosition();
                        exeDpts.add(dpt);
                        dpt.setPositions(new ArrayList<>());

                        TaskParameterVo.IdNamePair dpt0 = new TaskParameterVo.IdNamePair();
                        dpt0.setId(taskPerson.getDptId());
                        dpt0.setName(taskPerson.getDptName());
                        dpt.setDept(dpt0);

                        TaskParameterVo.IdNamePair pos1 = new TaskParameterVo.IdNamePair();
                        pos1.setId(taskPerson.getPositionId());
                        pos1.setName(taskPerson.getPositionName());
                        dpt.getPositions().add(pos1);

                    } else {
                        TaskParameterVo.IdNamePair dpt1 = new TaskParameterVo.IdNamePair();
                        dpt1.setId(taskPerson.getPositionId());
                        dpt1.setName(taskPerson.getPositionName());
                        dpt.getPositions().add(dpt1);
                    }
                }
                vo.setExeDeptPositions(exeDpts);

            } else { // 2: 按店铺
                List<TaskParameterVo.IdNamePair> exeShps = new ArrayList<>();
                for (TTaskPerson taskPerson : exePersons) {
                    TaskParameterVo.IdNamePair exeShp = new TaskParameterVo.IdNamePair();
                    exeShp.setId(taskPerson.getShopId());
                    exeShp.setName(taskPerson.getShopName());
                    exeShps.add(exeShp);
                }
                vo.setExeUserShops(exeShps);
                vo.setAuditDeptId(task.getAuditDptId());
                vo.setAuditDeptName(task.getAuditDptName());
            }
        }

        vo.setDurationType(task.getDurationTimeType());
        vo.setDurationBegin(DateUtil.dateToString1(task.getTaskStartDate()));
        vo.setDurationEnd(DateUtil.dateToString1(task.getTaskEndDate()));

        vo.setRepeatType(task.getLoopCycleType());
        if (StringUtils.isNotBlank(task.getLoopCycleValue())) {
            vo.setDays(Arrays.asList(task.getLoopCycleValue().split(",")).stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList()));
        }

        if (task.getScoreType() != null) {
            vo.setScoreType(task.getScoreType().intValue());
            vo.setScoreValue(task.getPreinstallScore());
            vo.setScoreWeight(task.getPreinstallWeight());
        }

        List<TOperateOption> options = operateOptionMapper.fetchOptionsByTaskId(task.getId());
        List<TaskParameterVo.TaskOption> taskOptions = new ArrayList<>();
        if (options != null) {
            for (TOperateOption option : options) {
                TaskParameterVo.TaskOption taskOption = new TaskParameterVo.TaskOption();
                taskOption.setId(option.getId());
                taskOption.setName(option.getName());
                taskOption.setNeedFeedbackText(option.getFeedbackNeedText());
                taskOption.setFeedbackTextName(option.getFeedbackTextName());
                taskOption.setFeedbackTextDescription(option.getFeedbackTextDescription());
                taskOption.setNeedFeedbackImg(option.getFeedbackNeedNum());
                taskOption.setFeedbackNumName(option.getFeedbackNumName());
                taskOption.setFeedbackNumType(option.getFeedbackNumType());
                taskOption.setNeedFeedbackImg(option.getFeedbackImgType() == 0 ? false : true);
                taskOption.setFeedbackImgType(option.getFeedbackImgFromAlbum() ? 1 : 0);
                taskOption.setFeedbackImgCount(option.getFeedbackImgMaxCount());
                if (option.getFeedbackImgExampleId() != null) {
                    TFile imgsample = fileMapper.selectByPrimaryKey(option.getFeedbackImgExampleId());
                    taskOption.setFeedbackImgSampleUrl(imgsample == null ? "" : imgsample.getImgUrl());
                }
                taskOption.setScore(option.getPreinstallScore());
                taskOption.setWeight(option.getPreinstallWeight());
                taskOptions.add(taskOption);
            }
        }
        vo.setTaskOptions(taskOptions);

        return vo;
    }

    private void cleanOldConfigForCommonTask(TTask task) {
        if (task.getIllustrationImgIds() != null) {
            List<String> ids = Arrays.asList(task.getIllustrationImgIds().split(","));
            if (ids.size() > 0)
                fileMapper.dropByIds(ids);
        }
        operateOptionMapper.dropSampleImagesByTaskId(task.getId());
        operateOptionMapper.dropByTaskId(task.getId());
        taskPersonMapper.dropByTaskId(task.getId());

    }

    @Override
    public void configCommonTask(TaskParameterVo data) {
        TTask task = taskMapper.selectByPrimaryKey(data.getId());
        if (task == null) {
            task = new TTask();
            task.setId(data.getId());
            task.setCreateTime(DateUtil.currentDate());
            task.setModifyTime(DateUtil.currentDate());
        } else {
            cleanOldConfigForCommonTask(task);
        }
        // task基本信息
        task.setName(data.getName());
        task.setDescription(data.getDescription());
        if (data.getImgUrls() != null) {
            Iterator<ImageUrl> it = data.getImgUrls().iterator();
            List<String> imgids = new ArrayList<>();
            while (it.hasNext()) {
                TFile imgfile = new TFile();
                imgfile.setImgUrl(it.next().getImgUrl());
                fileMapper.insert(imgfile);
                imgids.add(Long.toString(imgfile.getId()));
            }
            task.setIllustrationImgIds(StringUtils.join(imgids, ","));
        }

        data.setExeCloseHour(data.getExeCloseHour() == null ? 0 : data.getExeCloseHour());
        data.setExeCloseMinute(data.getExeCloseMinute() == null ? 0 : data.getExeCloseMinute());
        data.setExeRemindHour(data.getExeRemindHour() == null ? 0 : data.getExeRemindHour());
        data.setExeRemindMinute(data.getExeRemindMinute() == null ? 0 : data.getExeRemindMinute());
        data.setAuditCloseHour(data.getAuditCloseHour() == null ? 0 : data.getAuditCloseHour());
        data.setAuditCloseMinute(data.getAuditCloseMinute() == null ? 0 : data.getAuditCloseMinute());
        // 截止时间
        task.setExecuteDeadline(String.format("%d:%d", data.getExeCloseHour(), data.getExeCloseMinute()));
        task.setExecuteRemindTime(String.format("%d:%d", data.getExeRemindHour(), data.getExeRemindMinute()));
        task.setAuditDeadline(String.format("%d:%d", data.getAuditCloseHour(), data.getAuditCloseMinute()));

        // 执行人信息
        task.setExecuteUserType(data.getExeUserType());
        if (data.getExeUserType() == 1) { // 1: 按部门
            data.getExeDeptPositions().forEach(deptPosition -> {
                deptPosition.getPositions().forEach(position -> {
                    TTaskPerson taskPerson = new TTaskPerson();
                    taskPerson.setTaskId(data.getId());
                    taskPerson.setDptId(deptPosition.getDept().getId());
                    taskPerson.setDptName(deptPosition.getDept().getName());
                    taskPerson.setPositionId(position.getId());
                    taskPerson.setPositionName(position.getName());
                    taskPerson.setPersonType(1); // 1: 执行人 2：抄送人
                    taskPersonMapper.insert(taskPerson);
                });
            });
        } else { // 2:按店铺
            data.getExeUserShops().forEach(shop -> {
                TTaskPerson taskPerson = new TTaskPerson();
                taskPerson.setTaskId(data.getId());
                taskPerson.setPersonType(1);
                taskPerson.setShopId(shop.getId());
                taskPerson.setShopName(shop.getName());
                taskPersonMapper.insert(taskPerson);
            });
            task.setAuditDptId(data.getAuditDeptId());
            task.setAuditDptName(data.getAuditDeptName());
        }

        data.getCcUserPositions().forEach(ccDept -> {
            ccDept.getPositions().forEach(ccPos -> {
                TTaskPerson taskPerson = new TTaskPerson();
                taskPerson.setTaskId(data.getId());
                taskPerson.setDptId(ccDept.getDept().getId());
                taskPerson.setDptName(ccDept.getDept().getName());
                taskPerson.setPositionId(ccPos.getId());
                taskPerson.setPositionName(ccPos.getName());
                taskPerson.setPersonType(2);
                taskPersonMapper.insert(taskPerson);
            });
        });

        // 持续周期
        task.setDurationTimeType(data.getDurationType());
        task.setTaskStartDate(DateUtil.strToYYMMDDDate(StringUtils.isBlank(data.getDurationBegin()) ? DateUtil.simple(DateUtil.currentDate()) : data.getDurationBegin()));
        task.setTaskEndDate(DateUtil.strToYYMMDDDate(StringUtils.isBlank(data.getDurationEnd()) ? DateUtil.simple(DateUtil.currentDate()) : data.getDurationEnd()));

        // 循环周期 1: 每隔多少天一次,  2，3：每周（1-7），每月（1-31)
        task.setLoopCycleType(data.getRepeatType());
        task.setLoopCycleValue(StringUtils.join(data.getDays(), ","));

        // 评分方式
        task.setScoreType(data.getScoreType().byteValue());
        task.setPreinstallScore(data.getScoreValue());
        task.setPreinstallWeight(data.getScoreWeight());

        if (data.getTaskOptions() != null) {
            data.getTaskOptions().forEach(taskOption -> {
                TOperateOption option = new TOperateOption();
                option.setCreateTime(DateUtil.currentDate());
                option.setModifyTime(DateUtil.currentDate());
                option.setTaskId(data.getId());
                option.setName(taskOption.getName());
                option.setPreinstallScore(taskOption.getScore());
                option.setPreinstallWeight(taskOption.getWeight());
                option.setFeedbackNeedText(taskOption.getNeedFeedbackText());
                option.setFeedbackTextName(taskOption.getFeedbackTextName());
                option.setFeedbackTextDescription(taskOption.getFeedbackTextDescription());
                option.setFeedbackNeedNum(taskOption.getNeedFeedbackNum());
                option.setFeedbackNumName(taskOption.getFeedbackNumName());
                option.setFeedbackNumType(taskOption.getFeedbackNumType());
                option.setFeedbackImgType(taskOption.getNeedFeedbackImg() ? 1 : 0);
                option.setFeedbackImgFromAlbum(taskOption.getFeedbackImgType() == 1 ? true : false);
                option.setFeedbackImgName(taskOption.getFeedbackImgName());
                option.setFeedbackImgMaxCount(taskOption.getFeedbackImgCount());
                if (StringUtils.isNotBlank(taskOption.getFeedbackImgSampleUrl())) {
                    TFile img = new TFile();
                    img.setImgUrl(taskOption.getFeedbackImgSampleUrl());
                    fileMapper.insert(img);
                    option.setFeedbackImgExampleId(img.getId());
                }
                operateOptionMapper.insert(option);
            });
        }
        taskMapper.updateByPrimaryKeySelective(task);

    }


    /**
     * 根据定时任务的定义，为定时任务创建每天要执行的实例，规则是：
     * 1：根据执行人的定义，每个执行人创建一个任务实例
     * 2：根据执行时间和执行周期定义，每次执行创建一个执行实例
     * 3：根据执行截止时间定义，计算出提醒截止时间，审核截止时间
     */
    @Override
    public void buildCommonTaskInstance() {
        // list all common task: filter by the duration time
        Date tomorrow = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(DateUtil.dateAddDay(DateUtil.currentDate(), 1)));
        List<TTask> commonTasks = taskMapper.filterAvailableCommonTask(tomorrow);
        for (TTask commonTask : commonTasks) {
            createCommonLoopWork(commonTask, tomorrow);
        }
    }

    private void createCommonLoopWork(TTask commonTask, Date tomorrow) {
        if (!tomorrowIsExecuteDate(commonTask, tomorrow))
            return;
        List<SysUser> executors = determinExecutors(commonTask);

        // 因为拆分的具体的loop work没有跨天执行的，所以可以直接计算出明天就是该任务实例的执行日期
        Date exeBeginDt = tomorrow;
        Date exeEndDt = tomorrow;

        Date exeDeadLine = exeEndDt;
        if (StringUtils.isNotBlank(commonTask.getExecuteDeadline())) {
            String[] exeStr = commonTask.getExecuteDeadline().split(":");
            if (exeStr.length == 1) {
                exeDeadLine = DateUtil.timeForward(exeBeginDt, 0, Integer.parseInt(exeStr[0].trim()));
            }
            if (exeStr.length == 2) {
                exeDeadLine = DateUtil.timeForward(exeBeginDt, Integer.parseInt(exeStr[0].trim()), Integer.parseInt(exeStr[1].trim()));
            }
        }

        Date exeRemindTime = DateUtil.timeBackward(exeDeadLine, 0, 30);
        if (commonTask.getExecuteRemindTime() != null) {
            String[] remindStr = commonTask.getExecuteRemindTime().split(":");
            if (remindStr.length == 1) {
                exeRemindTime = DateUtil.timeBackward(exeDeadLine, 0, Integer.parseInt(remindStr[0].trim()));
            }
            if (remindStr.length == 2) {
                exeRemindTime = DateUtil.timeBackward(exeDeadLine, Integer.parseInt(remindStr[0]), Integer.parseInt(remindStr[1]));
            }
        }

        List<TOperateOption> options = operateOptionMapper.fetchOptionsByTaskId(commonTask.getId());

        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
        for (SysUser executor : executors) {
            // 判断数据库里是否已经有这条数据了
            int count = loopWorkMapper.isLoopWorkExist(commonTask.getId(), tomorrow, executor.getId());
            if (count > 0)
                continue;

            TLoopWork loopWork = new TLoopWork();
//            loopWork.setAuditDeadline();
//            loopWork.setAuditTime();
            SysUser auditor = determinAuditorByExecutorType(executor, commonTask);
            if (auditor != null) {
                loopWork.setAuditUserId(auditor.getId());
                loopWork.setAuditUserName(auditor.getRealName());
            }
            loopWork.setCreateTime(DateUtil.currentDate());
//            loopWork.setCreateUserId();
//            loopWork.setCreateUserName();
            loopWork.setExcuteUserId(executor.getId());
            loopWork.setExcuteUserName(executor.getRealName());
            loopWork.setExecuteBeginDate(exeBeginDt);
            loopWork.setExecuteDeadline(exeDeadLine);
            loopWork.setExecuteEndDate(exeEndDt);
            loopWork.setExecuteRemindTime(exeRemindTime);
            loopWork.setExecuteUserHeadImgUrl(executor.getHeadImgUrl());
//            loopWork.setId();
            loopWork.setModifyTime(DateUtil.currentDate());
//            loopWork.setScore();
            List<SysUser> ccPersons = determineSendersByExecutorType(executor, commonTask);
            if (ccPersons != null && ccPersons.size() > 0) {
                loopWork.setSendUserIds(StringUtils.join(ccPersons.stream().map(person -> Long.toString(person.getId())).collect(Collectors.toList()), ","));
            }
            loopWork.setSubWorkId(TaskUniqueIdUtils.genUniqueId());
//            loopWork.setTask();
            loopWork.setTaskId(commonTask.getId());
            loopWork.setType(1);
//            loopWork.setVersion();
            loopWork.setWorkId(uniWorkId);
//            loopWork.setWorkResult();
            loopWork.setWorkStatus(1);

            loopWorkMapper.insert(loopWork);

            createCommonLoopWorkOperateOptions(commonTask, loopWork, options);

        }
    }

    private List<SysUser> determineSendersByExecutorType(SysUser executor, TTask commonTask) {
        // 任务执行者选择店铺时，抄送人选择部门及岗位，只有符合条件且品牌和分区同时包含任务执行者所在店铺的品牌及分区的人员才可收到任务的抄送信息
        if (commonTask.getExecuteUserType() == 2) {
            return taskPersonMapper.filterSendersByExecutorShop(executor.getId(), commonTask.getId());
        } else { // 任务执行者选择部门及岗位时，抄送人选择部门及岗位，符合条件的所有人员均可收到任务的抄送信息；
            return taskPersonMapper.filterSendersByExecutorDpt(commonTask.getId());
        }
    }

    private List<SysUser> determinExecutors(TTask commonTask) {
        if (commonTask.getExecuteUserType() == 1) {
            return taskPersonMapper.filterTaskExecutorsByDpt(commonTask.getId());
        } else {
            return taskPersonMapper.filterTaskExecutorsByShop(commonTask.getId());
        }
    }

    private boolean tomorrowIsExecuteDate(TTask commonTask, Date tomorrow) {
        boolean flag = false;
        int cycleType = commonTask.getLoopCycleType() == null ? 0 : commonTask.getLoopCycleType();
        switch (cycleType) {
            case 1:
                int interval = commonTask.getLoopCycleValue() == null ? 0 : Integer.parseInt(commonTask.getLoopCycleValue());
                Date dt = commonTask.getTaskStartDate();
                while (DateUtil.compare(dt, commonTask.getTaskEndDate()) < 0) {
                    if (DateUtil.compare(dt, tomorrow) == 0) {
                        flag = true;
                        break;
                    }
                    dt = DateUtil.dateAddDay(dt, interval);
                }
                break;
            case 2:
                int dayOfWeek = DateUtil.getDayOfWeek(tomorrow);
                String weekDaysStr = commonTask.getLoopCycleValue() == null ? "" : commonTask.getLoopCycleValue();
                Set<Integer> daysWeek = new HashSet<Integer>(Arrays.asList(weekDaysStr.split(",")).stream().filter(s -> StringUtils.isNotBlank(s)).map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
                if (daysWeek.contains(dayOfWeek)) {
                    flag = true;
                }
                break;
            case 3:
                int dayOfMonth = DateUtil.getDay(tomorrow);
                String monthDaysStr = commonTask.getLoopCycleValue() == null ? "" : commonTask.getLoopCycleValue();
                Set<Integer> daysMonth = new HashSet<Integer>(Arrays.asList(monthDaysStr.split(",")).stream().filter(s -> StringUtils.isNotBlank(s)).map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
                if (daysMonth.contains(dayOfMonth)) {
                    flag = true;
                }
                break;
        }
        return flag;
    }

    private SysUser determinAuditorByExecutorType(SysUser executor, TTask commonTask) {
        if (commonTask.getExecuteUserType() == 2) { // 执行人按店铺：审核人是指定审核部门里对执行人店铺有数据权限的人。
            Long auditDeptId = commonTask.getAuditDptId();
            List<SysUser> supervisors = taskPersonMapper.fetchSupervisorsOfShop(auditDeptId, executor.getCurrentShopId());
            if (supervisors != null && supervisors.size() > 0) {
                int index = Long.valueOf(Math.round(Math.random() * (supervisors.size() - 1))).intValue();
                return supervisors.get(index);
            } else {
                return null;
            }
        } else { // 执行人按部门: 审核人是执行人的直接上级
            return taskPersonMapper.fetchImmediateSuperiorOfUser(executor.getId(), executor.getDptId(), executor.getCurrentPosId());
        }
    }

    private void createCommonLoopWorkOperateOptions(TTask commonTask, TLoopWork loopWork, List<TOperateOption> options) {
        if (commonTask.getScoreType() == 1) { // 任务整体评分
            return;
        }
        // 任务按操作项评分时，要报操作项也实例化
        if (options != null && options.size() > 0) {
            List<TOperateDataForWork> opOpts = new ArrayList<>();
            for (TOperateOption option : options) {
                TOperateDataForWork opOpt = new TOperateDataForWork();
                opOpt.setCreateTime(DateUtil.currentDate());
                opOpt.setImgOptionName(option.getFeedbackImgName());
                opOpt.setLoopWorkId(loopWork.getId());
                opOpt.setModifyTime(DateUtil.currentDate());
                opOpt.setNumOptionName(option.getFeedbackNumName());
                opOpt.setNumOptionType(option.getFeedbackNumType());
                opOpt.setTaskItemId(option.getId());
                opOpts.add(opOpt);
            }
            operateDataForWorkMapper.insertList(opOpts);
        }
    }

    @Override
    public List<SysPost> listPositionsOfDept(Long deptId) {
        return taskPersonMapper.listPositionsOfDept(deptId);
    }
}
