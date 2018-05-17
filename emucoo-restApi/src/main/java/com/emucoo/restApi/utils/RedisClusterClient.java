package com.emucoo.restApi.utils;

import com.alibaba.fastjson.JSON;
import com.emucoo.common.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Component
public class RedisClusterClient {
    private static final Logger logger = Logger.getLogger(RedisClusterClient.class);

    @Autowired
    private JedisCluster jedisCluster;

    public String set(String key, Object value, int expire) {
        String jsonObject = JSON.toJSONString(value);
        return jedisCluster.setex(key, expire, jsonObject);
    }

    public String buffer(String key, Object value) {
        String jsonObj = JSON.toJSONString(value);
        return jedisCluster.set(key, jsonObj);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String jsonObject = jedisCluster.get(key);
        return StringUtil.isBlank(jsonObject) ? null : JSON.parseObject(jsonObject, clazz);
    }

    public  void delete(String key) {
        if (!StringUtil.isBlank(key)) {
            jedisCluster.del(key);
        }
    }

    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     * @param key
     * @param value String
     * @return 返回List的长度
     */
    public  Long lpush(String key, String value){
        Long length =jedisCluster.lpush(key,value);
        return length;
    }

    /**
     * 获取List列表
     * @param key
     * @param start long，开始索引
     * @param end long， 结束索引
     * @return List<String>
     */
    public  List<String> lrange(String key, long start, long end){
        List<String> list = jedisCluster.lrange(key, start, end);
        return list;
    }
    
}
