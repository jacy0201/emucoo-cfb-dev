package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_add_item_value")
public class TFormAddItemValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 补充项纪录值
     */
    private String value;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "form_main_id")
    private Long formMainId;

    @Column(name = "form_addition_item_id")
    private Long formAdditionItemId;

    /**
     * 报告id
     */
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "form_result_id")
    private Long formResultId;

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
     * 获取补充项纪录值
     *
     * @return value - 补充项纪录值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置补充项纪录值
     *
     * @param value 补充项纪录值
     */
    public void setValue(String value) {
        this.value = value;
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
     * @return form_main_id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * @param formMainId
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }

    /**
     * @return form_addition_item_id
     */
    public Long getFormAdditionItemId() {
        return formAdditionItemId;
    }

    /**
     * @param formAdditionItemId
     */
    public void setFormAdditionItemId(Long formAdditionItemId) {
        this.formAdditionItemId = formAdditionItemId;
    }

    /**
     * 获取报告id
     *
     * @return report_id - 报告id
     */
    public Long getReportId() {
        return reportId;
    }

    /**
     * 设置报告id
     *
     * @param reportId 报告id
     */
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getFormResultId() {
        return formResultId;
    }

    public void setFormResultId(Long formResultId) {
        this.formResultId = formResultId;
    }
}