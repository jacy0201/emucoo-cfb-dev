package com.emucoo.manager.config.redis;


import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.emucoo.common.util.StringUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis缓存工具类
 * @author
 *
 */
@Component
public class RedisCachedUtil {
	
	private static final Logger LOGGER = Logger.getLogger(RedisCachedUtil.class);

	
	private static JedisCluster jedisCluster;

    public static JedisCluster getJedisCluster(String code,int maxTotal,int maxIdle,int timeout) {
        try
        {
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            String[] hostAndPortArray = code.split(",");
            for (String hostAndPort : hostAndPortArray)
            {
                String[] str = hostAndPort.split(":");
                jedisClusterNodes.add(new HostAndPort(str[0], Integer.parseInt(str[1])));
            }

            // 配置jedisPools
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(maxTotal);
            jedisPoolConfig.setMaxIdle(maxIdle);

            jedisCluster = new JedisCluster(jedisClusterNodes, timeout, jedisPoolConfig);
            LOGGER.info("Redis连接主机:" + jedisClusterNodes);
        }
        catch(Exception e) {
            LOGGER.error(e.toString());
        }

        return jedisCluster;
    }
    
    /*public static String set(String key, Object value) {
		String jsonObject = JSON.toJSONString(value);
        return jedisCluster.set(key, jsonObject);
	}*/
    
    /**
	 * 设置缓存
	 * @param key 缓存的键
	 * @param value 缓存的值
	 * @param expire 超时时间
	 * @return
	 */
	public static String set(String key, Object value, int expire) {
		String jsonObject = JSON.toJSONString(value);
        return jedisCluster.setex(key, expire, jsonObject);
	}
	
	 /**
     * 
     * 获取redis里的json对象，转换为对象
     * 
     * @param key
     * @return Object
     * @since p2p_cloud_v1.0
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        String jsonObject = jedisCluster.get(key);
        if (StringUtil.isBlank(jsonObject)) {
            return null;
        }
        return JSON.parseObject(jsonObject, clazz);
    }
    
    /**
     * 删除指定的key对应的缓存
     * @param key
     */
    public static void delete(String key){
    	if(StringUtil.isBlank(key)){
    		return;
    	}
    	jedisCluster.del(key);    	
    }

}
