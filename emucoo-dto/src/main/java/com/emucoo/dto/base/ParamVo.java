package com.emucoo.dto.base;

import com.emucoo.common.PageInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年8月8日 下午7:25:34
 *
 */
@ApiModel
public class ParamVo<E> {
	
	@ApiModelProperty(name="version", value="版本号", example="1")
	protected Long version;
	
	@ApiModelProperty(name="cmd",value="命令", example="1")
	protected String cmd;
	
	@ApiModelProperty(name="pageType",value="机器类型", example="1")
	protected String pageType;
	
	@ApiModelProperty(name="respCode",value="返回代码", example="000")
	protected String respCode;
	
	/*@ApiModelProperty(name="reSubmitToken",value="防重复提交token", example="")
	protected String reSubmitToken;*/
	
	@ApiModelProperty(name="respMsg",value="返回描述", example="请求成功！")
	protected String respMsg;
	
	@ApiModelProperty(name="pageSize",value="每页数据条数", example="10")
	protected Long pageSize = 10l;
	
	@ApiModelProperty(name="pageNumber",value="当前页数", example="1")
	protected Long pageNumber = 1l;
	
	@ApiModelProperty(name="sign",value="签名", example="@#42334i！@34")
	protected String sign;
	
	@ApiModelProperty(name="data",value="数据")
	protected E data;
	
/*	public String getReSubmitToken() {
		return reSubmitToken;
	}
	public void setReSubmitToken(String reSubmitToken) {
		this.reSubmitToken = reSubmitToken;
	}*/
	
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public Long getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public E getData() {
		return data;
	}
	@SuppressWarnings("unchecked")
	public void setData(E data) {
		if(data instanceof PageInfo)
		{
			PageInfo pageInfo = (PageInfo)data;
			this.pageSize = pageInfo.getPageSize();
			this.pageNumber = pageInfo.getPageNumber();
			this.data = (E)pageInfo.getData();
		}
		else
		{
			this.data = data;
		}
	}
	
	public PageInfo findPageInfo() {
		PageInfo info = new PageInfo();
		info.setPageSize(pageSize);
		info.setPageNumber(pageNumber);
		return info;
	}
}
