package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
public class TRemind extends BaseEntity {

    private Integer remindType;

    private String remindName;

}
