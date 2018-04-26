package com.emucoo.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xxl.job.core.executor.XxlJobExecutor;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class XxlJobConfig {

	    @Value("${xxl.job.admin.addresses}")
	    private String adminAddresses;

	    @Value("${xxl.job.executor.appname}")
	    private String appName;

	    @Value("${xxl.job.executor.ip}")
	    private String ip;

	    @Value("${xxl.job.executor.port}")
	    private int port;

	    @Value("${xxl.job.accessToken}")
	    private String accessToken;

	    @Value("${xxl.job.executor.logpath}")
	    private String logPath;

	    @Value("${xxl.job.executor.logretentiondays}")
	    private int logRetentionDays;


	    @Bean(initMethod = "start", destroyMethod = "destroy")
	    public XxlJobExecutor initExecutor() {
	        log.info(">>>>>>>>>>> xxl-job config init.");
	        XxlJobExecutor executor = new XxlJobExecutor();
	        executor.setAdminAddresses(adminAddresses);
	        executor.setAppName(appName);
	        executor.setIp(ip);
	        executor.setPort(port);
	        executor.setAccessToken(accessToken);
	        executor.setLogPath(logPath);
            executor.setLogRetentionDays(logRetentionDays);
	        
	        return executor;
	    }
}
