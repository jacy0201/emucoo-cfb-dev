package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_operate_data_for_work")
public class TOperateDataForWork extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作项ID
     */
    @Column(name = "task_item_id")
    private Long taskItemId;

    @Column(name = "name")
    private String name;

    /**
     * 工作文档
     */
    @Column(name = "work_txt")
    private String workTxt;

    @Column(name = "txt_need")
    private Boolean txtNeed;

    @Column(name = "txt_option_name")
    private String txtOptionName;

    @Column(name = "txt_option_description")
    private String txtOptionDescription;


    /**
     * 数字项名称
     */
    @Column(name = "num_option_name")
    private String numOptionName;

    /**
     * 数字项目值
     */
    @Column(name = "num_option_value")
    private String numOptionValue;

    /**
     * 1数值 2百分比 3货币
     */
    @Column(name = "num_option_type")
    private Integer numOptionType;

    @Column(name = "num_need")
    private Boolean numNeed;

    /**
     * 任务事务id
     */
    @Column(name = "loop_work_id")
    private Long loopWorkId;

    /**
     * 图片操作项名称
     */
    @Column(name = "img_option_name")
    private String imgOptionName;

    /**
     * 图片id，多个路径则对应多条记录【，】分割
     */
    @Column(name = "img_ids")
    private String imgIds;

    @Column(name = "img_need")
    private Boolean imgNeed;

    @Column(name = "img_option_max_count")
    private Integer imgOptionMaxCount;

    @Column(name = "img_option_from_album")
    private Boolean imgOptionFromAlbum;

    @Column(name = "img_example_id")
    private Long imgExampleId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 审核用户ID
     */
    @Column(name = "audit_user_id")
    private Long auditUserId;

    /**
     * 1合格 2不合格
     */
    @Column(name = "audit_result")
    private Integer auditResult;

    /**
     * 审核图片id，多个图片间用【,】分割
     */
    @Column(name = "audit_img_ids")
    private String auditImgIds;

    /**
     * 实际分数
     */
    private String score;

    @Column(name = "pre_score")
    private String preScore;

    @Column(name = "pre_weight")
    private String preWeight;

    /**
     * 审核内容
     */
    @Column(name = "audit_content")
    private String auditContent;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作项ID
     *
     * @return task_item_id - 操作项ID
     */
    public Long getTaskItemId() {
        return taskItemId;
    }

    /**
     * 设置操作项ID
     *
     * @param taskItemId 操作项ID
     */
    public void setTaskItemId(Long taskItemId) {
        this.taskItemId = taskItemId;
    }

    /**
     * 获取工作文档
     *
     * @return work_txt - 工作文档
     */
    public String getWorkTxt() {
        return workTxt;
    }

    /**
     * 设置工作文档
     *
     * @param workTxt 工作文档
     */
    public void setWorkTxt(String workTxt) {
        this.workTxt = workTxt;
    }

    /**
     * 获取数字项名称
     *
     * @return num_option_name - 数字项名称
     */
    public String getNumOptionName() {
        return numOptionName;
    }

    /**
     * 设置数字项名称
     *
     * @param numOptionName 数字项名称
     */
    public void setNumOptionName(String numOptionName) {
        this.numOptionName = numOptionName;
    }

    /**
     * 获取数字项目值
     *
     * @return num_option_value - 数字项目值
     */
    public String getNumOptionValue() {
        return numOptionValue;
    }

    /**
     * 设置数字项目值
     *
     * @param numOptionValue 数字项目值
     */
    public void setNumOptionValue(String numOptionValue) {
        this.numOptionValue = numOptionValue;
    }

    /**
     * 获取1数值 2百分比 3货币
     *
     * @return num_option_type - 1数值 2百分比 3货币
     */
    public Integer getNumOptionType() {
        return numOptionType;
    }

    /**
     * 设置1数值 2百分比 3货币
     *
     * @param numOptionType 1数值 2百分比 3货币
     */
    public void setNumOptionType(Integer numOptionType) {
        this.numOptionType = numOptionType;
    }

    /**
     * 获取任务事务id
     *
     * @return loop_work_id - 任务事务id
     */
    public Long getLoopWorkId() {
        return loopWorkId;
    }

    /**
     * 设置任务事务id
     *
     * @param loopWorkId 任务事务id
     */
    public void setLoopWorkId(Long loopWorkId) {
        this.loopWorkId = loopWorkId;
    }

    /**
     * 获取图片操作项名称
     *
     * @return img_option_name - 图片操作项名称
     */
    public String getImgOptionName() {
        return imgOptionName;
    }

    /**
     * 设置图片操作项名称
     *
     * @param imgOptionName 图片操作项名称
     */
    public void setImgOptionName(String imgOptionName) {
        this.imgOptionName = imgOptionName;
    }

    /**
     * 获取图片id，多个路径则对应多条记录【，】分割
     *
     * @return img_ids - 图片id，多个路径则对应多条记录【，】分割
     */
    public String getImgIds() {
        return imgIds;
    }

    /**
     * 设置图片id，多个路径则对应多条记录【，】分割
     *
     * @param imgIds 图片id，多个路径则对应多条记录【，】分割
     */
    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取审核用户ID
     *
     * @return audit_user_id - 审核用户ID
     */
    public Long getAuditUserId() {
        return auditUserId;
    }

    /**
     * 设置审核用户ID
     *
     * @param auditUserId 审核用户ID
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * 获取1合格 2不合格
     *
     * @return audit_result - 1合格 2不合格
     */
    public Integer getAuditResult() {
        return auditResult;
    }

    /**
     * 设置1合格 2不合格
     *
     * @param auditResult 1合格 2不合格
     */
    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    /**
     * 获取审核图片id，多个图片间用【,】分割
     *
     * @return audit_img_ids - 审核图片id，多个图片间用【,】分割
     */
    public String getAuditImgIds() {
        return auditImgIds;
    }

    /**
     * 设置审核图片id，多个图片间用【,】分割
     *
     * @param auditImgIds 审核图片id，多个图片间用【,】分割
     */
    public void setAuditImgIds(String auditImgIds) {
        this.auditImgIds = auditImgIds;
    }

    /**
     * 获取实际分数
     *
     * @return score - 实际分数
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置实际分数
     *
     * @param score 实际分数
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 获取审核内容
     *
     * @return audit_content - 审核内容
     */
    public String getAuditContent() {
        return auditContent;
    }

    /**
     * 设置审核内容
     *
     * @param auditContent 审核内容
     */
    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTxtNeed() {
        return txtNeed;
    }

    public void setTxtNeed(Boolean txtNeed) {
        this.txtNeed = txtNeed;
    }

    public String getTxtOptionName() {
        return txtOptionName;
    }

    public void setTxtOptionName(String txtOptionName) {
        this.txtOptionName = txtOptionName;
    }

    public String getTxtOptionDescription() {
        return txtOptionDescription;
    }

    public void setTxtOptionDescription(String txtOptionDescription) {
        this.txtOptionDescription = txtOptionDescription;
    }

    public Boolean getNumNeed() {
        return numNeed;
    }

    public void setNumNeed(Boolean numNeed) {
        this.numNeed = numNeed;
    }

    public Boolean getImgNeed() {
        return imgNeed;
    }

    public void setImgNeed(Boolean imgNeed) {
        this.imgNeed = imgNeed;
    }

    public Integer getImgOptionMaxCount() {
        return imgOptionMaxCount;
    }

    public void setImgOptionMaxCount(Integer imgOptionMaxCount) {
        this.imgOptionMaxCount = imgOptionMaxCount;
    }

    public Boolean getImgOptionFromAlbum() {
        return imgOptionFromAlbum;
    }

    public void setImgOptionFromAlbum(Boolean imgOptionFromAlbum) {
        this.imgOptionFromAlbum = imgOptionFromAlbum;
    }

    public Long getImgExampleId() {
        return imgExampleId;
    }

    public void setImgExampleId(Long imgExampleId) {
        this.imgExampleId = imgExampleId;
    }

    public String getPreScore() {
        return preScore;
    }

    public void setPreScore(String preScore) {
        this.preScore = preScore;
    }

    public String getPreWeight() {
        return preWeight;
    }

    public void setPreWeight(String preWeight) {
        this.preWeight = preWeight;
    }
}