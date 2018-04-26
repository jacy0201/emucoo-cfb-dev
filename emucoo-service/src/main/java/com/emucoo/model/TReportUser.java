package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "t_report_user")
public class TReportUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 报告id
     */
    @Column(name = "report_id")
    private Long reportId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否已读（0：未读，1：已读）
     */
    @Column(name = "is_read")
    private Boolean isRead;

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

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取是否已读（0：未读，1：已读）
     *
     * @return is_read - 是否已读（0：未读，1：已读）
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * 设置是否已读（0：未读，1：已读）
     *
     * @param isRead 是否已读（0：未读，1：已读）
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}