package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_main")
@ApiModel
public class TFormMain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表单名
     */
    @ApiModelProperty(name = "name", value="表单名")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "表单描述")
    private String description;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 预设总分
     */
    @Column(name = "total_score")
    private Integer totalScore;

    /**
     * 表单是否允许添加机会点
     */
    @Column(name = "can_add_oppt")
    private Boolean canAddOppt;

    /**
     * 抄送范围
     */
    @Column(name = "cc_dpt_ids")
    private String ccDptIds;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "modify_user_id")
    private Long modifyUserId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "is_use")
    private Boolean isUse;

    @Column(name = "is_del")
    private Boolean isDel;

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
     * 获取表单名
     *
     * @return name - 表单名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置表单名
     *
     * @param name 表单名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取预设总分
     *
     * @return total_score - 预设总分
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * 设置预设总分
     *
     * @param totalScore 预设总分
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取表单是否允许添加机会点
     *
     * @return can_add_oppt - 表单是否允许添加机会点
     */
    public Boolean getCanAddOppt() {
        return canAddOppt;
    }

    /**
     * 设置表单是否允许添加机会点
     *
     * @param canAddOppt 表单是否允许添加机会点
     */
    public void setCanAddOppt(Boolean canAddOppt) {
        this.canAddOppt = canAddOppt;
    }

    /**
     * 获取抄送范围
     *
     * @return cc_dpt_ids - 抄送范围
     */
    public String getCcDptIds() {
        return ccDptIds;
    }

    /**
     * 设置抄送范围
     *
     * @param ccDptIds 抄送范围
     */
    public void setCcDptIds(String ccDptIds) {
        this.ccDptIds = ccDptIds;
    }

    /**
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return modify_user_id
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * @param modifyUserId
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}