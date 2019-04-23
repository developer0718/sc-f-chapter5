package com.netzoom.servicezuul.apimanager.security.dao;

import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.model.Role;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.util.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDAO {


	@Autowired
	BaseDAO baseDAO;

	private static String NS = "com.netzoom.dao.UserDAO.";

	/**
	 * 通过用户名查询用户角色
	 *
	 * @param user (username-用户名)
	 * @return List
	 * @throws Exception 异常
	 */
	public List<Role> queryRoleByUsername(User user) throws Exception {
		return baseDAO.queryForList(NS + "queryRoleByUsername", user);
	}

	/**
	 * 新增角色
	 *
	 * @param role (roleId-角色id,roleName-角色名,state-角色状态,roleDetail-角色描述,createUser-创建人)
	 * @return int
	 * @throws Exception 异常
	 */
	public int insertRole(Role role) throws Exception {
		return baseDAO.insert(NS + "insertRole", role);
	}

	/**
	 * 删除角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return int 影响的记录数
	 * @throws Exception 异常
	 */
	public int deleteRole(Role role) throws Exception {
		return baseDAO.update(NS + "deleteRole", role);
	}

	/**
	 * 修改角色
	 *
	 * @param role (roleId-角色id,roleName-角色名,roleDetail-角色描述,updateUser-修改人)
	 * @return int 影响的记录数
	 * @throws Exception 异常
	 */
	public int editRole(Role role) throws Exception {
		return baseDAO.update(NS + "editRole", role);
	}


	/**
	 * 禁用角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return int 影响的记录数
	 * @throws Exception 异常
	 */
	public int disableRole(Role role) throws Exception {
		return baseDAO.update(NS + "disableRole", role);
	}

	/**
	 * 启用角色
	 *
	 * @param role (roleId-角色id,updateUser-修改人)
	 * @return int 影响的记录数
	 * @throws Exception 异常
	 */
	public int enableRole(Role role) throws Exception {
		return baseDAO.update(NS + "enableRole", role);
	}

	/**
	 * 为角色授权
	 *
	 * @param permission （roleId-角色id,permissionId-权限id）
	 * @return int 影响的记录数
	 * @throws Exception 异常
	 */
	public int insertRolePermission(Permission permission) throws Exception {
		return baseDAO.insert(NS + "insertRolePermission", permission);
	}

	/**
	 * 查询所有角色
	 *
	 * @return List
	 * @throws Exception mybatis异常
	 */
	public List<Role> queryRoleList() throws Exception {
		return baseDAO.queryForList(NS + "queryRoleList");
	}

	/**
	 * 模糊查询角色列表
	 *
	 * @param role (roleName-角色名)
	 * @return List
	 * @throws Exception mapper 异常
	 *
	 */
	public List<Role> queryRoleListFuzzy(Role role) throws Exception{
		return baseDAO.queryForList(NS+"selectRoleListFuzzy",role);
	}


}
