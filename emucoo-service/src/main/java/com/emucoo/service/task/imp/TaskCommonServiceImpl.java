package com.emucoo.service.task.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.emucoo.utils.DateUtil;
import com.emucoo.utils.WaterMarkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.dto.modules.task.Answer;
import com.emucoo.dto.modules.task.ImageUrl;
import com.emucoo.dto.modules.task.Review;
import com.emucoo.dto.modules.task.TaskCommonAuditIn;
import com.emucoo.dto.modules.task.TaskCommonAuditIn.AuditTaskItem;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.dto.modules.task.TaskCommonItem;
import com.emucoo.dto.modules.task.TaskCommonItemVo;
import com.emucoo.dto.modules.task.TaskCommonStatement;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn.TaskItem;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn.TaskItem.ImgUrl;
import com.emucoo.mapper.LoopWorkMapper;
import com.emucoo.mapper.WorkAnswerMapper;
import com.emucoo.mapper.WorkAuditMapper;
import com.emucoo.model.LoopWork;
import com.emucoo.model.User;
import com.emucoo.model.WorkAnswer;
import com.emucoo.model.WorkAudit;
import com.emucoo.model.WorkDataAppend;
import com.emucoo.model.WorkImgAppend;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.service.task.TaskCommonService;
import com.emucoo.service.task.WorkAuditService;
import com.emucoo.service.task.WorkDataAppendService;
import com.emucoo.service.task.WorkImgAppendService;
import com.emucoo.utils.ConstantsUtil;

@Transactional
@Service
public class TaskCommonServiceImpl implements TaskCommonService {

	@Resource
	private LoopWorkService loopWorkService;

	@Resource
	private WorkImgAppendService workImgAppendService;

	@Resource
	private WorkDataAppendService workDataAppendService;

	@Resource
	private WorkAuditService workAuditService;

	@Resource
	private WorkAnswerMapper workAnswerMapper;

	@Resource
	private LoopWorkMapper loopWorkMapper;

	@Resource
	private WorkAuditMapper workAuditMapper;

	/*@Autowired
	private WaterMarkUtils waterMarkUtils;*/

	@Override
	public void submitTask(TaskCommonSubmitIn taskCommonSubmitIn, User user) {
		LoopWork loopWork = new LoopWork();
		loopWork.setWorkid(taskCommonSubmitIn.getWorkID());
		loopWork.setType(taskCommonSubmitIn.getWorkType());
		loopWork.setSubWorkid(taskCommonSubmitIn.getSubID());
		loopWork.setSubmitUserId(user.getId());
		loopWork.setSubmitUserName(user.getUsername());
		loopWork.setModifyTime(DateUtil.currentDate());
		loopWork.setWorkStatus(2);

		List<WorkImgAppend> imgList = new ArrayList<WorkImgAppend>();
		List<WorkDataAppend> dataList = new ArrayList<WorkDataAppend>();

		List<TaskItem> taskItemArr = taskCommonSubmitIn.getTaskItemArr();
		for (TaskItem taskItem : taskItemArr) {
			List<ImgUrl> executeImgArr = taskItem.getExecuteImgArr();
			String imgurls = StringUtils.join(executeImgArr.stream().map(imgUrl -> WaterMarkUtils.genPicUrlWithWaterMark(imgUrl.getImgUrl(), imgUrl.getLocation(), DateUtil.dateToString(new Date(imgUrl.getDate())))).collect(Collectors.toList()), ",");
			String dts = StringUtils.join(executeImgArr.stream().map(imgUrl -> imgUrl.getDate()).collect(Collectors.toList()), ",");
			String locs = StringUtils.join(executeImgArr.stream().map(imgUrl -> imgUrl.getLocation()).collect(Collectors.toList()), "|");
            // 任务图片附录
            WorkImgAppend workImgAppend = new WorkImgAppend();
            workImgAppend.setWorkID(taskCommonSubmitIn.getWorkID());
            workImgAppend.setSubWorkID(taskCommonSubmitIn.getSubID());
            workImgAppend.setTaskItemID(taskItem.getTaskItemID());
            workImgAppend.setWorkTxt(taskItem.getWorkText());
            workImgAppend.setImgurls(imgurls);
            workImgAppend.setDate(dts);
            workImgAppend.setLocation(locs);
            workImgAppend.setInputType(ConstantsUtil.WorkImgAppend.INPUT_TYPE_ONE);
            workImgAppend.setCreateTime(DateUtil.currentDate());
            workImgAppend.setModifyTime(DateUtil.currentDate());
            imgList.add(workImgAppend);

			// 任务数字项附录
			WorkDataAppend workDataAppend = new WorkDataAppend();
			workDataAppend.setWorkID(taskCommonSubmitIn.getWorkID());
			workDataAppend.setSubWorkID(taskCommonSubmitIn.getSubID());
			workDataAppend.setTaskItemID(taskItem.getTaskItemID());
			workDataAppend.setDataValue(String.valueOf(taskItem.getDigitalItemValue()));
			workDataAppend.setDataType(ConstantsUtil.WorkDataAppend.DATA_TYPE_ONE);
			workDataAppend.setModifyTime(DateUtil.currentDate());
			workDataAppend.setCreateTime(DateUtil.currentDate());
			dataList.add(workDataAppend);
		}

		loopWorkService.modify(loopWork);
		workImgAppendService.addList(imgList);
		workDataAppendService.addList(dataList);
	}

	@Override
	public void auditTask(TaskCommonAuditIn taskCommonAuditIn, User user) {
		LoopWork loopWork = new LoopWork();
		loopWork.setWorkid(taskCommonAuditIn.getWorkID());
		loopWork.setType(taskCommonAuditIn.getWorkType());
		loopWork.setSubWorkid(taskCommonAuditIn.getSubID());
		loopWork.setAuditUserId(user.getId());
		loopWork.setAuditUserName(user.getUsername());

		List<WorkAudit> auditList = new ArrayList<WorkAudit>();
		List<AuditTaskItem> taskItemArr = taskCommonAuditIn.getReviewArr();
		for (AuditTaskItem taskItem : taskItemArr) {

			// 任务审核
			WorkAudit workAudit = new WorkAudit();
			workAudit.setWorkId(taskCommonAuditIn.getWorkID());
			workAudit.setSubWorkId(taskCommonAuditIn.getSubID());
			if(null!=taskItem.getTaskItemID() && !"".equals(taskItem.getTaskItemID()))
			   workAudit.setTaskItemId(taskItem.getTaskItemID());
			workAudit.setUserId(user.getId());
			workAudit.setUserName(user.getUsername());
			workAudit.setUserHeadUrl(user.getHeadImgUrl());
			workAudit.setAuditResult(taskItem.getReviewResult());
			workAudit.setContent(taskItem.getReviewOpinion());
			workAudit.setImgUrls(convertToString(taskItem.getReviewImgArr()));

			auditList.add(workAudit);
		}
		loopWorkService.modify(loopWork);
		workAuditService.addList(auditList);

	}

	@Override
	public TaskCommonDetailOut detail(TaskCommonDetailIn taskCommonDetailIn) {
		TaskCommonDetailOut out = new TaskCommonDetailOut();
		// taskStatement
		TaskCommonStatement taskStatement = loopWorkMapper.getTaskStatement(taskCommonDetailIn.getWorkID(), taskCommonDetailIn.getWorkType(), taskCommonDetailIn.getSubID());
		if(taskStatement != null) {
			taskStatement.setTaskImgArr(convertToList(taskStatement.getImgUrls()));
		}
		
		// TODO
		// taskItemArr
		List<TaskCommonItemVo> list = loopWorkMapper.getTaskCommonItem(taskCommonDetailIn.getWorkID(), taskCommonDetailIn.getSubID());
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
				item.setTaskItemImgArr(convertToList(vo.getItemImgUrls()));
				// TaskSubmit
				TaskCommonItem.TaskSubmit submit = new TaskCommonItem().new TaskSubmit();
				submit.setTaskSubID(vo.getTaskSubID());
				submit.setTaskSubHeadUrl(vo.getTaskSubHeadUrl());
				submit.setTaskSubTime(vo.getTaskSubTime());
				submit.setWorkText(vo.getWorkText());
				submit.setDigitalItemValue(vo.getDigitalItemValue());
				submit.setExecuteImgArr(convertToList(vo.getImageUrls()));
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
				review.setReviewImgArr(convertToList(vo.getImgUrls()));
				item.setTaskReview(review);
				itemList.add(item);
			}
		}

		// allTaskReview
		WorkAudit workAudit = workAuditMapper.getWorkAudit(taskCommonDetailIn.getWorkID(), taskCommonDetailIn.getSubID());
		Review review = new Review();
		if (workAudit != null) {
			review.setAuditorID(workAudit.getUserId());
			review.setAuditorName(workAudit.getUserName());
			review.setAuditorHeadUrl(workAudit.getUserHeadUrl());
			review.setReviewID(workAudit.getId());
			review.setReviewResult(workAudit.getAuditResult());
			review.setReviewOpinion(workAudit.getContent());
			review.setReviewTime(workAudit.getCreateTime().getTime());
			review.setReviewImgArr(convertToList(workAudit.getImgUrls()));
		}

		// taskReplyArr
		List<WorkAnswer> workAnswerList = workAnswerMapper.fetchAssignWorkAnswers(taskCommonDetailIn.getWorkID(), taskCommonDetailIn.getSubID());
		List<Answer> answerList = new ArrayList<Answer>();
		if (workAnswerList != null && workAnswerList.size() > 0) {
			for (WorkAnswer workAnswer : workAnswerList) {
				Answer answer = new Answer();
				answer.setReplyID(Integer.parseInt(String.valueOf(workAnswer.getId())));
				answer.setReplyContent(workAnswer.getContent());
				answer.setReplyTime(workAnswer.getCreateTime().getTime());
				answer.setAnswerID(Integer.parseInt(String.valueOf(workAnswer.getUserId())));
				answer.setAnswerName(workAnswer.getUserName());
				answer.setAnswerHeadUrl(workAnswer.getUserheadurl());
				answer.setReplyAction(workAnswer.getAnsweropt());
				answer.setReplyImgArr(convertToList(workAnswer.getImgurls()));
				answerList.add(answer);
			}
		}

		out.setTaskStatement(taskStatement);
		out.setTaskItemArr(itemList);
		out.setAllTaskReview(review);
		out.setTaskReplyArr(answerList);
		return out;
	}
	
	/**
	 * 图片地址数据格式转换
	 * 
	 * @param imgUrls
	 * @return
	 */
	private List<ImageUrl> convertToList(String imgUrls) {
		List<ImageUrl> list = new ArrayList<ImageUrl>();
		if (imgUrls != null && !"".equals(imgUrls)) {
			String[] imgArr = imgUrls.split(",");
			for (int i = 0; i < imgArr.length; i++) {
				ImageUrl imgUrl = new ImageUrl();
				imgUrl.setImgUrl(imgArr[i]);
				list.add(imgUrl);
			}
		}
		return list;
	}
	
	/**
	 * 图片地址数据格式转换，List转String
	 * 
	 * @param list
	 * @return
	 */
	private String convertToString(List<ImageUrl> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i == (list.size() - 1)) {
					sb.append(list.get(i).getImgUrl());
				} else {
					sb.append(list.get(i).getImgUrl()).append(ConstantsUtil.Separator.SEPARATOR_IMGURL);
				}
			}
		}
		return sb.toString();
	}

}
