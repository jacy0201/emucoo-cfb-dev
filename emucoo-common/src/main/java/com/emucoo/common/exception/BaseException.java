package com.emucoo.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = -9191765277576504662L;
    private int code = 500;
    private String msg;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
}
