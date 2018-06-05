package com.emucoo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_device_type")
public class TDeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "tier")
    private int tier;

    @Column(name = "parent_type_id")
    private long parentTypeId = 0;

    @Column(name = "parent_type_name")
    private String parentTypeName;

    @Column(name = "brand_ids")
    private String brandIds;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "is_use")
    private boolean isUse;

    @Column(name = "is_del")
    private boolean isDel;

    @Transient
    private long brandId;

    @Transient
    private List<TDeviceType> children;

    @Transient
    private List<TDeviceProblem> problems;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TDeviceType> getChildren() {
        return children;
    }

    public void setChildren(List<TDeviceType> children) {
        this.children = children;
    }

    public List<TDeviceProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<TDeviceProblem> problems) {
        this.problems = problems;
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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }
}
