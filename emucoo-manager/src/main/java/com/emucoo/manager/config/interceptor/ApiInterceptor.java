package com.emucoo.manager.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    /**
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
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
