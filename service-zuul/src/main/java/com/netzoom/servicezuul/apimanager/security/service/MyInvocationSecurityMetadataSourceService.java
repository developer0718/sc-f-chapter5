package com.netzoom.servicezuul.apimanager.security.service;

import com.alibaba.fastjson.JSON;
import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.netzoom.servicezuul.apimanager.util.Constant.ADMIN_LOGIN;
import static com.netzoom.servicezuul.apimanager.util.Constant.LOGIN_URL;

/**
 * 获取该地址需要的用户角色
 * @author tanzj
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	PermissionDAO permissionDAO;

	/**
	 * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
	 */
	private static HashMap<String, Collection<ConfigAttribute>> map =null;

	/**
	 * 返回请求的资源需要的角色
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//如果静态权限资源中没有map，则重加在
		if (null == map) {
			loadResourceDefine();
		}
		//object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		if (LOGIN_URL.equals(request.getRequestURL().toString())||ADMIN_LOGIN.equals(request.getRequestURL().toString()) ){
			return null;
		}
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String url = it.next();
			if (new AntPathRequestMatcher(url).matches(request)) {
				return map.get(url);
			}
		}
		logger.debug("map为："+ JSON.toJSONString(map));
		//没有匹配上的资源，都是登录访问
		return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}

	/**
	 * 初始化所有资源对应的角色
	 */
	public void loadResourceDefine(){
		map = new HashMap<>();
		//权限资源 和 角色对应的表  也就是 角色权限 中间表
		try {
			List<Permission> rolePermissionList = permissionDAO.queryPermission();
			for(Permission permission:rolePermissionList){
				String resource = permission.getResource();
				String roleName = permission.getRoleName();
				ConfigAttribute role = new SecurityConfig(roleName);
				if(map.containsKey(resource)){
					map.get(resource).add(role);
				}else{
					List<ConfigAttribute> list =  new ArrayList<>();
					list.add(role);
					map.put(resource,list);
				}
			}
			logger.debug("资源列表为："+ JSON.toJSONString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
