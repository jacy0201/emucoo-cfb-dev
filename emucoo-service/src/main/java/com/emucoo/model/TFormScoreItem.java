package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_score_item")
public class TFormScoreItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * 分数起始范围
     */
    @Column(name = "score_begin")
    private Integer scoreBegin;

    /**
     * 分数结束范围
     */
    @Column(name = "score_end")
    private Integer scoreEnd;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 表单主id
     */
    @Column(name = "form_main_id")
    private Long formMainId;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取分数起始范围
     *
     * @return score_begin - 分数起始范围
     */
    public Integer getScoreBegin() {
        return scoreBegin;
    }

    /**
     * 设置分数起始范围
     *
     * @param scoreBegin 分数起始范围
     */
    public void setScoreBegin(Integer scoreBegin) {
        this.scoreBegin = scoreBegin;
    }

    /**
     * 获取分数结束范围
     *
     * @return score_end - 分数结束范围
     */
    public Integer getScoreEnd() {
        return scoreEnd;
    }

    /**
     * 设置分数结束范围
     *
     * @param scoreEnd 分数结束范围
     */
    public void setScoreEnd(Integer scoreEnd) {
        this.scoreEnd = scoreEnd;
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
     * 获取表单主id
     *
     * @return form_main_id - 表单主id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单主id
     *
     * @param formMainId 表单主id
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }
}