package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_log")
public class SysLog extends BaseEntity {
    /**
     * 日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属应用
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 日志类型，0为操作日志，1为异常日志
     */
    @Column(name = "log_type")
    private Byte logType;

    /**
     * 日志来源：0 controller层；1service 层
     */
    @Column(name = "log_source")
    private Byte logSource;

    /**
     * 访问者/请求者
     */
    private String username;

    /**
     * 方法描述
     */
    private String operation;

    /**
     * 请求方法名称(全路径)
     */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 请求方式(GET,POST,DELETE,PUT)
     */
    @Column(name = "request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Column(name = "request_params")
    private String requestParams;

    /**
     * 访问者IP
     */
    @Column(name = "request_ip")
    private String requestIp;

    /**
     * 请求URI
     */
    @Column(name = "request_uri")
    private String requestUri;

    /**
     * 异常码
     */
    @Column(name = "exception_code")
    private String exceptionCode;

    /**
     * 异常描述
     */
    @Column(name = "exception_detail")
    private String exceptionDetail;

    /**
     * 请求耗时
     */
    @Column(name = "time_consuming")
    private Long timeConsuming;

    /**
     * 客户端信息
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 获取日志ID
     *
     * @return id - 日志ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置日志ID
     *
     * @param id 日志ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取所属应用
     *
     * @return app_name - 所属应用
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置所属应用
     *
     * @param appName 所属应用
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取日志类型，0为操作日志，1为异常日志
     *
     * @return log_type - 日志类型，0为操作日志，1为异常日志
     */
    public Byte getLogType() {
        return logType;
    }

    /**
     * 设置日志类型，0为操作日志，1为异常日志
     *
     * @param logType 日志类型，0为操作日志，1为异常日志
     */
    public void setLogType(Byte logType) {
        this.logType = logType;
    }

    /**
     * 获取日志来源：0 controller层；1service 层
     *
     * @return log_source - 日志来源：0 controller层；1service 层
     */
    public Byte getLogSource() {
        return logSource;
    }

    /**
     * 设置日志来源：0 controller层；1service 层
     *
     * @param logSource 日志来源：0 controller层；1service 层
     */
    public void setLogSource(Byte logSource) {
        this.logSource = logSource;
    }

    /**
     * 获取访问者/请求者
     *
     * @return username - 访问者/请求者
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置访问者/请求者
     *
     * @param username 访问者/请求者
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取方法描述
     *
     * @return operation - 方法描述
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置方法描述
     *
     * @param operation 方法描述
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取请求方法名称(全路径)
     *
     * @return method_name - 请求方法名称(全路径)
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置请求方法名称(全路径)
     *
     * @param methodName 请求方法名称(全路径)
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取请求方式(GET,POST,DELETE,PUT)
     *
     * @return request_method - 请求方式(GET,POST,DELETE,PUT)
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * 设置请求方式(GET,POST,DELETE,PUT)
     *
     * @param requestMethod 请求方式(GET,POST,DELETE,PUT)
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * 获取请求参数
     *
     * @return request_params - 请求参数
     */
    public String getRequestParams() {
        return requestParams;
    }

    /**
     * 设置请求参数
     *
     * @param requestParams 请求参数
     */
    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    /**
     * 获取访问者IP
     *
     * @return request_ip - 访问者IP
     */
    public String getRequestIp() {
        return requestIp;
    }

    /**
     * 设置访问者IP
     *
     * @param requestIp 访问者IP
     */
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    /**
     * 获取请求URI
     *
     * @return request_uri - 请求URI
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * 设置请求URI
     *
     * @param requestUri 请求URI
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * 获取异常码
     *
     * @return exception_code - 异常码
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * 设置异常码
     *
     * @param exceptionCode 异常码
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * 获取异常描述
     *
     * @return exception_detail - 异常描述
     */
    public String getExceptionDetail() {
        return exceptionDetail;
    }

    /**
     * 设置异常描述
     *
     * @param exceptionDetail 异常描述
     */
    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    /**
     * 获取请求耗时
     *
     * @return time_consuming - 请求耗时
     */
    public Long getTimeConsuming() {
        return timeConsuming;
    }

    /**
     * 设置请求耗时
     *
     * @param timeConsuming 请求耗时
     */
    public void setTimeConsuming(Long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    /**
     * 获取客户端信息
     *
     * @return user_agent - 客户端信息
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置客户端信息
     *
     * @param userAgent 客户端信息
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return modify_time - 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置更新时间
     *
     * @param modifyTime 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}