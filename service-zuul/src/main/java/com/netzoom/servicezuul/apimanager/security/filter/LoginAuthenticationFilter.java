package com.netzoom.servicezuul.apimanager.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.model.FailModel;
import com.netzoom.servicezuul.apimanager.model.SuccessModel;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.util.ContentCachingRequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 重写UsernamePassword过滤器认证方法
 * 使其支持json登录
 *
 * @author tanzj
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * 使用json登录，将request中的body转换为json
	 *
	 * @param request  http请求
	 * @param response http响应
	 * @return Authentication
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		//ContentCachingRequestWrapper requestWrapper;
		UsernamePasswordAuthenticationToken authRequest = null;
		BufferedReader streamReader = null;
		PrintWriter out = null;
		//解析request中的JSON并转换为User类对
		JSONObject jsonObject = JSON.parseObject(request.getAttribute("requestData").toString());
		JSONObject userJSON = (JSONObject) jsonObject.get("header");
		User user = JSONObject.toJavaObject(userJSON, User.class);
		//认证请求
		logger.debug("user为：" + JSON.toJSONString(user) + "password为：" + user.getPassword() + "userId为：" + user.getUserId());
		authRequest = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
		logger.debug("authRequest:" + JSON.toJSONString(authRequest));
		setDetails(request, authRequest);
		Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		return authentication;
	}

}

