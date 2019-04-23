package com.netzoom.servicezuul.apimanager.security.service;

import com.alibaba.fastjson.JSON;
import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.model.Role;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
import com.netzoom.servicezuul.apimanager.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;
	private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	PermissionDAO permissionDAO;
	@Test
	public void insertUser_success_test() throws Exception {
		User user = new User();
		user.setUserId(23);
		user.setUsername(CommonUtil.getUUID().substring(0,14));
		user.setCreateUser("tanzj");
		user.setPassword("123");
		logger.info("新增用户："+ JSON.toJSONString(userService.insertUser(user)));
	}

	@Test
	public void insertUser_userExist_test() throws Exception {
		User user = new User();
		user.setUserId(434);
		user.setUsername("tes22t");
		user.setCreateUser("tanzj");
//		user.setPassword("123");
		logger.info("新增用户："+ JSON.toJSONString(userService.insertUser(user)));
	}

	@Test
	public void queryServiceDetail_success_test(){
		User user = new User();
		user.setUserId(2518110);
		logger.info("查询用户详情："+JSON.toJSONString(userService.queryUserDetail(user)));
	}

	@Test
	public void queryServiceDetail_exception_test(){
		User user = new User();
		logger.info("查询用户详情："+JSON.toJSONString(userService.queryUserDetail(user)));
	}

	@Test
	public void queryUserList_success_test(){
		logger.info("查询服务列表"+JSON.toJSONString(userService.queryUserList()));
	}

	@Test
	public void insertUserRole_success_test() throws Exception {
		User user = new User();
		user.setUserId(1);
		Role role  = new Role();
//		role.setRoleId("1");
		List<Role> roles = new ArrayList<>();
//		roles.add(role);
		logger.info("为服务角色授权"+JSON.toJSONString(userService.insertUserRole(user,roles)));
	}


	@Test
	public void insertRole_success_test(){
		Role role = new Role();
		role.setRoleId(CommonUtil.getUUID());
		role.setRoleName("测试角色");
		role.setRoleDetail("这是一个角色");
		role.setState(1);
		role.setCreateUser("tanzj");
		logger.info("新增角色结果："+JSON.toJSONString(userService.insertRole(role)));
	}

	@Test
	public void insertRole_exception_test(){
		Role role = new Role();
		logger.info("新增角色结果："+JSON.toJSONString(userService.insertRole(role)));
	}

	@Test
	public void deleteRole_success_test() throws Exception {
		Role role = new Role();
		role.setRoleId("0397b8203ff8448d859932ca997eb87c");
		logger.info("删除角色结果："+JSON.toJSONString(userService.deleteRole(role)));
	}

	@Test
	public void deleteRole_exception_test() throws Exception {
		Role role = new Role();
		logger.info("删除角色结果："+JSON.toJSONString(userService.deleteRole(role)));
	}

	@Test
	public void editRole_success_test(){
		Role role = new Role();
		role.setRoleId("1");
		role.setRoleName("新角色修改");
		role.setRoleDetail("这是一条描述");
		role.setUpdateUser("tanzj");
		logger.info("修改角色结果"+JSON.toJSONString(userService.editRole(role)));
	}

	@Test
	public void editRole_exception_test(){
		Role role = new Role();
		logger.info("修改角色结果"+JSON.toJSONString(userService.editRole(role)));
	}

	@Test
	public void disableRole_success_test() throws Exception {
		Role role = new Role();
		role.setRoleId("1");
		role.setUpdateUser("tanzj");
		logger.info("禁用角色结果"+JSON.toJSONString(userService.disableRole(role)));
	}

	@Test
	public void disableRole_exception_test() throws Exception {
		Role role = new Role();
		logger.info("禁用角色结果"+JSON.toJSONString(userService.disableRole(role)));
	}

	@Test
	public void enableRole_success_test() throws Exception {
		Role role = new Role();
		role.setRoleId("1");
		role.setUpdateUser("tanzj");
		logger.info("启用角色结果"+JSON.toJSONString(userService.enableRole(role)));

	}

	@Test
	public void enableRole_exception_test() throws Exception {
		Role role = new Role();
		role.setRoleId("4ef42e9495db443eab488286f9910698");
		role.setUpdateUser("tanzj");
		logger.info("启用角色结果"+JSON.toJSONString(userService.enableRole(role)));
	}

	@Test
	public void queryPermissionDetail_success_test(){
		Permission permission = new Permission();
		permission.setPermissionId("as5ds6");
		logger.info("查询接口详情结果："+JSON.toJSONString(userService.queryPermissionDetail(permission)));
	}

	@Test
	public void queryUserPermission_success_test() throws Exception {
		User user = new User();
		user.setUserId(1);
		logger.info("查询服务所提供的结果："+JSON.toJSONString(userService.queryPermissionByUserId(user)));
	}

	@Test
	public void insertRolePermission_test() throws Exception {
		Role role = new Role();
		role.setRoleId("ce14b9c6d43745b5b005554e3acc7795");
		Permission permission = new Permission();
		permission.setPermissionId("as5ds6");
		List<Permission> permissionList = new ArrayList<>();
		permissionList.add(permission);
		role.setPermissionList(permissionList);
		logger.info("为角色授权："+JSON.toJSONString(userService.insertRolePermission(role)));
	}

	@Test
	public void insertPermission_success_test(){
		Permission permission = new Permission();
		permission.setServiceId(1);
		permission.setPermissionType("other");
		permission.setPermissionName("追到就让你嘿嘿嘿接口");
		permission.setState(1);
		permission.setCreateUser("admin");
		permission.setResource("/ZHUIDAOJIURANGNIHEIHEIHEI");
		logger.info("为角色授权："+JSON.toJSONString(userService.insertPermission(permission)));
	}

	@Test
	public void insertPermission_exception_test(){
		Permission permission = new Permission();
//		permission.setServiceId(1);
		permission.setPermissionType("other");
		permission.setPermissionName("追到就让你嘿嘿嘿接口");
		permission.setState(1);
		permission.setCreateUser("admin");
		permission.setResource("/ZHUIDAOJIURANGNIHEIHEIHEI");
		logger.info("为角色授权："+JSON.toJSONString(userService.insertPermission(permission)));
	}

	@Test
	public void disablePermission_success_test() throws Exception {
		Permission permission = new Permission();
		permission.setPermissionId("as5ds6");
		logger.info("禁用权限资源"+JSON.toJSONString(userService.disablePermission(permission)));
	}

	@Test
	public void enablePermission_success_test() {
		Permission permission = new Permission();
		permission.setPermissionId("as5ds6");
		CommonUtil.makeLogInfo(userService.enablePermission(permission));
	}

	@Test
	public void enablePermission_fail_test() {
		Permission permission = new Permission();
		permission.setPermissionId("as5d23s6");
		CommonUtil.makeLogInfo(userService.enablePermission(permission));
	}

	@Test
	public void deletePermission_success_test(){
		Permission permission = new Permission();
		permission.setPermissionId("b50417320fa5453bb967939ebce0d81a");
		CommonUtil.makeLogInfo(userService.deletePermission(permission));
	}

	@Test
	public void deletePermission_fail_test(){
		Permission permission = new Permission();
		permission.setPermissionId("b50417320fb967939ebce0d81a");
		CommonUtil.makeLogInfo(userService.deletePermission(permission));
	}

	@Test
	public void updateUser_success_test() throws Exception {
		User user = new User();
		user.setUserId(1);
		user.setUsername("金融服务平台");
		user.setUpdateUser("admin");
		CommonUtil.makeLogInfo(userService.updateService(user));
	}

	@Test
	public void updateUser_exception_test() throws Exception {
		User user = new User();
		CommonUtil.makeLogInfo(userService.updateService(user));
	}

	@Test
	public void updateUser_fail_test() throws Exception {
		User user = new User();
		user.setUserId(123232323);
		user.setUsername("嘿嘿嘿");
		user.setPassword("12345678978978979878979879");
		user.setUpdateUser("admin");
		CommonUtil.makeLogInfo(userService.updateService(user));
	}

	@Test
	public void disableUser_success_test(){
		User user = new User();
		user.setUserId(1);
		CommonUtil.makeLogInfo(userService.disableUser(user));
	}

	@Test
	public void disableUser_fail_test(){
		User user = new User();
		user.setUserId(322322323);
		CommonUtil.makeLogInfo(userService.disableUser(user));
	}

	@Test
	public void deleteUser_success_test(){
		User user = new User();
		user.setUserId(1);
		CommonUtil.makeLogInfo(userService.deleteUser(user));
	}

	@Test
	public void deleteUser_fail_test(){
		User user = new User();
		user.setUserId(1252525252);
		CommonUtil.makeLogInfo(userService.deleteUser(user));
	}

	@Test
	public void enableUser_success_test(){
		User user = new User();
		user.setUserId(1);
		CommonUtil.makeLogInfo(userService.enableUser(user));
	}

	@Test
	public void enableUser_fail_test(){
		User user = new User();
		user.setUserId(1252525252);
		CommonUtil.makeLogInfo(userService.enableUser(user));
	}

	@Test
	public void updatePermission_success_test() throws Exception {
		Permission permission = new Permission();
		permission.setPermissionId("10babd3b37ea4393bc8039a3cf667464");
		permission.setResource("/heiheiheiheihei");
		permission.setUpdateUser("tanzj");
		CommonUtil.makeLogInfo(userService.updatePermission(permission));
	}



	@Test
	public void updatePermission_fail_test() throws Exception {
		Permission permission = new Permission();
//		permission.setPermissionId("1");
		permission.setResource("/heiheiheiheihei");
		CommonUtil.makeLogInfo(userService.updatePermission(permission));
	}

	@Test
	public void queryRoleListFuzzy_success_test() throws Exception {
		Role role = new Role();
//		role.setRoleName("最高");
		CommonUtil.makeLogInfo(userService.queryRoleListFuzzy(role));
	}
}