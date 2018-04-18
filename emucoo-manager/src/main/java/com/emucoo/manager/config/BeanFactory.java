package com.emucoo.manager.config;


import cn.jpush.api.JPushClient;
import com.emucoo.manager.component.rong.RongCloud;
import com.emucoo.manager.config.QiNiuConfig;
import com.emucoo.manager.config.RongcloudConfig;
import com.qiniu.util.Auth;
import com.xiaomi.xmpush.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Autowired
    private RongcloudConfig rongcloudConfig;
    @Autowired
    private QiNiuConfig qiNiuConfig;
    @Autowired
    private JiguangConfig jiguangConfig;
    @Autowired
    private XiaomiPushConfig xiaomiConfig;


    @Bean("rongcloud")
    public RongCloud getRongcloud() {
        return RongCloud.getInstance(rongcloudConfig.getAppKey(), rongcloudConfig.getAppSecret());
    }

    @Bean("qiniuAuth")
    public Auth getQiniuAuth() {
        return Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
    }
    @Bean("jpushClient")
    public JPushClient getJPushClinet() {
    	JPushClient jPushClient = new JPushClient(jiguangConfig.getMasterSecret(), jiguangConfig.getAppKey());
    	return jPushClient;
    }
    @Bean("xiaomiSend")
    public Sender getXiaomiSend() {
    	Sender sender = new Sender(xiaomiConfig.getAppSecret());
    	return sender;
    }
}
