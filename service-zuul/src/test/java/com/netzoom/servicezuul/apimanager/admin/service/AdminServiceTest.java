package com.netzoom.servicezuul.apimanager.admin.service;

import com.alibaba.fastjson.JSON;
import com.netzoom.servicezuul.apimanager.model.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 管理员业务逻辑单元测试
 * @author tanzj
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

	@Autowired
	AdminService adminService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 登录成功
	 */
	@Test
	public void doLogin_success_test() throws Exception {
		Admin admin = new Admin();
		admin.setUsername("tanzj");
		admin.setPassword("tanzj");
		logger.info("登录结果："+ JSON.toJSONString(adminService.doLogin(admin)));
	}

	/**
	 * 密码错误
	 */
	@Test
	public void doLogin_passwordError_test() throws Exception {
		Admin admin = new Admin();
		admin.setUsername("tanzj");
		admin.setPassword("error");
		logger.info("登录结果："+ JSON.toJSONString(adminService.doLogin(admin)));
	}

	/**
	 * 用户不存在
	 */
	@Test
	public void doLogin_userNotFound_test() throws Exception {
		Admin admin = new Admin();
		admin.setUsername("tanzj1");
		admin.setPassword("tanzj");
		logger.info("登录结果："+ JSON.toJSONString(adminService.doLogin(admin)));
	}

	/**
	 * exception测试
	 */
	@Test
	public void doLogin_exception_test() throws Exception {
		Admin admin = new Admin();
		logger.info("登录结果："+ JSON.toJSONString(adminService.doLogin(admin)));
	}


}