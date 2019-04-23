package com.netzoom.servicezuul.apimanager.security.controller;

import com.alibaba.fastjson.JSON;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	protected User getUserFromContext(){
		logger.info("userDetail："+ JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
		return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * 获取请求中的用户信息
	 * @param request 请求体
	 * @return userId 用户id（教职工号/学号）
	 */
	public String getCurrentUID(HttpServletRequest request){
		return request.getSession().getAttribute(Constant.USER_ID).toString();
	}

}
