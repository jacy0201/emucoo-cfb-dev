package com.emucoo.manager.component.rong.messages;

public class UserInfo {

	private String id;
	private String name;
	private String icon;
	
	public UserInfo() {
		super();
	}
	public UserInfo(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
