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

    @Column(name = "report_date")
    private Date reportDate;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_title")
    private String deviceTitle;

    @Column(name = "trouble_id")
    private Long troubleId;

    @Column(name = "trouble_name")
    private String troubleName;

    @Column(name = "urgent_level")
    private Integer urgentLevel;

    @Column(name = "trouble_notes")
    private String troubleNotes;

    @Column(name = "trouble_imgs")
    private String troubleImgs;

    @Column(name = "repair_man_id")
    private Long repairManId;

    @Column(name = "repair_man_name")
    private String repairManName;

    @Column(name = "expect_repair_date")
    private Date expectRepairDate;

    @Column(name = "finish_repair_date")
    private Date finishRepairDate;

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


    ////////////////////////////
    //      辅助字段
    ////////////////////////////
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

    public String getDeviceTitle() {
        return deviceTitle;
    }

    public void setDeviceTitle(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }

    public Long getTroubleId() {
        return troubleId;
    }

    public void setTroubleId(Long troubleId) {
        this.troubleId = troubleId;
    }

    public String getTroubleName() {
        return troubleName;
    }

    public void setTroubleName(String troubleName) {
        this.troubleName = troubleName;
    }

    public Integer getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(Integer urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getTroubleNotes() {
        return troubleNotes;
    }

    public void setTroubleNotes(String troubleNotes) {
        this.troubleNotes = troubleNotes;
    }

    public String getTroubleImgs() {
        return troubleImgs;
    }

    public void setTroubleImgs(String troubleImgs) {
        this.troubleImgs = troubleImgs;
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

    public Date getExpectRepairDate() {
        return expectRepairDate;
    }

    public void setExpectRepairDate(Date expectRepairDate) {
        this.expectRepairDate = expectRepairDate;
    }

    public Date getFinishRepairDate() {
        return finishRepairDate;
    }

    public void setFinishRepairDate(Date finishRepairDate) {
        this.finishRepairDate = finishRepairDate;
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
}
