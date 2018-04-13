package com.emucoo.common.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 基础信息
 *
 */
@Data
public abstract class BaseEntity implements java.io.Serializable{

}
