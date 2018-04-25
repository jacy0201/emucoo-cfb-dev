package com.emucoo.manager.utils;

import com.alibaba.fastjson.JSON;
import com.emucoo.common.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisClusterClient {
    private static final Logger logger = Logger.getLogger(RedisClusterClient.class);

    @Autowired
    private JedisCluster jedisCluster;

    public String set(String key, Object value, int expire) {
        String jsonObject = JSON.toJSONString(value);
        logger.info("放入redis 缓存key："+key+". velue:"+jsonObject);
        return jedisCluster.setex(key, expire, jsonObject);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String jsonObject = jedisCluster.get(key);
        return StringUtil.isBlank(jsonObject) ? null : JSON.parseObject(jsonObject, clazz);
    }

    public  void delete(String key) {
        if (!StringUtil.isBlank(key)) {
            jedisCluster.del(key);
            logger.info("删除redis 缓存key："+key);
        }
    }

}
