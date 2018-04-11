package com.emucoo.restApi.exception;

import com.emucoo.common.ExecStatus;

/**
 * @package: com.jk.common.exception
 * @description: 业务逻辑异常
 * @author: cuiP
 * @date: 2017/8/10 19:06
 * @version: V1.0.0
 */
public class ServiceException extends BaseException{
    public ServiceException(String msg) {
        super(ExecStatus.FAIL.getCode(), msg);
    }

    public ServiceException(int code, String msg) {
        super(code, msg);
    }
}
