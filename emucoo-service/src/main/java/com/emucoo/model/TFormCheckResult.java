package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_check_result")
public class TFormCheckResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联表单主表
     */
    @Column(name = "form_main_id")
    private Long formMainId;

    /**
     * 关联的表单的名称
     */
    @Column(name = "form_main_name")
    private String formMainName;

    /**
     * 商铺的id
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 商铺名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 巡店安排的id
     */
    @Column(name = "front_plan_id")
    private Long frontPlanId;

    /**
     * l类型得分率
     */
    @Column(name = "score_rate")
    private Float scoreRate;

    /**
     * 得分
     */
    @Column(name = "score")
    private Integer score;

    @Column(name = "actual_total")
    private Integer actualTotal;

    @Column(name = "impt_item_deny_num")
    private Integer imptItemDenyNum;

    @Column(name = "na_num")
    private Integer naNum;

    @Column(name = "summary_img")
    private String summaryImg;
    /**
     * 提交结果的用户id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "summary")
    private String summary;

    @Column(name = "org_id")
    private Long orgId;


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
     * 获取关联表单主表
     *
     * @return form_main_id - 关联表单主表
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置关联表单主表
     *
     * @param formMainId 关联表单主表
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }

    /**
     * 获取关联的表单的名称
     *
     * @return form_main_name - 关联的表单的名称
     */
    public String getFormMainName() {
        return formMainName;
    }

    /**
     * 设置关联的表单的名称
     *
     * @param formMainName 关联的表单的名称
     */
    public void setFormMainName(String formMainName) {
        this.formMainName = formMainName;
    }

    /**
     * 获取商铺的id
     *
     * @return shop_id - 商铺的id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置商铺的id
     *
     * @param shopId 商铺的id
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取商铺名称
     *
     * @return shop_name - 商铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置商铺名称
     *
     * @param shopName 商铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取巡店安排的id
     *
     * @return front_plan_id - 巡店安排的id
     */
    public Long getFrontPlanId() {
        return frontPlanId;
    }

    /**
     * 设置巡店安排的id
     *
     * @param frontPlanId 巡店安排的id
     */
    public void setFrontPlanId(Long frontPlanId) {
        this.frontPlanId = frontPlanId;
    }

    /**
     * 获取l类型得分率
     *
     * @return score_rate - l类型得分率
     */
    public Float getScoreRate() {
        return scoreRate;
    }

    /**
     * 设置l类型得分率
     *
     * @param scoreRate l类型得分率
     */
    public void setScoreRate(Float scoreRate) {
        this.scoreRate = scoreRate;
    }

    /**
     * 获取得分
     *
     * @return score - 得分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置得分
     *
     * @param score 得分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取提交结果的用户id
     *
     * @return create_user_id - 提交结果的用户id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置提交结果的用户id
     *
     * @param createUserId 提交结果的用户id
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(Integer actualTotal) {
        this.actualTotal = actualTotal;
    }

    public Integer getImptItemDenyNum() {
        return imptItemDenyNum;
    }

    public void setImptItemDenyNum(Integer imptItemDenyNum) {
        this.imptItemDenyNum = imptItemDenyNum;
    }

    public Integer getNaNum() {
        return naNum;
    }

    public void setNaNum(Integer naNum) {
        this.naNum = naNum;
    }

    public String getSummaryImg() {
        return summaryImg;
    }

    public void setSummaryImg(String summaryImg) {
        this.summaryImg = summaryImg;
    }
}