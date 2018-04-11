package com.emucoo.restApi.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cuiP
 * Created by JK on 2017/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException{

    /**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	
	private static final long serialVersionUID = 1L;
	private int code = 500;
    private String msg = "";
    
    public AppException(Throwable e) {
    	super(e);
    }
    public AppException() {
//        super(msgs);
//        this.msg = msgs;
    }

    public AppException(String msgs) {
        super(msgs);
        this.msg = msgs;
    }

    public AppException(int code, String msgs) {
        super(msgs);
        this.code = code;
        this.msg = msgs;
    }

    public AppException(int code, String msgs, Throwable e) {
        super(msgs, e);
        this.code = code;
        this.msg = msgs;
    }
}
