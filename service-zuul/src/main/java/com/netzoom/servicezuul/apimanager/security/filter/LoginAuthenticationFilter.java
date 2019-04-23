package com.netzoom.servicezuul.apimanager.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.model.User;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 重写UsernamePassword过滤器认证方法
 * 使其支持json登录
 * @author tanzj
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * 使用json登录，将request中的body转换为json
	 * @param request http请求
	 * @param response http响应
	 * @return Authentication
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = null;
		BufferedReader streamReader = null;
		if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)|| request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)|| "application/json; charset=UTF-8".equals(request.getContentType())) {
			try {
				//解析request中的JSON并转换为User类对象
				streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
				StringBuilder stringBuilder = new StringBuilder();
				String inputStr;
				while ((inputStr = streamReader.readLine()) != null) {
					stringBuilder.append(inputStr);
				}
				User user = JSONObject.toJavaObject(JSON.parseObject(stringBuilder.toString()), User.class);
				//认证请求
				logger.debug("user为："+ JSON.toJSONString(user)+"password为："+user.getPassword()+"userId为："+user.getUserId());
				authRequest = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
				logger.debug("authRequest:"+ JSON.toJSONString(authRequest));
				setDetails(request, authRequest);
			} catch (IOException e) {
				e.printStackTrace();
				authRequest = new UsernamePasswordAuthenticationToken("", "");
			}
			return this.getAuthenticationManager().authenticate(authRequest);
		}
		else {
			return super.attemptAuthentication(request, response);
		}
	}
}
