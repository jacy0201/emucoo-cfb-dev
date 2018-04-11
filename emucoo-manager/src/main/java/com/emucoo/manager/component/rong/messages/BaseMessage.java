package com.emucoo.manager.component.rong.messages;


//消息基类，如实现自定义消息，可继承此类
public abstract class BaseMessage {
	
	protected UserInfo user;

	public abstract String getType();

	public abstract String toString();

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	
}
