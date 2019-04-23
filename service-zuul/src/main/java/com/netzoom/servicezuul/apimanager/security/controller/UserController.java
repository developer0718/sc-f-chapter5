package com.netzoom.servicezuul.apimanager.security.controller;

import com.netzoom.servicezuul.apimanager.model.*;
import com.netzoom.servicezuul.apimanager.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Decription
 * @Author TanzJ
 * @Date 2019/4/2- 22:36
 */
@RestController
@RequestMapping(value = "/api")
public class UserController extends BaseController {

	@Autowired
	UserService userService;
	private Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 新增服务
	 *
	 * @param user (serviceId-服务id,serviceName-服务名称)
	 * @return BaseModel
	 */
	@PostMapping(value = "/insertService")
	public BaseModel insertService(@RequestBody User user) {
		user.setCreateUser("admin");
		return userService.insertUser(user);
	}

	/**
	 * 更新服务用户
	 *
	 * @param user (serviceId-服务id,serviceName-服务名称,sign-签名)
	 * @return BaseModel
	 */
	@PostMapping(value = "/updateService")
	public BaseModel updateService(@RequestBody User user) throws Exception {
		user.setUpdateUser("admin");
		return userService.updateService(user);
	}

	/**
	 * 禁用用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/disableService")
	public BaseModel disableService(@RequestBody User user) {
		user.setUpdateUser("admin");
		return userService.disableUser(user);
	}

	/**
	 * 删除用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/deleteService")
	public BaseModel deleteService(@RequestBody User user) {
		user.setUpdateUser("admin");
		return userService.deleteUser(user);
	}

	/**
	 * 启用用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/enableService")
	public BaseModel enableService(@RequestBody User user) {
		user.setUpdateUser("admin");
		return userService.enableUser(user);
	}

	/**
	 * 查询服务用户详细信息
	 *
	 * @param user (serviceId-服务id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/queryServiceDetail")
	public BaseModel queryServiceDetail(@RequestBody User user) {
		return userService.queryUserDetail(user);
	}

	/**
	 * 为服务授予角色
	 *
	 * @param user (serviceId-服务id,roleList-角色列表)
	 * @return BaseModel
	 */
	@PostMapping(value = "/insertServiceRole")
	public BaseModel insertUserRole(@RequestBody User user) throws Exception {
		return userService.insertUserRole(user, user.getRoleList());
	}

	@GetMapping(value = "/queryServiceList")
	public BaseModel queryUserList() {
		return userService.queryUserList();
	}

	/**
	 * 插入角色
	 *
	 * @param request 请求
	 * @param role    (roleName-角色名,state-状态,roleDetail-角色描述,createUser-创建人)
	 * @return BaseModel
	 */
	@PostMapping(value = "/insertRole")
	public BaseModel insertRole(HttpServletRequest request, @RequestBody Role role) {
//		String createUser = super.getCurrentUID(request);
		String createUser = "admin";
		role.setCreateUser(createUser);
		return userService.insertRole(role);
	}

	/**
	 * 删除角色
	 *
	 * @param request
	 * @param role    (roleId-角色id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/deleteRole")
	public BaseModel deleteRole(HttpServletRequest request, @RequestBody Role role) throws Exception {
//		String updateUser = super.getCurrentUID(request);
		String updateUser = "admin";
		role.setUpdateUser(updateUser);
		return userService.deleteRole(role);
	}

	/**
	 * 修改角色
	 *
	 * @param request 请求
	 * @param role    (roleId-角色id,roleName-角色名,roleDetail-角色描述,updateUser-修改人)
	 * @return BaseModel
	 */
	@PostMapping(value = "/editRole")
	public BaseModel editRole(@RequestBody Role role, HttpServletRequest request) {
//		String updateUser = super.getCurrentUID(request);
		String updateUser = "admin";
		role.setUpdateUser(updateUser);
		return userService.editRole(role);
	}

	/**
	 * 禁用角色
	 *
	 * @param request 请求
	 * @param role    (roleId-角色id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/disableRole")
	public BaseModel disableRole(HttpServletRequest request, @RequestBody Role role) throws Exception {
//		String updateUser = super.getCurrentUID(request);
		String updateUser = "admin";
		role.setUpdateUser(updateUser);
		return userService.disableRole(role);
	}

	/**
	 * 启用角色
	 *
	 * @param role (roleId-角色id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/enableRole")
	public BaseModel enableRole(@RequestBody Role role) throws Exception {
		String updateUser = "admin";
		role.setUpdateUser(updateUser);
		return userService.enableRole(role);
	}

	/**
	 * 为角色授权
	 *
	 * @param role (roleId-角色id,permissionList-权限list)
	 * @return
	 */
	@PostMapping(value = "/insertRolePermission")
	public BaseModel insertRolePermission(@RequestBody Role role) throws Exception {
		return userService.insertRolePermission(role);
	}

	/**
	 * 查询所有权限
	 *
	 * @return BaseModel
	 */
	@GetMapping(value = "/queryAllPermission")
	public BaseModel queryAllPermission() {
		return userService.queryAllPermission();
	}

	/**
	 * 查询权限详情
	 *
	 * @param permission (permissionId-权限id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/queryPermissionDetail")
	public BaseModel queryPermissionDetail(@RequestBody Permission permission) {
		return userService.queryPermissionDetail(permission);
	}

	/**
	 * 查询服务所提供的接口
	 *
	 * @param user (serviceId-服务id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/queryServiceResourceList")
	public BaseModel queryServicePermission(@RequestBody User user) {
		try {
			return userService.queryPermissionByUserId(user);
		} catch (Exception e) {
			logger.error("查询权限列表失败" + e);
			return new FailModel("网络异常，查询权限列表失败");
		}
	}

	/**
	 * 查询所有角色列表
	 *
	 * @return BaseModel
	 */
	@GetMapping(value = "/queryRoleList")
	public BaseModel queryRoleList() {
		try {
			return userService.queryRoleList();
		} catch (Exception e) {
			logger.error("查询角色列表失败" + e);
			return new FailModel("网络异常，查询角色列表失败");
		}
	}

	/**
	 * 插入权限资源
	 *
	 * @param permission (serviceId-服务id,permissionName-权限名称,resource-权限资源,state-状态)
	 * @return BaseModel
	 */
	@PostMapping(value = "/insertServicePermission")
	public BaseModel insertPermission(@RequestBody Permission permission) {
		return userService.insertPermission(permission);
	}

	/**
	 * 禁用资源
	 *
	 * @param permission (permissionId-资源id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/disablePermission")
	public BaseModel disablePermission(@RequestBody Permission permission) {
		return userService.disablePermission(permission);
	}

	/**
	 * 启用资源
	 *
	 * @param permission (permissionId-资源id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/enablePermission")
	public BaseModel enablePermission(@RequestBody Permission permission) {
		return userService.enablePermission(permission);
	}

	/**
	 * 删除资源
	 *
	 * @param permission (persmissionID-权限资源id)
	 * @return BaseModel
	 */
	@PostMapping(value = "/deletePermission")
	public BaseModel deletePermission(@RequestBody Permission permission) {
		return userService.deletePermission(permission);
	}


	/**
	 * 编辑资源
	 *
	 * @param permission (persmissionId-权限资源id,serviceId-服务id,permissionName-接口名,resource-内容,roleList-角色列表)
	 * @return BaseModel
	 */
	@PostMapping(value = "/updatePermission")
	public BaseModel updatePermission(@RequestBody Permission permission) throws Exception {
		permission.setUpdateUser("admin");
		return userService.updatePermission(permission);
	}


	/**
	 * 模糊查询角色列表
	 *
	 * @param role (roleName-模糊查询角色名)
	 * @return BaseModel
	 * @throws Exception
	 */
	@PostMapping(value = "/queryRoleListFuzzy")
	public BaseModel queryRoleListFuzzy(@RequestBody Role role) {
		return userService.queryRoleListFuzzy(role);
	}



}
