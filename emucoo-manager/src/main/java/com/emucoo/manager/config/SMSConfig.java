package com.emucoo.manager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sms")
public class SMSConfig {

	private boolean send;

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}
	
	
}
