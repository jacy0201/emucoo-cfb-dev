package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_from_report")
public class TFromReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表单配置主id
     */
    @Column(name = "form_main_id")
    private String formMainId;

    /**
     * 表单配置纪录id
     */
    @Column(name = "from_config_id")
    private Long fromConfigId;

    /**
     * 表单配置项客户端所填值
     */
    @Column(name = "form_config_value")
    private String formConfigValue;

    /**
     * 表单报告id
     */
    @Column(name = "form_report_id")
    private String formReportId;

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
     * 获取表单配置主id
     *
     * @return form_main_id - 表单配置主id
     */
    public String getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单配置主id
     *
     * @param formMainId 表单配置主id
     */
    public void setFormMainId(String formMainId) {
        this.formMainId = formMainId;
    }

    /**
     * 获取表单配置纪录id
     *
     * @return from_config_id - 表单配置纪录id
     */
    public Long getFromConfigId() {
        return fromConfigId;
    }

    /**
     * 设置表单配置纪录id
     *
     * @param fromConfigId 表单配置纪录id
     */
    public void setFromConfigId(Long fromConfigId) {
        this.fromConfigId = fromConfigId;
    }

    /**
     * 获取表单配置项客户端所填值
     *
     * @return form_config_value - 表单配置项客户端所填值
     */
    public String getFormConfigValue() {
        return formConfigValue;
    }

    /**
     * 设置表单配置项客户端所填值
     *
     * @param formConfigValue 表单配置项客户端所填值
     */
    public void setFormConfigValue(String formConfigValue) {
        this.formConfigValue = formConfigValue;
    }

    /**
     * 获取表单报告id
     *
     * @return form_report_id - 表单报告id
     */
    public String getFormReportId() {
        return formReportId;
    }

    /**
     * 设置表单报告id
     *
     * @param formReportId 表单报告id
     */
    public void setFormReportId(String formReportId) {
        this.formReportId = formReportId;
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
}