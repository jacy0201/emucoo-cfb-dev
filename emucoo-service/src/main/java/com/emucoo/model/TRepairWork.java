package com.emucoo.model;

import com.emucoo.dto.modules.task.ImageUrl;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_repair_work")
public class TRepairWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "reporter_name")
    private String reporterName;

    @Column(name = "report_date")
    private Date reportDate;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_idstr")
    private String deviceIdstr;

    @Column(name = "device_title")
    private String deviceTitle;

    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "problem_name")
    private String problemName;

    @Column(name = "urgent_level")
    private Integer urgentLevel;

    @Column(name = "problem_imgs")
    private String problemImgs;

    @Column(name = "problem_notes")
    private String problemNotes;

    @Column(name = "repair_man_id")
    private Long repairManId;

    @Column(name = "repair_man_name")
    private String repairManName;

    @Column(name = "expect_date")
    private Date expectDate;

    @Column(name = "finish_date")
    private Date finishDate;

    @Column(name = "repair_notes")
    private String repairNotes;

    @Column(name = "repair_imgs")
    private String repairImgs;

    @Column(name = "review_notes")
    private String reviewNotes;

    @Column(name = "review_result")
    private Integer reviewResult;

    @Column(name = "work_status")
    private Integer workStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;


    ////////////////////////////
    //      辅助字段
    ////////////////////////////
    @Transient
    private String repairManAvatar;

    @Transient
    private long reportTime = 0;

    @Transient
    private long expectTime = 0;

    @Transient
    private long finishTime = 0;

    @Transient
    private List<ImageUrl> troubleImages;

    @Transient
    private List<ImageUrl> repaireImages;

    @Transient
    private String reportDateStr;

    @Transient
    private String expectRepairDateStr;

    @Transient
    private String finishRepairDateStr;


    /////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId){
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIdstr() {
        return deviceIdstr;
    }

    public void setDeviceIdstr(String deviceIdstr) {
        this.deviceIdstr = deviceIdstr;
    }

    public String getDeviceTitle() {
        return deviceTitle;
    }

    public void setDeviceTitle(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Integer getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(Integer urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getProblemImgs() {
        return problemImgs;
    }

    public void setProblemImgs(String problemImgs) {
        this.problemImgs = problemImgs;
    }

    public Long getRepairManId() {
        return repairManId;
    }

    public void setRepairManId(Long repairManId) {
        this.repairManId = repairManId;
    }

    public String getRepairManName() {
        return repairManName;
    }

    public void setRepairManName(String repairManName) {
        this.repairManName = repairManName;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getRepairNotes() {
        return repairNotes;
    }

    public void setRepairNotes(String repairNotes) {
        this.repairNotes = repairNotes;
    }

    public String getRepairImgs() {
        return repairImgs;
    }

    public void setRepairImgs(String repairImgs) {
        this.repairImgs = repairImgs;
    }

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public List<ImageUrl> getTroubleImages() {
        return troubleImages;
    }

    public void setTroubleImages(List<ImageUrl> troubleImages) {
        this.troubleImages = troubleImages;
    }

    public List<ImageUrl> getRepaireImages() {
        return repaireImages;
    }

    public void setRepaireImages(List<ImageUrl> repaireImages) {
        this.repaireImages = repaireImages;
    }

    public String getReportDateStr() {
        return reportDateStr;
    }

    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }

    public String getExpectRepairDateStr() {
        return expectRepairDateStr;
    }

    public void setExpectRepairDateStr(String expectRepairDateStr) {
        this.expectRepairDateStr = expectRepairDateStr;
    }

    public String getFinishRepairDateStr() {
        return finishRepairDateStr;
    }

    public void setFinishRepairDateStr(String finishRepairDateStr) {
        this.finishRepairDateStr = finishRepairDateStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getProblemNotes() {
        return problemNotes;
    }

    public void setProblemNotes(String problemNotes) {
        this.problemNotes = problemNotes;
    }

    public String getRepairManAvatar() {
        return repairManAvatar;
    }

    public void setRepairManAvatar(String repairManAvatar) {
        this.repairManAvatar = repairManAvatar;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public long getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(long expectTime) {
        this.expectTime = expectTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
