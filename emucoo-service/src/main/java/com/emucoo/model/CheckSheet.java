package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

@Data
public class CheckSheet extends BaseEntity {

    private long id;
    private String name;
    private int sourceType;
    private String sourceName;

}
