package com.netzoom.servicezuul.apimanager.admin.dao;

import com.netzoom.servicezuul.apimanager.model.Admin;
import com.netzoom.servicezuul.apimanager.util.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理员登录
 * @author tanzj
 */
@Component
public class AdminDAO  {

	@Autowired
	BaseDAO baseDAO;

	private static String NS = "com.netzoom.dao.AdminDAO.";

	/**
	 * 通过username查询管理员信息
	 * @param admin (username-用户名)
	 * @return Admin
	 * @throws Exception 异常
	 */
	public Admin queryAdminByUsername(Admin admin) throws Exception {
		return baseDAO.queryForObject(NS+"queryAdminByUsername",admin);
	};
}
