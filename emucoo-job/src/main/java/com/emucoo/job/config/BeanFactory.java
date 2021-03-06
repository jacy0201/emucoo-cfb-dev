package com.emucoo.job.config;

import com.emucoo.common.msg.JiGuangProxy;
import com.emucoo.common.util.MsgPushing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BeanFactory {

    @Autowired
    private Environment env;

    @Bean
    public MsgPushing msgPushing() {
        return new MsgPushing(env);
    }

    @Bean
    public JiGuangProxy createJiGuangProxy() {
        return JiGuangProxy.createJiGuangProxyInstance(env.getProperty("jiguangPush.appKey"), env.getProperty("jiguangPush.masterSecret"), env.getProperty("jiguangPush.apnsProduction", Boolean.class));
    }
}
