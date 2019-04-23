package com.netzoom.servicezuul.apimanager.security.dao;

import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.model.Role;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.util.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionDAO {

	private static String NS = "com.netzoom.dao.UserDAO.";
	@Autowired
	BaseDAO baseDAO;

	/**
	 * 根据roleId查询权限列表
	 *
	 * @param role (roleId)
	 * @return List
	 * @throws Exception
	 */
	public List<Permission> queryRolePermission(Role role) throws Exception {
		return baseDAO.queryForList(NS + "queryRolePermission", role);
	}

	/**
	 * 查询所有角色权限对应关系
	 *
	 * @return List
	 * @throws Exception
	 */
	public List<Permission> queryPermission() throws Exception {
		return baseDAO.queryForList(NS + "queryPermission");
	}

	/**
	 * 查询所有权限
	 *
	 * @return List
	 * @throws Exception
	 */
	public List<Permission> queryAllPermission() throws Exception {
		return baseDAO.queryForList(NS + "queryAllPermission");
	}

	/**
	 * 新增权限
	 *
	 * @param permission (serviceId-服务id,permissionId-权限id，resource-资源，permissionName-资源名称)
	 * @return int
	 * @throws Exception
	 */
	public int insertPermission(Permission permission) throws Exception {
		return baseDAO.insert(NS + "insertPermission", permission);
	}

	/**
	 * 为角色授权
	 *
	 * @param role (roleId-角色id,permissionId-权限id)
	 * @return int
	 * @throws Exception 异常
	 */
	public int insertRolePermission(Role role) throws Exception {
		return baseDAO.insert(NS + "insertRolePermission", role);
	}

	/**
	 * 根据权限查询权限详情
	 *
	 * @param permission (permissionId-权限id)
	 * @return List
	 * @throws Exception 异常
	 */
	public Permission queryPermissionDetail(Permission permission) throws Exception {
		return baseDAO.queryForObject(NS + "queryPermissionDetail", permission);
	}

	/**
	 * 根据权限id查询对应角色
	 *
	 * @param permission (perimissionId)
	 * @return List
	 * @throws Exception
	 */
	public List<Role> queryPermissionRole(Permission permission) throws Exception {
		return baseDAO.queryForList(NS + "queryPermissionRole", permission);
	}

	/**
	 * 通过userId查询其提供的接口
	 *
	 * @param user (userId-用户id)
	 * @return List
	 * @throws Exception
	 */
	public List<Permission> queryPermissionByUserId(User user) throws Exception {
		return baseDAO.queryForList(NS + "queryPermissionByUserId", user);
	}

	/**
	 * 通过roleId删除其下权限
	 *
	 * @param role (roleId-角色id)
	 * @return int
	 * @throws Exception
	 */
	public int deleteRolePermission(Role role) throws Exception {
		return baseDAO.delete(NS + "deleteRolePermission", role);
	}

	/**
	 * 禁用权限资源
	 *
	 * @param permission (permissionId-资源id)
	 * @return int
	 * @throws Exception
	 */
	public int disablePermission(Permission permission) throws Exception {
		return baseDAO.update(NS + "disablePermission", permission);
	}

	/**
	 * 启用权限资源
	 *
	 * @param permission (permissionId-资源id)
	 * @return int
	 * @throws Exception
	 */
	public int enablePermission(Permission permission) throws Exception {
		return baseDAO.update(NS + "enablePermission", permission);
	}

	/**
	 * 删除权限资源
	 *
	 * @param permission (permissionId-权限id)
	 * @return int 影响得行数
	 * @throws Exception
	 */
	public int deletePermission(Permission permission) throws Exception {
		return baseDAO.delete(NS + "deletePermission", permission);
	}

	/**
	 * 更新服务资源
	 *
	 * @param permission (permissionId-服务资源id,serviceId-资源所属服务id,permissionName-服务名,resource-资源内容)
	 * @return int
	 * @throws Exception
	 */
	public int updatePermission(Permission permission) throws Exception {
		return baseDAO.update(NS + "updatePermission", permission);
	}

	/**
	 * 删除权限所拥有角色
	 *
	 * @param permission (permissionId-接口资源id)
	 * @return int 影响条数
	 * @throws Exception mapper异常
	 */
	public int unAuthenticatedPermission(Permission permission) throws Exception {
		return baseDAO.delete(NS + "unAuthenticatedPermission", permission);
	}

}
