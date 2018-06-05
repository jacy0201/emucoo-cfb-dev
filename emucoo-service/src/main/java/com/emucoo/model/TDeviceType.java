package com.emucoo.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_device_type")
public class TDeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_type_id")
    private long parentTypeId = 0;

    @Column(name = "parent_type_name")
    private String parentTypeName;

    @Column(name = "brand_ids")
    private String brandIds;

    @Column(name = "description")
    private String description;

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
}
