package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.base.ParamVo;
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

import javax.annotation.Resource;

/**
 * @author river
 * @date 2018/3/14 14:19
 */
@Service
public class LoopWorkServiceImpl extends BaseServiceImpl<TLoopWork> implements LoopWorkService {

    @Resource
    private LoopWorkMapper loopWorkMapper;

    @Resource
    private WorkImgAppendMapper workImgAppendMapper;

    @Resource
    private WorkAuditMapper workAuditMapper;

    @Resource
    private WorkAnswerMapper workAnswerMapper;

    @Resource
    private WorkDataAppendMapper workDataAppendMapper;

    @Resource
    private TaskTypeMapper taskTypeMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    /**
     *
     * @param submitUserId
     * @param date
     * @return
     */
    @Override
    public List<TLoopWork> list(Long submitUserId, Date date){
        return loopWorkMapper.list(submitUserId,date);
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
		loopWorkMapper.add(loopWork);
	}

	@Override
	public void modify(TLoopWork loopWork) {
		loopWorkMapper.update(loopWork);
	}
	
    @Override
    public TLoopWork fetchByWorkId(String workId, String subWorkId, int workType) {
        return loopWorkMapper.fetchByWorkIdAndType(workId, subWorkId, workType);
    }

    @Override
    public List<WorkImgAppend> fetchSubmitImgs(String workId, String subWorkId) {
        return workImgAppendMapper.fetchSubmitImgs(workId, subWorkId);
    }

    @Override
    public WorkAudit fetchAssignWorkAudit(String workId, String subWorkId) {
        return workAuditMapper.fetchAssignWorkAudit(workId, subWorkId);
    }

    @Override
    public List<Comment> fetchAssignWorkAnswers(String workId, String subWorkId) {
        return commentMapper.fetchAssignTaskComments(workId, subWorkId);
    }

    @Override
    public List<WorkDataAppend> fetchAssignTaskHistory(String workId, String subWorkId, int workType) {
        return workDataAppendMapper.fetchAssignTaskHistory(workId, subWorkId, workType);
    }

    @Override
    @Transactional
    public void submitAssignTask(AssignTaskSubmitVo_I voi) {
        TLoopWork lw = new TLoopWork();
        lw.setType(voi.getWorkType());
        lw.setWorkid(voi.getWorkID());
        lw.setSubWorkid(voi.getSubID());
        lw.setWorkStatus(2);
        lw.setWorkText(voi.getWorkText());
        lw.setAuditDeadline(DateUtil.addDateHours(new Date(), 12));
        lw.setModifyTime(DateUtil.currentDate());

        List<AssignTaskSubmitImgVo> imgsi = voi.getExecuteImgArr();
        List<WorkImgAppend> imgs = new ArrayList<WorkImgAppend>();
        if(imgsi!=null && imgsi.size()>0) {
            for (AssignTaskSubmitImgVo imgi : imgsi) {
                WorkImgAppend img = new WorkImgAppend();
                img.setWorkID(lw.getWorkid());
                img.setSubWorkID(lw.getSubWorkid());
                img.setTaskItemID(1L);
                Date dt = new Date(imgi.getDate());
                img.setCreateTime(dt);
                img.setImgurls(WaterMarkUtils.genPicUrlWithWaterMark(imgi.getImgUrl(), imgi.getLocation(), DateUtil.dateToString(dt)));
                img.setLocation(imgi.getLocation());
                img.setCreateTime(DateUtil.currentDate());
                img.setModifyTime(DateUtil.currentDate());
                imgs.add(img);
            }
        }

        WorkDataAppend data = new WorkDataAppend();
        data.setWorkID(lw.getWorkid());
        data.setSubWorkID(lw.getSubWorkid());
        data.setDataValue(Double.toString(voi.getDigitalItemValue()));
        data.setModifyTime(DateUtil.currentDate());
        data.setCreateTime(DateUtil.currentDate());

        loopWorkMapper.updateWorkStatus(lw);
        if(imgs.size() > 0) workImgAppendMapper.insertImgs(imgs);
        workDataAppendMapper.insertAssignTaskAppend(data);
    }

    @Override
    public void auditAssignTask(User user, AssignTaskAuditVo_I atai) {
        TLoopWork loopWork = new TLoopWork();
        loopWork.setWorkid(atai.getWorkID());
        loopWork.setType(atai.getWorkType());
        loopWork.setSubWorkid(atai.getSubID());
        loopWork.setWorkResult(atai.getReviewResult());
        loopWork.setAuditUserId(user.getId());
        loopWork.setAuditUserName(user.getUsername());

        List<ImageUrlVo> imgs = atai.getReviewImgArr();

        List<String> imgList=new ArrayList<String>();
        for(ImageUrlVo imageUrlVo:imgs){
            imgList.add(imageUrlVo.getImgUrl());
        }

        String imgurls="";
        if(null!=imgs && imgs.size()>0 ){
            // imgurls = imgs.stream().map(imageUrlVo -> imageUrlVo.getImgUrl()).reduce((s, l) -> s + "," + l).get();
            imgurls= StringUtils.join(imgList.toArray(), ",");
        }
        WorkAudit workAudit = new WorkAudit();
        workAudit.setWorkId(atai.getWorkID());
        workAudit.setSubWorkId(atai.getSubID());
        workAudit.setUserId(user.getId());
        workAudit.setUserHeadUrl(user.getHeadImgUrl());
        workAudit.setUserName(user.getUsername());
        workAudit.setImgUrls(imgurls);
        workAudit.setCreateTime(DateUtil.currentDate());
        workAudit.setModifyTime(DateUtil.currentDate());
        workAudit.setAuditResult(atai.getReviewResult());
        loopWorkMapper.updateWorkAudit(loopWork);
        workAuditMapper.insertSelective(workAudit);
    }

	@Override
	public TaskImproveSubmit getTaskImproveSubmit(String workId, String subWorkId) {
		return loopWorkMapper.getTaskImproveSubmit(workId, subWorkId);
	}

	@Override
	public TaskImproveStatement getTaskImproveStatement(String workId, String subWorkId) {
		return loopWorkMapper.getTaskImproveStatement(workId, subWorkId);
	}

    @Override
    public TaskType fetchTaskType(long id) {
        return taskTypeMapper.fetchTaskTypeById(id);
    }

    @Override
    @Transactional
    public void createAssignTask(AssignTaskCreationVo_I voi) {
        List<Date> dts = genDatesByRepeatType(voi);
        String uniWorkId = TaskUniqueIdUtils.genUniqueId();
        for(Date dt : dts) {
            TLoopWork lw = new TLoopWork();
            lw.setWorkStatus(1);
            lw.setAuditUserId(voi.getAuditorID());
            lw.setWorkid(uniWorkId);
            lw.setSubWorkid(TaskUniqueIdUtils.genUniqueId());
            lw.setStartTime(DateUtil.strToSimpleYYMMDDDate(voi.getStartDate()));
            lw.setEndTime(DateUtil.strToSimpleYYMMDDDate(voi.getEndDate()));
            lw.setSubmitBeginTime(dt);
            lw.setSubmitEndTime(dt);
            lw.setType(2);
            lw.setName(voi.getTaskTitle());
            lw.setDescription(voi.getTaskExplain());
            lw.setTaskTempId(0L);
            lw.setSubmitDeadlineRule(voi.getSubmitDeadlineRule());
            lw.setSubmitDeadline(DateUtil.yyyyMMddHHmmssStrToDate(DateUtil.simple(dt) + voi.getSubmitDeadlineRule()));
            lw.setFeedbackImgType(voi.getFeedbackImg());
            lw.setFeedbackTextType(voi.getFeedbackText());
            lw.setDigitalItemName(voi.getDigitalItemName());
            lw.setDigitalItemType(voi.getDigitalItemType());
            lw.setTaskRepeatType(voi.getTaskRepeatType());
            lw.setTaskRepeatValue(voi.getTaskRepeatValue());
            lw.setWorkLevel(voi.getTaskRank());
            lw.setCreateTime(new Date());
            lw.setModifyTime(new Date());


            // 存储关联的任务图片
            List<ImageUrlVo> imgarr = voi.getTaskImgArr();
            if (imgarr != null && imgarr.size() > 0) {
                WorkImgAppend wia = new WorkImgAppend();
                wia.setWorkID(lw.getWorkid());
                wia.setSubWorkID(lw.getSubWorkid());
                wia.setTaskItemID(0L);
                wia.setName(lw.getName());
                List<String> imgs = imgarr.stream().map(imageUrlVo -> imageUrlVo.getImgUrl()).collect(Collectors.toList());
                wia.setImgurls(String.join(",", imgs));
                wia.setInputType(3);
                workImgAppendMapper.insert(wia);

            }

            // 存储关联的Executor Ids
            List<ExecutorIdVo> exeids = voi.getExecutorArray();
            if (exeids != null && exeids.size() > 0) {
                lw.setSubmitUserId(exeids.get(0).getExecutorID());
                User user = userMapper.findById(lw.getSubmitUserId());
                if (user != null) {
                    lw.setSubmitUserName(user.getRealName());
                }
            }

            // 存储关联的Auditor d
            lw.setAuditUserId(voi.getAuditorID());
            User user = userMapper.findById(lw.getAuditUserId());
            if (user != null) {
                lw.setAuditUserName(user.getRealName());
            }

            // 存储关联的ccPerson Ids
            List<CCPersonIdVo> ccpids = voi.getCcPersonArray();
            if (ccpids != null && ccpids.size() > 0) {
                List<String> ccps = ccpids.stream().map(ccPersonIdVo -> Long.toString(ccPersonIdVo.getCcPersonID())).collect(Collectors.toList());
                List<String> ccpnms = ccpids.stream().map(ccPersonIdVo -> {
                    User u = userMapper.findById(ccPersonIdVo.getCcPersonID());
                    return u == null ? null : u.getRealName();
                }).filter(s -> {
                    return s == null ? false : true;
                }).collect(Collectors.toList());
                lw.setSendUserIds(String.join(",", ccps));
                lw.setSendUserName(String.join(",", ccpnms));
            }
            loopWorkMapper.insert(lw);
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
    public List<TLoopWork> fetchExeHistory(String workType, String workId) {
        return loopWorkMapper.fetchExeHistory(workType, workId);
    }

    @Autowired
    private TTaskMapper taskMapper;
    @Override
    public TTask fetchTaskById(long id) {
        return taskMapper.selectByPrimaryKey(id);
    }
}
