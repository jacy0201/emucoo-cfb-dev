package com.emucoo.model;

import javax.persistence.*;

@Table(name = "t_device_problem")
public class TDeviceProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "device_type_id")
    private long deviceTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_del")
    private boolean isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
