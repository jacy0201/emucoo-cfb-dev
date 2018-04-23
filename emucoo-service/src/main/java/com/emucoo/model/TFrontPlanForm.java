package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Table(name = "t_front_plan_form")
public class TFrontPlanForm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 巡店安排id
     */
    @Column(name = "front_plan_id")
    private Long frontPlanId;

    /**
     * 表单表id
     */
    @Column(name = "form_main_id")
    private Long formMainId;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_status")
    private Byte reportStatus;

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
     * 获取巡店安排id
     *
     * @return front_plan_id - 巡店安排id
     */
    public Long getFrontPlanId() {
        return frontPlanId;
    }

    /**
     * 设置巡店安排id
     *
     * @param frontPlanId 巡店安排id
     */
    public void setFrontPlanId(Long frontPlanId) {
        this.frontPlanId = frontPlanId;
    }

    /**
     * 获取表单表id
     *
     * @return form_main_id - 表单表id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单表id
     *
     * @param formMainId 表单表id
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}