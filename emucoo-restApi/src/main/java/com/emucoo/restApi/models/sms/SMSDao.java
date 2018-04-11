package com.emucoo.restApi.models.sms;

import com.emucoo.restApi.utils.RedisClusterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emucoo.dto.base.ISystem;
@Repository
public class SMSDao {

	@Autowired
	private RedisClusterClient redisClient;

	public void saveVcode(String mobile, String vcode, int template) {
//		StringRedisTemplate redisTemplate = RedisResource.getStringRedisTemplate();
//		redisTemplate.boundValueOps(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY + mobile).set(vcode, ISMSTemplateCode.EXPIRATION_TIME, TimeUnit.SECONDS);
//		redisTemplate.boundValueOps(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile).increment(1);
//		redisTemplate.expire(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile, ISMSTemplateCode.EXPIRATION_TIME,
//				TimeUnit.SECONDS);
//		String count = RedisCachedUtil.getObject(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile, String.class);
		String count = redisClient.getObject(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile, String.class);
		if (count == null) {
			count = "1";
		} else {
			count = (Integer.parseInt(count) + 1) + "";
		}

		redisClient.set(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY + mobile, vcode, ISystem.ISMSTemplateCode.EXPIRATION_TIME);
		redisClient.set(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile, count, ISystem.ISMSTemplateCode.EXPIRATION_TIME);
	}
	
	public int getSendCount(String mobile,int template) {
//		StringRedisTemplate redisTemplate = RedisResource.getStringRedisTemplate();
//		String count = redisTemplate.boundValueOps(ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile).get();
		String count = redisClient.getObject(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY_COUNT + template + ":" + mobile, String.class);
		if (count == null) {
			return 0;
		}
		return Integer.parseInt(count);
		
	}
	
	public String getVcode(String mobile) {
//		StringRedisTemplate redisTemplate = RedisResource.getStringRedisTemplate();
		String vcode = redisClient.getObject(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY + mobile, String.class);
		return vcode;
	}
	
	public void delVcode(String mobile) {
//		StringRedisTemplate redisTemplate = RedisResource.getStringRedisTemplate();
//		redisTemplate.delete(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY + mobile);
		redisClient.delete(ISystem.ISMSTemplateCode.SMS_VCODE_STORE_KEY + mobile);
	}

}
