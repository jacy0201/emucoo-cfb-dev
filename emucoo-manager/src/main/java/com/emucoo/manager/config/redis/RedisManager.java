package com.emucoo.manager.config.redis;

import java.util.Set;

import redis.clients.jedis.JedisCluster;

public class RedisManager {
	
	
    public String nodes = "192.168.199.109:7000,192.168.199.110:7001";

    public int maxIdel = 8;

    public int maxTotal = 50;

    public int timeout = 0;
    
    public JedisCluster jedisCluster = null;
	
	public RedisManager(){
		
	}
	
	
	public void init(){
		jedisCluster = RedisCachedUtil.getJedisCluster(nodes,maxTotal,maxIdel,timeout);
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		byte[] value = null;
		value = jedisCluster.get(key);
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		jedisCluster.set(key, value);
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		jedisCluster.set(key, value);
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		jedisCluster.del(key);
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		jedisCluster.flushDB();
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;
		dbSize = jedisCluster.dbSize();
		return dbSize;
	}

	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		keys = jedisCluster.hkeys(pattern.getBytes());
		return keys;
	}
	
	

	public String getNodes() {
		return nodes;
	}


	public void setNodes(String nodes) {
		this.nodes = nodes;
	}


	public int getMaxIdel() {
		return maxIdel;
	}

	public void setMaxIdel(int maxIdel) {
		this.maxIdel = maxIdel;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	
	
}
