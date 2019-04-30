package com.netzoom.servicezuul.apimanager.security.dao;

import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.util.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

	@Autowired
	BaseDAO baseDAO;

	private static String NS = "com.netzoom.dao.UserDAO.";

	/**
	 * 通过用户id查询用户
	 *
	 * @param user (userId-用户名/服务名)
	 * @return User
	 * @throws Exception
	 */
	public User queryUser(User user) throws Exception {
		return baseDAO.queryForObject(NS + "queryUser", user);
	}

	/**
	 * 通过用户id查询用户信息
	 *
	 * @param user (userId-用户id)
	 * @return User
	 * @throws Exception 异常
	 */
	public User queryUserByUserId(User user) throws Exception {
		return baseDAO.queryForObject(NS + "queryUserByUserId", user);
	}

	/**
	 * 查询所有服务用户列表
	 *
	 * @return List
	 * @throws Exception 异常
	 */
	public List<User> queryUserList() throws Exception {
		return baseDAO.queryForList(NS + "queryUserList");
	}

	/**
	 * 为用户授予角色
	 *
	 * @param user (userId-用户id,roleId-角色Id)
	 * @return int
	 * @throws Exception
	 */
	public int insertUserRole(User user) throws Exception {
		return baseDAO.insert(NS + "insertUserRole", user);
	}

	/**
	 * 新增服务用户
	 *
	 * @param user (userId,username,password,)
	 * @return int
	 * @throws Exception 异常
	 */
	public int insertUser(User user) throws Exception {
		return baseDAO.insert(NS + "insertUser", user);
	}

	/**
	 * 更新用户信息
	 *
	 * @param user (userId-服务id,userName-用户名,updateUser-更新人)
	 * @return int
	 * @throws Exception
	 */
	public int updateService(User user) throws Exception {
		return baseDAO.update(NS + "updateService", user);
	}

	/**
	 * 禁用服务用户
	 *
	 * @param user (userId-用户id)
	 * @return int
	 * @throws Exception
	 */
	public int disableUser(User user) throws Exception {
		return baseDAO.update(NS + "disableUser", user);
	}

	/**
	 * 删除用户
	 *
	 * @param user (userId-用户id)
	 * @return int
	 * @throws Exception
	 */
	public int deleteUser(User user) throws Exception{
		return baseDAO.update(NS+"deleteUser",user);
	}

	/**
	 * 启用用户
	 *
	 * @param user (userId-用户id)
	 * @return int
	 * @throws Exception
	 */
	public int enableUser(User user) throws Exception{
		return baseDAO.update(NS+"enableUser",user);
	}

	/**
	 * 取消用户的角色授权
	 * @param user (userId-服务用户id)
	 * @return int
	 * @throws Exception
	 */
	public int unAuthenticatedServiceRole(User user) throws Exception{
		return baseDAO.delete(NS+"unAuthenticatedServiceRole",user);
	}

}
