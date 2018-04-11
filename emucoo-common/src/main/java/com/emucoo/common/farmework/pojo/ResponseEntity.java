/**
 * Copyright (C), 2015-2018, 金恪投资控股
 * FileName: ResponseEntity.java
 * Author:   fujg
 * Date:     2017 2017年9月19日 下午5:25:59
 * History:  
 * <author>    <time>    <version>   <desc>
 * 修改人姓名  修改时间  版本号      描述
 *
 */
package com.emucoo.common.farmework.pojo;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg(可选)
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年9月19日 下午5:25:59
 *
 */
public class ResponseEntity<T> implements Serializable {

	//默认返回成功
	private String code = "0000";
	private String msg;
	private T data;
	private Integer count;

	public ResponseEntity()
	{
	}

	public ResponseEntity(T data) {
		this.data = data;
	}

	public ResponseEntity(T data, int count) {
		this.data = data;
		this.count = Integer.valueOf(count);
	}

	public ResponseEntity(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public ResponseEntity<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return this.data;
	}

	public ResponseEntity<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = Integer.valueOf(count);
	}

	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String toString() {
		return "ResponseEntity(super=" + super.toString() + ", msg=" + getMsg() + ", data=" + getData() + ", count=" + getCount() + ", code=" + getCode() + ")";
	}
	
}
