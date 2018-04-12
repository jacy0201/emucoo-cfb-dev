package com.emucoo.restApi.controller.task.assign;

import javax.annotation.Resource;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.ReSubmitTokenManager;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.task.*;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.emucoo.restApi.controller.demo.AppBaseController;

import java.util.*;

@RestController
@RequestMapping("/api/task/assign")
public class TaskAssignController extends AppBaseController{
	
	@Resource
	private TaskDesignService taskDesignService;
	
	@Resource
	private TaskTemplateService taskTemplateService;
	 
	@Resource
	private TaskTypeService taskTypeService;

	@Resource
    private LoopWorkService loopWorkService;

	@Resource
    private UserService userService;

	@Resource
    private WorkDataAppendService workDataAppendService;

	@Autowired
    private WorkImgAppendService workImgAppendService;

	/**
     *  取得防止重复提交的token
	 */
    @PostMapping("/fetchSubmitToken")
	public AppResult<SubmitTokenVo_O> fetchSubmitToken() {
		SubmitTokenVo_O submitToken = new SubmitTokenVo_O();
		submitToken.setSubmitToken(ReSubmitTokenManager.getToken());
	    return success(submitToken);
    }

    /**
     * 取联系人列表
     * @param params
     * @return
     */
    @PostMapping("/fetchContacts")
    public AppResult<ContactsVo_O> fetchContacts(@RequestBody ParamVo<ContactsVo_I> params) {
	    ContactsVo_I contactsVo_i = params.getData();
	    int contactsType = Integer.parseInt(contactsVo_i.getContactsType());
	    List<TaskUnionUserList> contactsList = taskTypeService.findContacts(contactsType);
	    List<ContactVo> allContacts = new ArrayList<ContactVo>();

	    for(TaskUnionUserList tu : contactsList) {
	        ContactVo cv = new ContactVo();
	        cv.setContactsID(tu.getUnionId());
	        cv.setContactsName(tu.getUserName()==null?"":tu.getUserName());
	        cv.setContactsHeadUrl(tu.getUserHeadUrl()==null?"":tu.getUserHeadUrl());
            allContacts.add(cv);
        }

        ContactsVo_O result = new ContactsVo_O();
	    result.setAllContactsArr(allContacts);
        int len = allContacts.size();
	    if(len > 9) {
	        List<ContactVo> topContacts = allContacts.subList(0, 9);
	        result.setTopContactsArr(topContacts);
        } else {
	        result.setTopContactsArr(allContacts);
        }
        return success(result);

    }

    /**
     * 创建指派任务
     * @param params
     * @return
     */
    @PostMapping("/createTask")
	public AppResult<String> createTask(@RequestBody  ParamVo<AssignTaskCreationVo_I> params) {
	    AssignTaskCreationVo_I assignTaskCreationVo_i = params.getData();
	    String submitToken = assignTaskCreationVo_i.getSubmitToken();

	    if(ReSubmitTokenManager.validateToken(submitToken)){
	    	loopWorkService.createAssignTask(assignTaskCreationVo_i);
	    	ReSubmitTokenManager.clearToken(submitToken);
	    	return success("创建成功");
		} else {
	        return fail(AppExecStatus.SERVICE_ERR, "创建任务失败！");
		}

	}

    /**
     * 提交指派任务
     * @param params
     * @return
     */
    @PostMapping("/submitTask")
    public AppResult<String> submitTask(@RequestBody ParamVo<AssignTaskSubmitVo_I> params) {
        AssignTaskSubmitVo_I atsi = params.getData();
        loopWorkService.submitAssignTask(atsi);
        return success("提交成功。");
    }

    /**
     * 审核指派任务
     * @param params
     * @return
     */
    @PostMapping("/auditTask")
    public AppResult<String> auditTask(@RequestBody ParamVo<AssignTaskAuditVo_I> params) {
        User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        checkParam(user, "没有当前用户！");
        AssignTaskAuditVo_I atai = params.getData();
        loopWorkService.auditAssignTask(user, atai);

        return success("审核成功。");
    }

    /**
     * 查看指派任务的详细信息
     * @param userToken
     * @param params
     * @return
     */
    @PostMapping("/taskDetail")
	public AppResult<AssignTaskDetailVo_O> taskDetail(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<AssignTaskDetailVo_I> params) {
        AssignTaskDetailVo_I voi = params.getData();
        int workType = voi.getWorkType();
        String workId = voi.getWorkID();
        String subWorkId = voi.getSubID();

        AssignTaskDetailVo_O result = new AssignTaskDetailVo_O();
        LoopWork loopWork = loopWorkService.fetchByWorkId(workId, subWorkId, workType);
        if(loopWork == null){
            return fail(AppExecStatus.SERVICE_ERR, "找不到对应的任务信息！");
        }

        AssignTaskStateVo stateVo = new AssignTaskStateVo();
        stateVo.setBackTime(new Date().getTime());
        stateVo.setTaskStatus(loopWork.getWorkStatus()==null?0:loopWork.getWorkStatus());
        stateVo.setTaskTitle(loopWork.getName()==null?"":loopWork.getName());
        stateVo.setTaskExplain(loopWork.getDescription()==null?"":loopWork.getDescription());
        stateVo.setStartDate(DateUtil.simple(loopWork.getStartTime()));
        stateVo.setEndDate(DateUtil.simple(loopWork.getEndTime()));
        if(loopWork.getSubmitDeadline() != null) stateVo.setSubmitDeadline(loopWork.getSubmitDeadline().getTime());
        if(loopWork.getAuditDeadline() != null) stateVo.setReviewDeadline(loopWork.getAuditDeadline().getTime());
        stateVo.setAuditorID(loopWork.getAuditUserId()==null?0:loopWork.getAuditUserId());
        stateVo.setAuditorName(loopWork.getAuditUserName()==null?"":loopWork.getAuditUserName());
        stateVo.setCcPersonNames(loopWork.getSendUserName());
        stateVo.setDigitalItemName(loopWork.getDigitalItemName()==null?"":loopWork.getDigitalItemName());
        stateVo.setDigitalItemType(loopWork.getDigitalItemType());
        stateVo.setExecutorID(loopWork.getSubmitUserId()==null?0:loopWork.getSubmitUserId());
        stateVo.setExecutorName(loopWork.getSubmitUserName()==null?"":loopWork.getSubmitUserName());
        stateVo.setTaskRank(loopWork.getWorkLevel()==null?5:loopWork.getWorkLevel());
        stateVo.setTaskRepeatType(loopWork.getTaskRepeatType());
        stateVo.setTaskRepeatValue(loopWork.getTaskRepeatValue()==null?"":loopWork.getTaskRepeatValue());
        stateVo.setFeedbackImg(loopWork.getFeedbackImgType());
        stateVo.setFeedbackText(loopWork.getFeedbackTextType());
        List<ImageUrlVo> ivs = new ArrayList<ImageUrlVo>();
        List<WorkImgAppend> tmpImgs = workImgAppendService.fetchTaskImgs(workId, subWorkId);
//        List<ImgAppend> tmpImgs = taskTemplateService.findImgsByTempletId(loopWork.getTaskTempId()==null?0L:loopWork.getTaskTempId());
        for(WorkImgAppend ia : tmpImgs){
            String url = ia.getImgurls()==null?"":ia.getImgurls();
            String[] urls = url.split(",");
            for(String s : urls) {
                ImageUrlVo iv = new ImageUrlVo();
                iv.setImgUrl(s);
                ivs.add(iv);
            }
        }
        stateVo.setTaskImgArr(ivs);
        result.setTaskStatement(stateVo);

        User submitUser = userService.findById(loopWork.getCreateUserId());
        WorkDataAppend workDataAppend = workDataAppendService.fetchWorkDataAppend(loopWork.getWorkid(), loopWork.getSubWorkid());
        AssignTaskSubmitDetailVo submitVo = new AssignTaskSubmitDetailVo();
        if(loopWork.getCreateUserId()!=null) submitVo.setTaskSubPerID(loopWork.getCreateUserId());
        submitVo.setTaskSubPerHeadUrl(submitUser==null?"":submitUser.getHeadImgUrl());
        if(loopWork.getCreateTime()!=null) submitVo.setTaskSubTime(loopWork.getCreateTime().getTime());
        submitVo.setWorkText(loopWork.getWorkText());
        submitVo.setDigitalItemValue(Double.parseDouble(workDataAppend==null?"0.0":workDataAppend.getDataValue()));
        List<ImageUrlVo> svs = new ArrayList<ImageUrlVo>();
        List<WorkImgAppend> was = loopWorkService.fetchSubmitImgs(loopWork.getWorkid(), loopWork.getSubWorkid());
        for(WorkImgAppend wa : was) {
            String url = wa.getImgurls()==null?"":wa.getImgurls();
            String[] urls = url.split(",");
            for(String s:urls) {
                ImageUrlVo sv = new ImageUrlVo();
                sv.setImgUrl(wa.getImgurls());
                svs.add(sv);
            }
        }
        //TODO
        submitVo.setWorkText(loopWork.getWorkText()==null?"":loopWork.getWorkText());
        submitVo.setExecuteImgArr(svs);
        result.setTaskSubmit(submitVo);

        WorkAudit workAudit = loopWorkService.fetchAssignWorkAudit(loopWork.getWorkid(), loopWork.getSubWorkid());
        AssignTaskReviewVo reviewVo = new AssignTaskReviewVo();
        if(workAudit != null) {
            reviewVo.setReviewResult(workAudit.getAuditResult()==null?0:workAudit.getAuditResult());
            reviewVo.setReviewID(workAudit.getId());
            reviewVo.setReviewOpinion(workAudit.getContent());
            if(workAudit.getCreateTime()!=null) reviewVo.setReviewTime(workAudit.getCreateTime().getTime());
            if(workAudit.getUserId()!=null) reviewVo.setAuditorID(workAudit.getUserId());
            reviewVo.setAuditorName(workAudit.getUserName());
            reviewVo.setAuditorHeadUrl(workAudit.getUserHeadUrl());
            String imgurls = workAudit.getImgUrls();
            String[] imgurll = imgurls.split(",");
            List<ImageUrlVo> xvs = new ArrayList<ImageUrlVo>();
            for (String img : imgurll) {
                ImageUrlVo xv = new ImageUrlVo();
                xv.setImgUrl(img);
                xvs.add(xv);
            }
            reviewVo.setReviewImgArr(xvs);
        } else {
            reviewVo.setReviewImgArr(new ArrayList<ImageUrlVo>());
        }
        result.setTaskReview(reviewVo);

        User loginUser = UserTokenManager.getInstance().currUser(userToken);
        List<AssignTaskReplyVo> replyVos = new ArrayList<AssignTaskReplyVo>();
        List<Comment> wns = loopWorkService.fetchAssignWorkAnswers(loopWork.getWorkid(), loopWork.getSubWorkid());
        if(wns != null) {
            for (Comment wn : wns) {
                AssignTaskReplyVo rv = new AssignTaskReplyVo();
                rv.setReplyID(wn.getId());
                rv.setReplyContent(wn.getContent());
                if(wn.getCreateTime()!=null) rv.setReplyTime(wn.getCreateTime().getTime());
                if(wn.getUserId()!=null) {
                    rv.setAnswerID(wn.getUserId());
                    rv.setAnswerName(wn.getUserName());
                    User user = userService.findById(rv.getAnswerID());
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
                String urls = wn.getImgUrls() == null ? "" : wn.getImgUrls();
                String[] urlsl = urls.split(",");
                for (String str : urlsl) {
                    ImageUrlVo im = new ImageUrlVo();
                    im.setImgUrl(str);
                    ims.add(im);
                }
                rv.setReplyImgArr(ims);
                replyVos.add(rv);
            }
        }
        result.setTaskReplyArr(replyVos);

        return success(result);
	}

    /**
     * 查看指派任务的历史数据
     * @param param
     * @return
     */
	@PostMapping("/viewHistoryData")
    public AppResult<AssignTaskHistoryVo_O> viewHistoryData(@RequestBody ParamVo<AssignTaskHistoryVo_I> param) {
	    AssignTaskHistoryVo_I ahi = param.getData();
	    int workType = ahi.getWorkType();
	    String workId = ahi.getWorkID();
	    String subWorkId = ahi.getSubID();

	    LoopWork loopWork = loopWorkService.fetchByWorkId(workId, subWorkId, workType);

        AssignTaskHistoryVo_O result = new AssignTaskHistoryVo_O();
	    List<WorkDataAppend> aps = loopWorkService.fetchAssignTaskHistory(workId, subWorkId, workType);
	    List<AssignTaskHistoryItemVo> its = new ArrayList<AssignTaskHistoryItemVo>();
	    for(WorkDataAppend ap : aps) {
	        AssignTaskHistoryItemVo hio = new AssignTaskHistoryItemVo();
	        hio.setDate(ap.getCreateTime().getTime());
	        hio.setDigitalItemType(ap.getDataType());
	        hio.setValue(Double.parseDouble(ap.getDataValue()));
            its.add(hio);
        }

        result.setDigitalItemName(loopWork.getDigitalItemName());
	    result.setHistoryDataArr(its);

        return success(result);
    }

    /**
     * 根据类型查看任何一种任务的执行的历史列表
     * @param params
     * @return
     */
    @PostMapping("/viewExeHistory")
    public AppResult<ExeHistoryVo_O> viewExeHistory(@RequestBody ParamVo<ExeHistoryVo_I> params) {
        ExeHistoryVo_I voi = params.getData();
        String workType = voi.getWorkId();
        String workId = voi.getWorkId();
        List<LoopWork> loopWorks = loopWorkService.fetchExeHistory(workType, workId);

        ExeHistoryVo_O voo = new ExeHistoryVo_O();
        List<ExeHistoryItemVo> items = new ArrayList<ExeHistoryItemVo>();
        for(LoopWork lw : loopWorks) {
            ExeHistoryItemVo vo = new ExeHistoryItemVo();
            vo.setWorkId(lw.getWorkid());
            vo.setSubWorkId(lw.getSubWorkid());
            vo.setDate(lw.getSubmitDeadline().getTime());
            vo.setStatus(lw.getWorkResult());
            items.add(vo);
        }
        voo.setTotalImplementNum(loopWorks.size());
        voo.setHistoryTaskArr(items);

        return success(voo);
    }


}
