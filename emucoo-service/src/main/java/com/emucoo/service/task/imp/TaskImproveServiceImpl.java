package com.emucoo.service.task.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.emucoo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.dto.modules.task.Answer;
import com.emucoo.dto.modules.task.ImageUrl;
import com.emucoo.dto.modules.task.Review;
import com.emucoo.dto.modules.task.TaskImproveAuditIn;
import com.emucoo.dto.modules.task.TaskImproveDetailIn;
import com.emucoo.dto.modules.task.TaskImproveDetailOut;
import com.emucoo.dto.modules.task.TaskImproveStatement;
import com.emucoo.dto.modules.task.TaskImproveSubmit;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn.ImgUrl;
import com.emucoo.dto.modules.task.TaskImproveVo;
import com.emucoo.dto.modules.task.TaskImproveVo.CCPerson;
import com.emucoo.dto.modules.task.TaskImproveVo.Executor;
import com.emucoo.mapper.ImgAppendMapper;
import com.emucoo.mapper.LoopPlanMapper;
import com.emucoo.mapper.LoopWorkMapper;
import com.emucoo.mapper.TaskTemplateMapper;
import com.emucoo.mapper.WorkAnswerMapper;
import com.emucoo.mapper.WorkAuditMapper;
import com.emucoo.model.ImgAppend;
import com.emucoo.model.LoopPlan;
import com.emucoo.model.LoopWork;
import com.emucoo.model.TaskTemplate;
import com.emucoo.model.User;
import com.emucoo.model.WorkAnswer;
import com.emucoo.model.WorkAudit;
import com.emucoo.model.WorkDataAppend;
import com.emucoo.model.WorkImgAppend;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.service.task.TaskImproveService;
import com.emucoo.service.task.WorkAuditService;
import com.emucoo.service.task.WorkDataAppendService;
import com.emucoo.service.task.WorkImgAppendService;

@Transactional
@Service
public class TaskImproveServiceImpl implements TaskImproveService {

	@Resource
	private LoopPlanMapper loopPlanMapper;

	@Resource
	private ImgAppendMapper imgAppendMapper;

	@Resource
	private TaskTemplateMapper taskTemplateMapper;

	@Autowired
	private LoopWorkMapper loopWorkMapper;

	@Autowired
	private WorkAnswerMapper workAnswerMapper;

	@Autowired
	private WorkAuditMapper workAuditMapper;

	@Resource
	private LoopWorkService loopWorkService;

	@Resource
	private WorkImgAppendService workImgAppendService;

	@Resource
	private WorkDataAppendService workDataAppendService;

	@Resource
	private WorkAuditService workAuditService;

	/*@Autowired
	private WaterMarkUtils waterMarkUtils;*/

	@Override
	public void save(TaskImproveVo vo, User user) {
		// LoopPlan
		LoopPlan lp = new LoopPlan();
		// lp.setId(vo.getPatrolShopArrangeID());//TODO
		lp.setDptId(vo.getShopID());
		lp.setOpptId(vo.getChancePointID());
		lp.setOpptTitle(vo.getChancePointTitle());
		lp.setLoopType(vo.getTaskRepeatType());
		lp.setLoopTypeValue(vo.getTaskRepeatValue());
		lp.setReportId(vo.getTaskReportID());
		loopPlanMapper.add(lp);

		// TaskTemplate
		TaskTemplate taskTemplate = new TaskTemplate();
		taskTemplate.setFeedbackTxt(vo.getFeedbackText());
		taskTemplate.setFeedbackImg(vo.getFeedbackImg());
		taskTemplate.setFeedbackData(vo.getDigitalItemName());
		taskTemplate.setFeedbackDataValue(vo.getDigitalItemType());
		taskTemplateMapper.insertTaskTemplate(taskTemplate);
		
		// ImgAppend
		ImgAppend imgAppend = new ImgAppend();
		imgAppend.setImgurls(convertToString(vo.getTaskImgArr()));
		imgAppend.setTaskTempletId(taskTemplate.getId());
		imgAppendMapper.add(imgAppend);

		// LoopWork
		List<LoopWork> loopWorkList = new ArrayList<LoopWork>();

		List<CCPerson> ccPersonlist = vo.getCcPersonArray();
		StringBuilder idSb = new StringBuilder();
		StringBuilder nameSb = new StringBuilder();
		for (CCPerson ccPerson : ccPersonlist) {
			idSb.append(ccPerson.getCcPersonID()).append(",");
			nameSb.append(ccPerson.getCcPersonName()).append(",");
		}
		String ccPersonIds = null;
		String ccPersonNames = null;
		if(StringUtils.isNotBlank(idSb)) {
			ccPersonIds = idSb.toString().substring(0, idSb.length() - 1);
			ccPersonNames = nameSb.toString().substring(0, nameSb.length() - 1);
		}

		List<Executor> executorList = vo.getExecutorArray();

		// 重复规则逻辑处理
		if (vo.getTaskRepeatType() == 0) {// 不重复
			for (Executor executor : executorList) {
				LoopWork loopWork = new LoopWork();
				loopWork.setWorkid(UUIDUtils.getUUID());
                loopWork.setSubWorkid(UUIDUtils.getUUID());
				loopWork.setLoopPlanId(lp.getId());
				loopWork.setTaskTempId(taskTemplate.getId());
				loopWork.setName(vo.getTaskTitle());
				loopWork.setDescription(vo.getTaskExplain());
				loopWork.setStartTime(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
				loopWork.setEndTime(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
				loopWork.setSubmitBeginTime(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
				loopWork.setSubmitEndTime(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
				loopWork.setSubmitDeadlineRule(vo.getSubmitDeadlineRule());
				loopWork.setSubmitDeadline(DateUtil.yyyyMMddHHmmssStrToDate(vo.getEndDate() + vo.getSubmitDeadlineRule()));
				loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_ONE);
				loopWork.setType(ConstantsUtil.LoopWork.TYPE_THREE);
				loopWork.setWorkLevel(vo.getTaskRank());
				loopWork.setSubmitUserId(executor.getExecutorID());
				loopWork.setSubmitUserName(executor.getExecutorName());
				loopWork.setAuditUserId(vo.getAuditorID());
				loopWork.setAuditUserName(vo.getAuditorName());
				loopWork.setSendUserIds(ccPersonIds);
				loopWork.setSendUserName(ccPersonNames);
				loopWork.setCreateUserId(user.getId());
				loopWork.setCreateUserName(user.getUsername());
				loopWorkList.add(loopWork);
			}
		} else if (vo.getTaskRepeatType() == 1) {// 每天
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = new SimpleDateFormat("yyyyMMdd").parse(vo.getStartDate());
				endDate = new SimpleDateFormat("yyyyMMdd").parse(vo.getEndDate());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			int days = daysBetween(startDate, endDate);
			for (int i = 0; i <= days; i++) {
				Calendar calendar = Calendar.getInstance();
				try {
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(vo.getStartDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.add(Calendar.DAY_OF_MONTH, i);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vo.getSubmitDeadlineRule().substring(0, 2)));
				calendar.set(Calendar.MINUTE, Integer.parseInt(vo.getSubmitDeadlineRule().substring(2, 4)));
				calendar.set(Calendar.SECOND, Integer.parseInt(vo.getSubmitDeadlineRule().substring(4, 6)));

				for (Executor executor : executorList) {
					LoopWork loopWork = new LoopWork();
					loopWork.setWorkid(UUIDUtils.getUUID());
                    loopWork.setSubWorkid(UUIDUtils.getUUID());
					loopWork.setLoopPlanId(lp.getId());
					loopWork.setTaskTempId(taskTemplate.getId());
					loopWork.setName(vo.getTaskTitle());
					loopWork.setDescription(vo.getTaskExplain());
					// 规则
					loopWork.setStartTime(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
					loopWork.setEndTime(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
					loopWork.setSubmitDeadlineRule(vo.getSubmitDeadlineRule());

					// 实例
					loopWork.setSubmitBeginTime(calendar.getTime());
					loopWork.setSubmitEndTime(calendar.getTime());
					loopWork.setSubmitDeadline(calendar.getTime());

					loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_ONE);
					loopWork.setType(ConstantsUtil.LoopWork.TYPE_THREE);
					loopWork.setWorkLevel(vo.getTaskRank());
					loopWork.setSubmitUserId(executor.getExecutorID());
					loopWork.setSubmitUserName(executor.getExecutorName());
					loopWork.setAuditUserId(vo.getAuditorID());
					loopWork.setAuditUserName(vo.getAuditorName());
					loopWork.setSendUserIds(ccPersonIds);
					loopWork.setSendUserName(ccPersonNames);
					loopWork.setCreateUserId(user.getId());
					loopWork.setCreateUserName(user.getUsername());
					loopWorkList.add(loopWork);
				}
			}
		} else if (vo.getTaskRepeatType() == 2) {// 每周
			// 符合条件日期集合
			List<String> list = WeekDayUtil.getDates(vo.getStartDate(), vo.getEndDate(), vo.getTaskRepeatValue());

			for (String date : list) {
				Calendar calendar = Calendar.getInstance();
				try {
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vo.getSubmitDeadlineRule().substring(0, 2)));
				calendar.set(Calendar.MINUTE, Integer.parseInt(vo.getSubmitDeadlineRule().substring(2, 4)));
				calendar.set(Calendar.SECOND, Integer.parseInt(vo.getSubmitDeadlineRule().substring(4, 6)));

				for (Executor executor : executorList) {
					LoopWork loopWork = new LoopWork();
					loopWork.setWorkid(UUIDUtils.getUUID());
                    loopWork.setSubWorkid(UUIDUtils.getUUID());
					loopWork.setLoopPlanId(lp.getId());
					loopWork.setTaskTempId(taskTemplate.getId());
					loopWork.setName(vo.getTaskTitle());
					loopWork.setDescription(vo.getTaskExplain());
					// 规则
					loopWork.setStartTime(DateUtil.strToSimpleYYMMDDDate(vo.getStartDate()));
					loopWork.setEndTime(DateUtil.strToSimpleYYMMDDDate(vo.getEndDate()));
					loopWork.setSubmitDeadlineRule(vo.getSubmitDeadlineRule());

					// 实例
					loopWork.setSubmitBeginTime(DateUtil.strToSimpleYYMMDDDate(date));
					loopWork.setSubmitEndTime(DateUtil.strToSimpleYYMMDDDate(date));
					loopWork.setSubmitDeadline(calendar.getTime());

					loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_ONE);
					loopWork.setType(ConstantsUtil.LoopWork.TYPE_THREE);
					loopWork.setWorkLevel(vo.getTaskRank());
					loopWork.setSubmitUserId(executor.getExecutorID());
					loopWork.setSubmitUserName(executor.getExecutorName());
					loopWork.setAuditUserId(vo.getAuditorID());
					loopWork.setAuditUserName(vo.getAuditorName());
					loopWork.setSendUserIds(ccPersonIds);
					loopWork.setSendUserName(ccPersonNames);
					loopWork.setCreateUserId(user.getId());
					loopWork.setCreateUserName(user.getUsername());
					loopWorkList.add(loopWork);
				}
			}
		}
		loopWorkMapper.addList(loopWorkList);
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
	public void submit(TaskImproveSubmitIn taskImproveSubmitIn, User user) {
		// LoopWork
		LoopWork loopWork = new LoopWork();
		loopWork.setWorkid(taskImproveSubmitIn.getWorkID());
		loopWork.setSubWorkid(taskImproveSubmitIn.getSubID());
		loopWork.setType(taskImproveSubmitIn.getWorkType());
		loopWork.setSubmitUserId(user.getId());
		loopWork.setWorkStatus(ConstantsUtil.LoopWork.WORK_STATUS_TWO);
		// 设置提交时间的12小时后
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.HOUR_OF_DAY, 12);
		loopWork.setAuditDeadline(cl.getTime());
		loopWork.setModifyTime(new Date());

		// WorkImgAppend
		List<ImgUrl> list = taskImproveSubmitIn.getExecuteImgArr();
		List<String> imgurls = list.stream().map(imgUrl -> {
			Date dt = new Date(imgUrl.getDate());
			return WaterMarkUtils.genPicUrlWithWaterMark(imgUrl.getImgUrl(), imgUrl.getLocation(), DateUtil.dateToString(dt));
		}).collect(Collectors.toList());

		List<String> locations = list.stream().map(imgUrl -> imgUrl.getLocation()).collect(Collectors.toList());
		List<String> dates = list.stream().map(imgUrl -> Long.toString(imgUrl.getDate())).collect(Collectors.toList());

		WorkImgAppend workImgAppend = new WorkImgAppend();
		workImgAppend.setWorkID(taskImproveSubmitIn.getWorkID());
		workImgAppend.setWorkTxt(taskImproveSubmitIn.getWorkText());
		workImgAppend.setImgurls(StringUtils.join(imgurls, ","));
		workImgAppend.setLocation(StringUtils.join(locations, "|"));
		workImgAppend.setDate(StringUtils.join(dates, ","));
		workImgAppend.setInputType(ConstantsUtil.WorkImgAppend.INPUT_TYPE_ONE);

		// WorkDataAppend
		WorkDataAppend workDataAppend = new WorkDataAppend();
		workDataAppend.setWorkID(taskImproveSubmitIn.getWorkID());
		workDataAppend.setDataValue(String.valueOf(taskImproveSubmitIn.getDigitalItemValue()));

		loopWorkService.modify(loopWork);
		workImgAppendService.add(workImgAppend);
		workDataAppendService.add(workDataAppend);
	}

	@Override
	public void audit(TaskImproveAuditIn taskImproveAuditIn, User user) {
		// LoopWork
		LoopWork loopWork = new LoopWork();
		loopWork.setWorkid(taskImproveAuditIn.getWorkID());
		loopWork.setType(taskImproveAuditIn.getWorkType());
		loopWork.setSubWorkid(taskImproveAuditIn.getSubID());
		loopWork.setAuditUserId(user.getId());
		loopWork.setWorkResult(taskImproveAuditIn.getReviewResult());

		// WorkAudit
		WorkAudit workAudit = new WorkAudit();
		workAudit.setWorkId(taskImproveAuditIn.getWorkID());
		workAudit.setSubWorkId(taskImproveAuditIn.getSubID());
		workAudit.setUserId(user.getId());
		workAudit.setUserName(user.getUsername());
		workAudit.setUserHeadUrl(user.getHeadImgUrl());
		workAudit.setAuditResult(taskImproveAuditIn.getReviewResult());
		workAudit.setContent(taskImproveAuditIn.getReviewOpinion());
		workAudit.setImgUrls(convertToString(taskImproveAuditIn.getReviewImgArr()));

		loopWorkService.modify(loopWork);
		workAuditService.add(workAudit);
	}

	@Override
	public TaskImproveDetailOut detail(TaskImproveDetailIn taskImproveDetailIn) {
		TaskImproveDetailOut out = new TaskImproveDetailOut();
		// taskStatement
		TaskImproveStatement statement = loopWorkService.getTaskImproveStatement(taskImproveDetailIn.getWorkID(), taskImproveDetailIn.getSubID());
		statement.setBackTime(new Date().getTime());
		statement.setStartDate(statement.getStartDate().replace("-", ""));
		statement.setEndDate(statement.getEndDate().replace("-", ""));
		statement.setSubmitDeadline(statement.getSubmitDeadlineDate().getTime());
		statement.setChancePointNum(1);
		if(statement.getReviewDeadlineDate() != null) {
			statement.setReviewDeadline(statement.getReviewDeadlineDate().getTime());
		}
		statement.setTaskImgArr(convertToList(statement.getImgUrls()));

		// taskSubmit
		TaskImproveSubmit submit = loopWorkService.getTaskImproveSubmit(taskImproveDetailIn.getWorkID(), taskImproveDetailIn.getSubID());
		submit.setExecuteImgArr(convertToList(submit.getImgUrls()));
		if(submit.getTaskSubTimeDate() != null) {
			submit.setTaskSubTime(submit.getTaskSubTimeDate().getTime());
		}

		// taskReview
		WorkAudit workAudit = workAuditMapper.getWorkAudit(taskImproveDetailIn.getWorkID(), taskImproveDetailIn.getSubID());
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
		List<WorkAnswer> workAnswerList = workAnswerMapper.fetchAssignWorkAnswers(taskImproveDetailIn.getWorkID(), taskImproveDetailIn.getSubID());
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

		out.setTaskStatement(statement);
		out.setTaskSubmit(submit);
		out.setTaskReview(review);
		out.setTaskReplyArr(answerList);
		return out;
	}

	/**
	 * 图片地址数据格式转换，String转List
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
