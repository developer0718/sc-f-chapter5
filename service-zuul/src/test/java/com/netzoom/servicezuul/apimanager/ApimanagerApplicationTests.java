package com.netzoom.servicezuul.apimanager;

import com.alibaba.fastjson.JSON;
import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
import com.netzoom.servicezuul.apimanager.security.dao.RoleDAO;
import com.netzoom.servicezuul.apimanager.security.dao.UserDAO;
import com.netzoom.servicezuul.apimanager.security.service.MyInvocationSecurityMetadataSourceService;
import com.netzoom.servicezuul.apimanager.security.service.MyUserDetailService;
import com.netzoom.servicezuul.apimanager.security.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApimanagerApplicationTests {
	@Autowired
	UserDAO userDAO;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	MyUserDetailService userDetailService;
	@Autowired
	MyInvocationSecurityMetadataSourceService metadataSourceService;
	@Autowired
	RoleDAO roleDAO;
	@Autowired
	PermissionDAO permissionDAO;
	@Autowired
	UserService userService;

	@Test
	public void contextLoads() throws Exception {
		User user = new User();
		user.setUsername("newService");
		user = userDAO.queryUser(user);
		logger.info("查询结果，{}", JSON.toJSONString(user));
	}

	@Test
	public void loadUserByUsername_test(){
		UserDetails user = userDetailService.loadUserByUsername("newService");
		logger.info("查询结果{}",JSON.toJSONString(user));
	}

	@Test
	public void loadAllPermission_test(){
		metadataSourceService.loadResourceDefine();
	}

	@Test
	public void encode_test(){
		String encodePassword = new BCryptPasswordEncoder().encode("tanzj");
		System.out.println("password:"+encodePassword);
	}

	@Test
	public void insertPermission_test(){
		Permission permission = new Permission();
		permission.setPermissionId("as5ds6");
		permission.setServiceId(1);
		permission.setPermissionName("测试接口2");
		permission.setPermissionType("other");
		permission.setState(1);
//		permission.setParentId("");
		permission.setCreateUser("tanzjjj");
		permission.setResource("/test/test/apisdsdsdsdsd");
		try {
			permissionDAO.insertPermission(permission);
			logger.info("添加权限成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void insertRolePermission_test(){
		Permission permission = new Permission();
		//admin权限
		permission.setRoleId("1");
		//添加角色权限接口
		permission.setPermissionId("as5ds6");
//		userService.insertRolePermission(permission);
	}


	@Test
	public void insertUserRole_test() throws Exception {
		User user = new User();
		user.setUserId(1);
		user.setRoleId("1");
		userDAO.insertUserRole(user);
	}

	@Test
	public void queryAllPermission_test(){
		logger.info("查询结果："+JSON.toJSONString(userService.queryAllPermission()));
	}



}
