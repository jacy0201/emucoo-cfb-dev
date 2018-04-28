package com.emucoo.manager.config;


import cn.jpush.api.JPushClient;
import com.emucoo.manager.component.rong.RongCloud;
import com.xiaomi.xmpush.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BeanFactory {
    @Autowired
    private RedisConnectionFactory factory;
    @Autowired
    private RongcloudConfig rongcloudConfig;
    @Autowired
    private QiNiuConfig qiNiuConfig;
    @Autowired
    private JiguangConfig jiguangConfig;
    @Autowired
    private XiaomiPushConfig xiaomiConfig;

    @Bean("redisTemplate")
    public RedisTemplate<String,String> getRedisTemplate()  {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("rongcloud")
    public RongCloud getRongcloud() {
        return RongCloud.getInstance(rongcloudConfig.getAppKey(), rongcloudConfig.getAppSecret());
    }

//    @Bean("qiniuAuth")
//    public Auth getQiniuAuth() {
//        return Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
//    }

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
