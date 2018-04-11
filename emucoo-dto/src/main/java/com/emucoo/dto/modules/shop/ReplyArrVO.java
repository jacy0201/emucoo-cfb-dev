package com.emucoo.dto.modules.shop;

import java.util.List;

/**
 * @author Simon
 *
 */
public class ReplyArrVO {
	private long replyID;
	private String replyContent;
	private long replyTime;
	private long answerID;
	private String answerName;
	private String answerHeadUrl;
	private int replyAction;
	private List<ReplyImgArrVO> replyImgArr;
	/**
	 * @return the replyID
	 */
	public long getReplyID() {
		return replyID;
	}
	/**
	 * @param replyID the replyID to set
	 */
	public void setReplyID(long replyID) {
		this.replyID = replyID;
	}
	/**
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * @param replyContent the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * @return the replyTime
	 */
	public long getReplyTime() {
		return replyTime;
	}
	/**
	 * @param replyTime the replyTime to set
	 */
	public void setReplyTime(long replyTime) {
		this.replyTime = replyTime;
	}
	/**
	 * @return the answerID
	 */
	public long getAnswerID() {
		return answerID;
	}
	/**
	 * @param answerID the answerID to set
	 */
	public void setAnswerID(long answerID) {
		this.answerID = answerID;
	}
	/**
	 * @return the answerName
	 */
	public String getAnswerName() {
		return answerName;
	}
	/**
	 * @param answerName the answerName to set
	 */
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	/**
	 * @return the answerHeadUrl
	 */
	public String getAnswerHeadUrl() {
		return answerHeadUrl;
	}
	/**
	 * @param answerHeadUrl the answerHeadUrl to set
	 */
	public void setAnswerHeadUrl(String answerHeadUrl) {
		this.answerHeadUrl = answerHeadUrl;
	}
	/**
	 * @return the replyAction
	 */
	public int getReplyAction() {
		return replyAction;
	}
	/**
	 * @param replyAction the replyAction to set
	 */
	public void setReplyAction(int replyAction) {
		this.replyAction = replyAction;
	}
	/**
	 * @return the replyImgArr
	 */
	public List<ReplyImgArrVO> getReplyImgArr() {
		return replyImgArr;
	}
	/**
	 * @param replyImgArr the replyImgArr to set
	 */
	public void setReplyImgArr(List<ReplyImgArrVO> replyImgArr) {
		this.replyImgArr = replyImgArr;
	}
	
}
