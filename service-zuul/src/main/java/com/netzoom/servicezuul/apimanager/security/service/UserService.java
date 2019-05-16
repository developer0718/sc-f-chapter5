package com.netzoom.servicezuul.apimanager.security.service;

import com.netzoom.servicezuul.apimanager.model.*;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
import com.netzoom.servicezuul.apimanager.security.dao.RoleDAO;
import com.netzoom.servicezuul.apimanager.security.dao.UserDAO;
import com.netzoom.servicezuul.apimanager.util.CommonUtil;
import com.netzoom.servicezuul.apimanager.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * @Decription
 * @Author TanzJ
 * @Date 2019/4/2- 22:39
 */
@Service
public class UserService {

	@Autowired
	PermissionDAO permissionDAO;
	@Autowired
	RoleDAO roleDAO;
	@Autowired
	MyUserDetailService userDetailService;
	@Autowired
	MyInvocationSecurityMetadataSourceService metadataSourceService;
	@Autowired
	UserDAO userDAO;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 插入服务用户
	 *
	 * @param user (userId-服务id,username-服务名,password-服务签名,state-服务状态,createUser-创建人)
	 * @return BaseModel
	 */
	public BaseModel insertUser(User user) {
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getUserId() + Constant.SALT));
			userDAO.insertUser(user);
			return new BaseModel(Constant.SUCCESS, "新增服务用户成功");
		} catch (Exception e) {
			logger.error("新增服务用户失败" + e);
			return new BaseModel(Constant.FAIL, "网络异常，新增服务用户失败，请检查用户是否已存在");
		}
	}

	/**
	 * 查询所有权限资源
	 *
	 * @return BaseModel
	 */
	public BaseModel queryAllPermission() {
		try {
			List<Permission> permissionList = permissionDAO.queryAllPermission();
			return new SuccessModel(new ResponseData(permissionList));
		} catch (Exception e) {
			logger.error("查询权限列表失败" + e);
			return new FailModel("网络异常,查询权限列表失败");
		}
	}

	/**
	 * 插入权限资源
	 *
	 * @param permission (serviceId-所属服务id,permissionType-权限类型,permissionName-权限名称,parentId-父目录id,state-状态,createUser-创建人,resource-权限资源内容)
	 * @return BaseModel
	 */
	public BaseModel insertPermission(Permission permission) {
		try {
			permission.setPermissionType("other");
			permission.setPermissionId(CommonUtil.getUUID());
			permissionDAO.insertPermission(permission);
			return new SuccessModel("添加权限成功");
		} catch (Exception e) {
			return new FailModel("网络异常，添加权限失败");
		}
	}

	/**
	 * 通过服务id查询服务详情
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	public BaseModel queryUserDetail(User user) {
		try {
			user = userDAO.queryUserByUserId(user);
			List<Role> roleList = roleDAO.queryRoleByUsername(user);
			user.setAuthority(roleList);
			return new SuccessModel(new ResponseData(user));
		} catch (Exception e) {
			logger.error("查询服务用户信息失败" + e);
			return new FailModel("网络异常，查询服务用户信息失败");
		}
	}

	/**
	 * 查询服务列表
	 *
	 * @return BaseModel
	 */
	public BaseModel queryUserList() {
		try {
			List<User> userList = userDAO.queryUserList();
			return new SuccessModel(new ResponseData(userList));
		} catch (Exception e) {
			logger.error("网络异常，查询服务列表失败" + e);
			return new FailModel("网络异常，查询服务列表失败");
		}
	}

	/**
	 * 为服务授予角色
	 *
	 * @param user (userId-用户id,roleId-角色id)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel insertUserRole(User user, List<Role> roleList) throws Exception {
		//先删除所有已存在的角色关系
		userDAO.unAuthenticatedServiceRole(user);
		//再添加角色
		for (Role role : roleList) {
			User authorizedUser = new User();
			authorizedUser.setUserId(user.getUserId());
			authorizedUser.setRoleId(role.getRoleId());
			userDAO.insertUserRole(authorizedUser);
		}
		return new SuccessModel("授权服务角色成功！");
	}


	/**
	 * 插入角色权限
	 *
	 * @param role (permissionList-权限列表,roleId-角色id)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel insertRolePermission(Role role) throws Exception {
		//先删除现有的权限
		permissionDAO.deleteRolePermission(role);
		List<Permission> permissionList = role.getPermissionList();
		//逐一授权
		for (Permission permission : permissionList) {
			role.setPermissionId(permission.getPermissionId());
			permissionDAO.insertRolePermission(role);
		}
		//重新加载资源定义
		metadataSourceService.loadResourceDefine();
		return new SuccessModel("授权成功");
	}

	/**
	 * 新增角色
	 *
	 * @param role (roleName-角色名,state-状态,roleDetail-角色描述,createUser-创建人)
	 * @return BaseModel
	 */
	public BaseModel insertRole(Role role) {
		try {
			role.setRoleId(CommonUtil.getUUID());
			roleDAO.insertRole(role);
			return new SuccessModel("新增角色成功");
		} catch (Exception e) {
			logger.error("新增角色失败" + e);
			return new FailModel("网络异常，新增角色失败，请检查角色是否已存在");
		}
	}

	/**
	 * 删除角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel deleteRole(Role role) throws Exception {
		int result = roleDAO.deleteRole(role);
		if (result == 1) {
			//重新加载资源
			metadataSourceService.loadResourceDefine();
			return new SuccessModel("删除角色成功");
		} else {
			throw new Exception("影响多条数据，删除角色失败");
		}
	}

	/**
	 * 修改角色
	 *
	 * @param role (roleId-角色id,roleName-角色名称,roleDetail-角色描述,updateUser-修改人)
	 * @return BaseModel
	 */
	public BaseModel editRole(Role role) {
		try {
			int result = roleDAO.editRole(role);
			if (result == 1) {
				return new SuccessModel("修改角色成功");
			} else {

				logger.error("修改角色失败，影响" + result + "条记录");
				return new FailModel("修改角色失败");
			}
		} catch (Exception e) {
			logger.error("修改角色失败");
			return new FailModel("网络异常，修改角色失败，请检查角色是否已存在");
		}
	}

	/**
	 * 禁用角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel disableRole(Role role) throws Exception {
		int result = roleDAO.disableRole(role);
		if (result == 1) {
			metadataSourceService.loadResourceDefine();
			return new SuccessModel("禁用角色成功");
		} else {
			logger.error("禁用角色失败，影响" + result + "条记录");
			throw new Exception("禁用角色失败，影响多条记录");
		}
	}

	/**
	 * 启用角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel enableRole(Role role) throws Exception {
		int result = roleDAO.enableRole(role);
		if (result == 1) {
			//重新加载资源
			metadataSourceService.loadResourceDefine();
			return new SuccessModel("启用角色成功");
		} else {
			logger.error("启用角色失败，影响" + result + "条记录");
			throw new Exception("启用角色失败，影响多条记录");
		}
	}



	/**
	 * 查询权限详情
	 *
	 * @param permission (permissionId-权限id)
	 * @return
	 */
	public BaseModel queryPermissionDetail(Permission permission) {
		try {
			permission = permissionDAO.queryPermissionDetail(permission);
			List<Role> roleList = permissionDAO.queryPermissionRole(permission);
			permission.setRoleList(roleList);
			return new SuccessModel(new ResponseData(permission));
		} catch (Exception e) {
			logger.error("查询权限详情失败" + e);
			return new FailModel("网络异常，查询权限详情失败");
		}
	}

	/**
	 * 查询服务所挂载的接口
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 * @throws Exception 异常
	 */
	public BaseModel queryPermissionByUserId(User user) throws Exception {
		List<Permission> permissionList = permissionDAO.queryPermissionByUserId(user);
		return new SuccessModel(new ResponseData(permissionList));
	}

	/**
	 * 查询角色列表
	 *
	 * @return BaseModel
	 * @throws Exception mybatis异常
	 */
	public BaseModel queryRoleList() throws Exception {
		List roleList = roleDAO.queryRoleList();
		return new SuccessModel(new ResponseData(roleList));
	}

	/**
	 * 禁用权限资源
	 *
	 * @param permission (permissionId-权限id)
	 * @return BaseModel
	 * @throws Exception
	 */
	public BaseModel disablePermission(Permission permission) {
		try {
			int result = permissionDAO.disablePermission(permission);
			if (result == 1) {
				//重新初始化所有资源对应的角色
				metadataSourceService.loadResourceDefine();
				return new SuccessModel("禁用成功");
			} else {
				return new FailModel("禁用失败");
			}
		} catch (Exception e) {
			logger.error("禁用失败" + e);
			return new FailModel("网络异常,禁用失败");
		}
	}

	/**
	 * 启用权限
	 *
	 * @param permission （permissionId-权限id）
	 * @return BaseModel
	 */
	public BaseModel enablePermission(Permission permission) {
		try {
			int result = permissionDAO.enablePermission(permission);
			if (result != 0) {
				//重新初始化所有资源对应的角色
				metadataSourceService.loadResourceDefine();
				return new SuccessModel("启用成功");
			} else {
				return new FailModel("启用失败");
			}
		} catch (Exception e) {
			logger.error("启用失败" + e);
			return new FailModel("网络异常,启用失败");
		}
	}

	/**
	 * 删除权限资源
	 *
	 * @param permission (permissionId-权限id)
	 * @return BaseModel
	 */
	public BaseModel deletePermission(Permission permission) {
		try {
			int result = permissionDAO.deletePermission(permission);
			permissionDAO.deletePermissionRole(permission);
			//重新初始化所有资源对应的角色
			metadataSourceService.loadResourceDefine();
			if (result == 1) {
				return new SuccessModel("删除成功");
			} else {
				return new FailModel("删除失败");
			}
		} catch (Exception e) {
			logger.error("删除权限资源失败" + e);
			return new FailModel("网络异常，删除权限资源失败");
		}
	}

	/**
	 * 更新服务用户信息
	 *
	 * @param user (userId-服务id,userName-用户名,updateUser-更新人,roleList-角色列表)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel updateService(User user) throws Exception {
		//为roleList去重
		List<Role> roleList = user.getRoleList();
		HashSet<Role> roleHashSet = new HashSet<Role>(roleList.size());
		for (Role role:roleList){
			roleHashSet.add(role);
		}
		//先删除所有已存在的角色关系
		userDAO.unAuthenticatedServiceRole(user);
		//再添加角色
		for (Role role : roleHashSet) {
			User authorizedUser = new User();
			authorizedUser.setUserId(user.getUserId());
			authorizedUser.setRoleId(role.getRoleId());
			userDAO.insertUserRole(authorizedUser);
		}
		int affectRows = userDAO.updateService(user);
		if (affectRows == 1) {
			return new SuccessModel("更新成功");
		} else {
			throw new Exception("更新失败，不存在服务或影响多条记录");
		}

	}

	/**
	 * 禁用用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	public BaseModel disableUser(User user) {
		try {
			int affectRows = userDAO.disableUser(user);
			if (affectRows == 1) {
				return new SuccessModel("禁用成功");
			} else {
				return new FailModel("参数错误，禁用失败" + affectRows);
			}
		} catch (Exception e) {
			logger.error("禁用失败" + e);
			return new FailModel("网络异常，禁用失败");
		}
	}

	/**
	 * 删除用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	public BaseModel deleteUser(User user) {
		try {
			int affectRows = userDAO.deleteUser(user);
			if (affectRows == 1) {
				return new SuccessModel("删除成功");
			} else {
				return new FailModel("参数错误，删除失败" + affectRows);
			}
		} catch (Exception e) {
			logger.error("删除失败" + e);
			return new FailModel("网络异常，删除失败");
		}
	}

	/**
	 * 启用用户
	 *
	 * @param user (userId-用户id)
	 * @return BaseModel
	 */
	public BaseModel enableUser(User user) {
		try {

			int affectRows = userDAO.enableUser(user);
			if (affectRows == 1) {
				return new SuccessModel("启用成功");
			} else {
				return new FailModel("参数错误，启用失败" + affectRows);
			}
		} catch (Exception e) {
			logger.error("启用失败" + e);
			return new FailModel("网络异常，启用失败");
		}
	}


	/**
	 * 更新资源权限
	 *
	 * @param permission (permissionId-服务资源id,serviceId-资源所属服务id,permissionName-服务名,resource-资源内容,roleList-角色列表)
	 * @return BaseModel
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseModel updatePermission(Permission permission) throws Exception {
		//删除现有接口角色
		permissionDAO.unAuthenticatedPermission(permission);
		//为接口授予角色
		for (Role role : permission.getRoleList()) {
			role.setPermissionId(permission.getPermissionId());
			permissionDAO.insertRolePermission(role);
		}
		int affectRows = permissionDAO.updatePermission(permission);
		if (affectRows == 1) {
			//重新加载资源
			metadataSourceService.loadResourceDefine();
			return new SuccessModel("更新成功");
		} else {
			throw new Exception("影响多条记录，更新失败");
		}

	}

	/**
	 * 模糊查询角色列表
	 *
	 * @param role (roleName-模糊角色名)
	 * @return BaseModel
	 */
	public BaseModel queryRoleListFuzzy(Role role) {
		List<Role> roleList = null;
		try {
			roleList = roleDAO.queryRoleListFuzzy(role);
			return new SuccessModel(new ResponseData(roleList));
		} catch (Exception e) {
			logger.error("参数错误，查询失败" + e);
			return new FailModel("参数错误，查询失败");
		}
	}


}
