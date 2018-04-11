package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_operate_option")
public class TOperateOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作项名称
     */
    private String name;

    /**
     * 反馈文本名称
     */
    @Column(name = "feedback_text_name")
    private String feedbackTextName;

    /**
     * 反馈文本描述
     */
    @Column(name = "feedback_text_description")
    private String feedbackTextDescription;

    /**
     * 反馈数字名称
     */
    @Column(name = "feedback_num_name")
    private String feedbackNumName;

    /**
     * 反馈数字类型（1数值 2百分比 3货币）
     */
    @Column(name = "feedback_num_type")
    private Boolean feedbackNumType;

    /**
     * 反馈图片名称
     */
    @Column(name = "feedback_img_name")
    private String feedbackImgName;

    /**
     * 反馈图片最大数量
     */
    @Column(name = "feedback_img_max_count")
    private Integer feedbackImgMaxCount;

    /**
     * 是否允许从相册上传（0：不允许 1允许）
     */
    @Column(name = "feedback_img_from_album")
    private Boolean feedbackImgFromAlbum;

    /**
     * 反馈图片示例id
     */
    @Column(name = "feedback_img_example_id")
    private Long feedbackImgExampleId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 预设的满分
     */
    @Column(name = "preinstall_score")
    private String preinstallScore;

    /**
     * 预设的权重
     */
    @Column(name = "preinstall_weight")
    private String preinstallWeight;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取操作项名称
     *
     * @return name - 操作项名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置操作项名称
     *
     * @param name 操作项名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取反馈文本名称
     *
     * @return feedback_text_name - 反馈文本名称
     */
    public String getFeedbackTextName() {
        return feedbackTextName;
    }

    /**
     * 设置反馈文本名称
     *
     * @param feedbackTextName 反馈文本名称
     */
    public void setFeedbackTextName(String feedbackTextName) {
        this.feedbackTextName = feedbackTextName;
    }

    /**
     * 获取反馈文本描述
     *
     * @return feedback_text_description - 反馈文本描述
     */
    public String getFeedbackTextDescription() {
        return feedbackTextDescription;
    }

    /**
     * 设置反馈文本描述
     *
     * @param feedbackTextDescription 反馈文本描述
     */
    public void setFeedbackTextDescription(String feedbackTextDescription) {
        this.feedbackTextDescription = feedbackTextDescription;
    }

    /**
     * 获取反馈数字名称
     *
     * @return feedback_num_name - 反馈数字名称
     */
    public String getFeedbackNumName() {
        return feedbackNumName;
    }

    /**
     * 设置反馈数字名称
     *
     * @param feedbackNumName 反馈数字名称
     */
    public void setFeedbackNumName(String feedbackNumName) {
        this.feedbackNumName = feedbackNumName;
    }

    /**
     * 获取反馈数字类型（1数值 2百分比 3货币）
     *
     * @return feedback_num_type - 反馈数字类型（1数值 2百分比 3货币）
     */
    public Boolean getFeedbackNumType() {
        return feedbackNumType;
    }

    /**
     * 设置反馈数字类型（1数值 2百分比 3货币）
     *
     * @param feedbackNumType 反馈数字类型（1数值 2百分比 3货币）
     */
    public void setFeedbackNumType(Boolean feedbackNumType) {
        this.feedbackNumType = feedbackNumType;
    }

    /**
     * 获取反馈图片名称
     *
     * @return feedback_img_name - 反馈图片名称
     */
    public String getFeedbackImgName() {
        return feedbackImgName;
    }

    /**
     * 设置反馈图片名称
     *
     * @param feedbackImgName 反馈图片名称
     */
    public void setFeedbackImgName(String feedbackImgName) {
        this.feedbackImgName = feedbackImgName;
    }

    /**
     * 获取反馈图片最大数量
     *
     * @return feedback_img_max_count - 反馈图片最大数量
     */
    public Integer getFeedbackImgMaxCount() {
        return feedbackImgMaxCount;
    }

    /**
     * 设置反馈图片最大数量
     *
     * @param feedbackImgMaxCount 反馈图片最大数量
     */
    public void setFeedbackImgMaxCount(Integer feedbackImgMaxCount) {
        this.feedbackImgMaxCount = feedbackImgMaxCount;
    }

    /**
     * 获取是否允许从相册上传（0：不允许 1允许）
     *
     * @return feedback_img_from_album - 是否允许从相册上传（0：不允许 1允许）
     */
    public Boolean getFeedbackImgFromAlbum() {
        return feedbackImgFromAlbum;
    }

    /**
     * 设置是否允许从相册上传（0：不允许 1允许）
     *
     * @param feedbackImgFromAlbum 是否允许从相册上传（0：不允许 1允许）
     */
    public void setFeedbackImgFromAlbum(Boolean feedbackImgFromAlbum) {
        this.feedbackImgFromAlbum = feedbackImgFromAlbum;
    }

    /**
     * 获取反馈图片示例id
     *
     * @return feedback_img_example_id - 反馈图片示例id
     */
    public Long getFeedbackImgExampleId() {
        return feedbackImgExampleId;
    }

    /**
     * 设置反馈图片示例id
     *
     * @param feedbackImgExampleId 反馈图片示例id
     */
    public void setFeedbackImgExampleId(Long feedbackImgExampleId) {
        this.feedbackImgExampleId = feedbackImgExampleId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取预设的满分
     *
     * @return preinstall_score - 预设的满分
     */
    public String getPreinstallScore() {
        return preinstallScore;
    }

    /**
     * 设置预设的满分
     *
     * @param preinstallScore 预设的满分
     */
    public void setPreinstallScore(String preinstallScore) {
        this.preinstallScore = preinstallScore;
    }

    /**
     * 获取预设的权重
     *
     * @return preinstall_weight - 预设的权重
     */
    public String getPreinstallWeight() {
        return preinstallWeight;
    }

    /**
     * 设置预设的权重
     *
     * @param preinstallWeight 预设的权重
     */
    public void setPreinstallWeight(String preinstallWeight) {
        this.preinstallWeight = preinstallWeight;
    }
}