package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_report_oppt")
public class TReportOppt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "oppt_id")
    private Long opptId;

    @Column(name = "oppt_name")
    private String opptName;

    @Column(name = "form_oppt_value_id")
    private Long formOpptValueId;

    @Column(name = "oppt_desc")
    private String opptDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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
     * @return report_id
     */
    public Long getReportId() {
        return reportId;
    }

    /**
     * @param reportId
     */
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    /**
     * @return oppt_id
     */
    public Long getOpptId() {
        return opptId;
    }

    /**
     * @param opptId
     */
    public void setOpptId(Long opptId) {
        this.opptId = opptId;
    }

    public String getOpptName() {
        return opptName;
    }

    public void setOpptName(String opptName) {
        this.opptName = opptName;
    }

    public String getOpptDesc() {
        return opptDesc;
    }

    public void setOpptDesc(String opptDesc) {
        this.opptDesc = opptDesc;
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

    public Long getFormOpptValueId() {
        return formOpptValueId;
    }

    public void setFormOpptValueId(Long formOpptValueId) {
        this.formOpptValueId = formOpptValueId;
    }
}