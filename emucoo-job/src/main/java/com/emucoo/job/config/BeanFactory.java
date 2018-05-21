package com.emucoo.job.config;

import com.emucoo.common.util.MsgPushTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Bean
    public MsgPushTool msgPushTool() {
        return new MsgPushTool();
    }
}
