package com.emucoo.restApi.sdk.token;

import com.emucoo.common.util.StringUtil;
import com.emucoo.common.util.UuidUtil;
import com.emucoo.dto.base.ISystem;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.utils.RedisClusterClient;
import com.emucoo.restApi.utils.SpringContextUtil;
import com.emucoo.service.sys.UserService;
import com.emucoo.utils.DESUtil;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年8月14日 下午4:47:18
 *
 */
public class UserTokenManager {
	
    private UserService userService;
	
	
	private static UserTokenManager instance;
	
	 // 算法名称
    public static final String KEY = "DES/ECB/PKCS5Padding";
	
//	private Map<String,String> cache = new HashMap<String,String>();
	private Map<String,String> userCache = new ConcurrentHashMap<String,String>();

	// 这个类这样用有问题，既然是个单例，又用了spring，就不要用这种方式来实现单例了。
	public synchronized static UserTokenManager getInstance()
	{
//		synchronized (instance)
//		{
			if(null == instance)
			{
				instance = new UserTokenManager();
			}
//		}

		return instance;
	}


	/*public String saveUserToken(String userToken){
        return RedisCachedUtil.set(ISystem.IUSER.USER_TOKEN,userToken,ISystem.IUSER.USER_TOKEN_EXPIRATION_TIME);
    }*/

    public boolean validateToken(String submitToken) {
        Long v = redisClient.getObject("submit:token:"+submitToken, Long.class);
        if(v.longValue() == 20180531){
            return true;
        } else {
            return false;
        }
    }

	public String produceReSubmitToken() {
        String key = UuidUtil.creatUUID();
        redisClient.set("submit:token:" + key, new Long(20180531), 1000*60*30);
        return key;
	}

	public void clearReSubmitToken(String key) {
	    redisClient.delete("submit:token:" + key);
    }

	private RedisClusterClient redisClient = SpringContextUtil.getApplicationContext().getBean(RedisClusterClient.class);

    public String saveUserToken(Long userId){
        String userToken = null;
        try {
            userToken = DESUtil.encryptStr(String.valueOf(userId.longValue()), KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisClient.set(ISystem.IUSER.USER_TOKEN + userId,userToken,ISystem.IUSER.USER_TOKEN_EXPIRATION_TIME);
        return userToken;
    }

	/*public String userToken() {

		String userToken = RedisCachedUtil.getObject(ISystem.IUSER.USER_TOKEN, String.class);

		if (StringUtil.isBlank(userToken)) {
			return "";
		}

//		try {
//			return DESUtil.encryptStr(userID, KEY);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
		return userToken;
	}*/

	public Long getUserIdFromToken(String userToken){
		try {
			String idStr =  DESUtil.decryptStr(userToken, KEY);
			return new Long(idStr);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
	}

/*	public User currUser() {

		String token = userToken();
		User user = null;
		if (StringUtil.isBlank(token)) {
			return user;
		}
		String userID = "";
		try {
			userID = DESUtil.decryptStr(token, KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		}

		 user = RedisCachedUtil.getObject(ISystem.IUSER.USER + userID, User.class);
		if ( user == null) {
			// 获取Service
			ApplicationContext context = SpringContextUtil.getApplicationContext();
			userService = context.getBean(UserService.class);

			user = userService.findById(Long.valueOf(userID));
			if ( user == null) {
				return user;
			}
			RedisCachedUtil.set(ISystem.IUSER.USER + userID, user, ISystem.IUSER.USER_TOKEN_EXPIRATION_TIME);
		}

		return user;

    }*/

	public SysUser currUser(String userToken) {

		String token = userToken;
		SysUser user = null;
		if (StringUtil.isBlank(token)) {
			return user;
		}
		String userID = "";
		try {
			userID = DESUtil.decryptStr(token, KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		}

		 user = redisClient.getObject(ISystem.IUSER.USER + userID, SysUser.class);
		if ( user == null) {
			// 获取Service
			ApplicationContext context = SpringContextUtil.getApplicationContext();
			userService = context.getBean(UserService.class);

			user = userService.findById(Long.valueOf(userID));
			if ( user == null) {
				return user;
			}
			redisClient.set(ISystem.IUSER.USER + userID, user, ISystem.IUSER.USER_TOKEN_EXPIRATION_TIME);
		}

		return user;

    }

}
