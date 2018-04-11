package com.emucoo.restApi.exception;

public class AppReSubmitException extends AppException {
	
	/**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	
	private static final long serialVersionUID = 1L;
	
	private static final int RE_SUBMIT_CODE = 600;

//	private ApiReSubmitException(int code, String msg) {
//		super(code, msg);
//	}
//	private ApiReSubmitException(String msg) {
//		super(NOT_LOGIN_CODE, msg);
//	}
	public AppReSubmitException()
	{
		super(RE_SUBMIT_CODE, "重复提交异常！");
	}

	/**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	

}
