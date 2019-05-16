package com.netzoom.servicezuul.apimanager.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.util.ContentCachingRequestWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 最高优先级预认证过滤器
 * 所有请求均经过预认证，抽取serviceId与Sign进行ss认证
 * 通过后以当前session继续执行过滤器链
 * 否则deny
 * @author tanzj
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreAuthenticationFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/login", "/logout","/actuator","/v1","/api","/adminLogin")));
    final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 登录认证过滤器
     */
    @Autowired
    LoginAuthenticationFilter loginAuthenticationFilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("aaaaaaaaaaaaa:"+((HttpServletRequest)servletRequest).getRequestURI());
        String uri = ((HttpServletRequest)servletRequest).getRequestURI();
        String target;
        if(uri.split("/").length-1>1){
            target = uri.substring(0, uri.indexOf("/",uri.indexOf("/")+1 ));
        }else {
            target = uri;
        }

        Boolean doSecurityLoginFilter = ALLOWED_PATHS.contains(target);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper( (HttpServletRequest)servletRequest);
        if (doSecurityLoginFilter){
            logger.debug("不执行PreAuthenticationFilter");
            filterChain.doFilter(requestWrapper,servletResponse);
        }else{
            try {
                servletRequest.setAttribute("requestData",new String(requestWrapper.getBody()));
                loginAuthenticationFilter.attemptAuthentication((HttpServletRequest) requestWrapper,(HttpServletResponse) servletResponse);
                filterChain.doFilter(requestWrapper, servletResponse);
            }catch (Exception e){
                e.printStackTrace();
                logger.debug("服务认证失败!");
                JSONObject responseData = new JSONObject();
                JSONObject responseBody = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("ServiceHttpCode",401);
                responseBody.put("success","false");
                responseBody.put("code","199999");
                responseBody.put("msg","服务认证失败！");
                responseBody.put("data",data);
                responseData.put("body",responseBody);
                servletResponse.setCharacterEncoding("UTF-8");
                responseData.put("body",responseBody);
                servletResponse.setCharacterEncoding("utf-8");
                ((HttpServletResponse) servletResponse).setStatus(200);
                servletResponse.getWriter().write(new String(responseData.toJSONString().getBytes("utf-8")));
             }

        }
    }
    @Override
    public void destroy() {

    }
}
