package com.netzoom.servicezuul.apimanager.security.service;

import com.netzoom.servicezuul.apimanager.model.Role;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.security.dao.RoleDAO;
import com.netzoom.servicezuul.apimanager.security.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * spring security处理用户信息校验
 * 并返回相应的角色
 * @author tanzj
 */
@Component
public class MyUserDetailService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserDAO userDAO;
	@Autowired
	RoleDAO roleDAO;


	/**
	 * 加载用户所拥有的角色
	 *
	 * @param userId 用户名 (serviceId-服务id)
	 * @return UserDetails
	 * @throws UsernameNotFoundException 找不到用户名异常
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.debug("user用户名：{}", userId);
//		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		// 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
		User queryUser = new User();
		queryUser.setUserId(Integer.parseInt(userId));
		User user = null;
		try {
			user = userDAO.queryUser(queryUser);
			if (user != null) {
				List<Role> roles = roleDAO.queryRoleByUsername(user);
				user.setAuthority(roles);
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error("查询用户失败,{}", e);
			return null;
		}
	}
}
