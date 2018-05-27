package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "t_work_target")
public class TWorkTarget extends BaseEntity {
    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 目标月份：YYYY-MM
     */
    private String month;

    /**
     * kpi
     */
    private String kpi;

    /**
     * 工作重点
     */
    @Column(name = "work_content")
    private String workContent;

    /**
     * 进货时间
     */
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_user_id")
    private Long modifyUserId;

    @Column(name = "modifyTime")
    private Date modifytime;

    /**
     * 1-删除；0-未删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "org_id")
    private Long orgId;

    /**
     * 获取PK
     *
     * @return id - PK
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置PK
     *
     * @param id PK
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取目标月份：YYYYMM
     *
     * @return month - 目标月份：YYYYMM
     */
    public String getMonth() {
        return month;
    }

    /**
     * 设置目标月份：YYYYMM
     *
     * @param month 目标月份：YYYYMM
     */
    public void setMonth(String month) {
        this.month = month;
    }


    /**
     * 获取kpi
     *
     * @return kpi - kpi
     */
    public String getKpi() {
        return kpi;
    }

    /**
     * 设置kpi
     *
     * @param kpi kpi
     */
    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    /**
     * 获取工作重点
     *
     * @return work_content - 工作重点
     */
    public String getWorkContent() {
        return workContent;
    }

    /**
     * 设置工作重点
     *
     * @param workContent 工作重点
     */
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    /**
     * 获取进货时间
     *
     * @return purchase_date - 进货时间
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * 设置进货时间
     *
     * @param purchaseDate 进货时间
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
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
     * @return modifyTime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获取1-删除；0-未删除
     *
     * @return is_del - 1-删除；0-未删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置1-删除；0-未删除
     *
     * @param isDel 1-删除；0-未删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
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
}