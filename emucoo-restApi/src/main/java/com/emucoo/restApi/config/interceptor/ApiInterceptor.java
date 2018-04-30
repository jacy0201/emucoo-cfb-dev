package com.emucoo.restApi.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.emucoo.restApi.controller.demo.AppResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author fujg
 */

@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

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
    	}

		try {
			/*InputStream is = request.getInputStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			IOUtils.copy(is, os);
			String encoding = request.getCharacterEncoding() != null ? "utf-8" : request.getCharacterEncoding();
			System.out.println(os.toString(encoding));*/
			String userToken = request.getHeader("userToken");
			if(StringUtils.isBlank(userToken)) {
				AppResult r = new AppResult();
				r.setRespCode("500");
				r.setRespMsg("param error");
				response.getWriter().write(JSON.toJSONString(r));
				return false;
			}
		} catch (Exception e) {
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
