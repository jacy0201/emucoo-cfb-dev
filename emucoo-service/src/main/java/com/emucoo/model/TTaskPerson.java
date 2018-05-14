package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "t_task_person")
public class TTaskPerson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "dpt_id")
    private Long dptId;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 1：选择部门 2：选择店铺
     */
    @Column(name = "exec_type")
    private Boolean execType;

    /**
     * 1:执行人 2：抄送人
     */
    @Column(name = "person_type")
    private Boolean personType;

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
     * @return task_id
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * @return dpt_id
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * @param dptId
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }

    /**
     * @return position_id
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return shop_id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * @param shopId
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取1：选择部门 2：选择店铺
     *
     * @return exec_type - 1：选择部门 2：选择店铺
     */
    public Boolean getExecType() {
        return execType;
    }

    /**
     * 设置1：选择部门 2：选择店铺
     *
     * @param execType 1：选择部门 2：选择店铺
     */
    public void setExecType(Boolean execType) {
        this.execType = execType;
    }

    /**
     * 获取1:执行人 2：抄送人
     *
     * @return person_type - 1:执行人 2：抄送人
     */
    public Boolean getPersonType() {
        return personType;
    }

    /**
     * 设置1:执行人 2：抄送人
     *
     * @param personType 1:执行人 2：抄送人
     */
    public void setPersonType(Boolean personType) {
        this.personType = personType;
    }
}