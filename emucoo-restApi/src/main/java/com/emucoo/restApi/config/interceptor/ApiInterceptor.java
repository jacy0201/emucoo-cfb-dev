package com.emucoo.restApi.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.emucoo.dto.base.ISystem;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.restApi.utils.RedisClusterClient;
import com.emucoo.restApi.utils.SpringContextUtil;
import com.emucoo.utils.DESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

import static com.emucoo.restApi.sdk.token.UserTokenManager.KEY;

/**
 * @author fujg
 */

@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

	private RedisClusterClient redisClient = SpringContextUtil.getApplicationContext().getBean(RedisClusterClient.class);

	/**
     * (non-Javadoc)
     * @see HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
	{
    	String userAgent = request.getHeader("user-agent");
    	String ip = this.getIpAddr(request);
    	if(null!=userAgent)
    	{
    		if(userAgent.contains("Android")) 
        	{
        		log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的Android移动客户端请求URL[" + request.getRequestURI() + "]");
        	} else if(userAgent.contains("iPhone")) 
        	{
        		log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的iPhone移动客户端请求URL[" + request.getRequestURI() + "]");
        	}  else if(userAgent.contains("iPad")) 
        	{
        		log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的iPad客户端请求URL[" + request.getRequestURI() + "]");
        	}  else 
        	{
        		log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的其他客户端请求URL[" + request.getRequestURI() + "]");
        	}
			   log.info("<<<<<<<<<<userToken:"+request.getHeader("userToken")+"");
    	}
		     AppResult r = new AppResult();
		try {
			String userToken = request.getHeader("userToken");
			if(StringUtils.isBlank(userToken)) {
				r.setRespCode("401");
				r.setRespMsg("invalid token");
				response.getWriter().write(JSON.toJSONString(r));
				return false;
			}
			Long userID =Long.parseLong(DESUtil.decryptStr(userToken, KEY));
			if(!redisClient.existsKey(ISystem.IUSER.USER_TOKEN + userID)){
				r.setRespCode("401");
				r.setRespMsg("invalid token");
				response.getWriter().write(JSON.toJSONString(r));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setRespCode("401");
			r.setRespMsg("invalid token");
			response.getWriter().write(JSON.toJSONString(r));
			return false;
		}
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
    public String getIpAddr(HttpServletRequest request) 
    {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
		{
			ip = request.getRemoteAddr();
		}
		return ip;
    }
    
}
