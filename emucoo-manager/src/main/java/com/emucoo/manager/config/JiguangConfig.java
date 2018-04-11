package com.emucoo.manager.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jiguangPush")
public class JiguangConfig {

    private String appKey;

    private String masterSecret;

    private boolean apnsProduction;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public boolean isApnsProduction() {
		return apnsProduction;
	}

	public void setApnsProduction(boolean apnsProduction) {
		this.apnsProduction = apnsProduction;
	}
}
