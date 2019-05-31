package com.netzoom.servicezuul.apimanager.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
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
import java.util.*;

/**
 * 最高优先级预认证过滤器
 * 所有请求均经过预认证，抽取serviceId与Sign进行ss认证
 * 通过后以当前session继续执行过滤器链
 * 否则deny
 *
 * @author tanzj
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreAuthenticationFilter implements Filter {
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/login", "/logout", "/actuator", "/v1", "/api", "/adminLogin")));
	final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 登录认证过滤器
	 */
	@Autowired
	LoginAuthenticationFilter loginAuthenticationFilter;
	@Autowired
	PermissionDAO permissionDAO;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		logger.debug("请求URI为:" + ((HttpServletRequest) servletRequest).getRequestURI());
		String uri = ((HttpServletRequest) servletRequest).getRequestURI();
		String target;
		//单'/'为adminLogin地址，多'/'为mvc请求与路由请求地址
		if (uri.split("/").length - 1 > 1) {
			target = uri.substring(0, uri.indexOf("/", uri.indexOf("/") + 1));
		} else {
			target = uri;
		}
		//不执行认证的请求uri
		Boolean doSecurityLoginFilter = ALLOWED_PATHS.contains(target);
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
		if (doSecurityLoginFilter) {
			logger.debug("不执行PreAuthenticationFilter");
			filterChain.doFilter(requestWrapper, servletResponse);
		} else {
			try {
				servletRequest.setAttribute("requestData", new String(requestWrapper.getBody()));
				//ss认证
				loginAuthenticationFilter.attemptAuthentication((HttpServletRequest) requestWrapper, (HttpServletResponse) servletResponse);
				//判断接口是否存在
				Boolean isRequestUriExist = this.judgeRequestExist(uri,(HttpServletResponse)servletResponse);
				if (isRequestUriExist){
					filterChain.doFilter(requestWrapper, servletResponse);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("服务认证失败!");
				JSONObject responseData = new JSONObject();
				JSONObject responseBody = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("ServiceHttpCode", 401);
				responseBody.put("success", "false");
				responseBody.put("code", "199999");
				responseBody.put("msg", "服务认证失败！");
				responseBody.put("data", data);
				responseData.put("body", responseBody);
				servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				servletResponse.setCharacterEncoding("utf-8");
				((HttpServletResponse) servletResponse).setStatus(200);
				servletResponse.getWriter().write(new String(responseData.toJSONString().getBytes("utf-8")));
			}
		}
	}

	/**
	 * 判断请求资源是否存在数据库
	 * @param uri 所请求的资源
	 * @param response 响应
	 */
	public Boolean judgeRequestExist(String uri,HttpServletResponse response) throws Exception {
		Permission permission = new Permission();
		permission.setResource(uri);
		List<Permission> permissionList = permissionDAO.queryAllPermission();
		if (!permissionList.contains(permission)){
			logger.debug("接口资源不存在!");
			JSONObject responseData = new JSONObject();
			JSONObject responseBody = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("ServiceHttpCode", 404);

			responseBody.put("success", "false");
			responseBody.put("code", "199998");
			responseBody.put("msg", "接口资源不存在");
			responseBody.put("data", data);
			responseData.put("body", responseBody);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(200);
			response.getWriter().write(new String(responseData.toJSONString().getBytes("utf-8")));
			return false;
		}else {
			return true;
		}
	}



	@Override
	public void destroy() {

	}
}
