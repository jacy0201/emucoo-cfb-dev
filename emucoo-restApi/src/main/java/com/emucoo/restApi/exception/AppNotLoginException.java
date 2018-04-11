package com.emucoo.restApi.exception;

public class AppNotLoginException extends AppException {
	
	private static final int NOT_LOGIN_CODE = 300;

//	private ApiNotLoginException(int code, String msg) {
//		super(code, msg);
//	}
//	private ApiNotLoginException(String msg) {
//		super(NOT_LOGIN_CODE, msg);
//	}
	public AppNotLoginException()
	{
		super(NOT_LOGIN_CODE, "用户未登录！");
	}

	/**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	
	private static final long serialVersionUID = 1L;

}
