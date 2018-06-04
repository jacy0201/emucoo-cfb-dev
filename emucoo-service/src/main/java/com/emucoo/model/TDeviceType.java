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

    @Column(name = "type_id")
    private long typeId = 0;

    @Column(name = "type_name")
    private String typeName;

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

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
