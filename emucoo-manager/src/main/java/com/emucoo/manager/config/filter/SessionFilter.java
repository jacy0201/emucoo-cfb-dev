package com.emucoo.manager.config.filter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emucoo.manager.config.session.HttpServletRequestWrapper;
import com.emucoo.common.CommonConstant;
import com.emucoo.common.util.CookieUtil;
import com.emucoo.common.util.StringUtil;

/**
 * Created by fujg on 2017/9/21.
 */
public class SessionFilter extends OncePerRequestFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SessionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //从cookie中获取sessionId，如果此次请求没有sessionId，重写为这次请求设置一个sessionId
       // String sid = CookieUtil.getCookieValue(request, GlobalConstant.JSESSIONID);
        String sid = CookieUtil.getCookieValue(request, CommonConstant.JSESSIONID);
        if(StringUtils.isEmpty(sid) || sid.length() != 36){
            sid = StringUtil.getUuid();
           // CookieUtil.setCookie(request, response, GlobalConstant.JSESSIONID, sid, 60 * 60);
            CookieUtil.setCookie(request, response, CommonConstant.JSESSIONID, sid, 60 * 60);
        }

        //交给自定义的HttpServletRequestWrapper处理
        filterChain.doFilter(new HttpServletRequestWrapper(sid, request, response), response);
    }

}
