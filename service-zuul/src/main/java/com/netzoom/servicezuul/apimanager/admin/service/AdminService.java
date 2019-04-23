package com.netzoom.servicezuul.apimanager.admin.service;

import com.netzoom.servicezuul.apimanager.admin.dao.AdminDAO;
import com.netzoom.servicezuul.apimanager.model.Admin;
import com.netzoom.servicezuul.apimanager.model.BaseModel;
import com.netzoom.servicezuul.apimanager.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员相关
 *
 * @author tanzj
 */
@Service
public class AdminService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AdminDAO adminDAO;

	/**
	 * 管理员登录
	 *
	 * @param admin (username-用户名,password-密码)
	 * @return BaseModel
	 * @throws Exception
	 */
	public BaseModel doLogin(Admin admin) throws Exception {
		Admin targetAdmin = adminDAO.queryAdminByUsername(admin);
		if (targetAdmin != null) {
			if (targetAdmin.getPassword().equals(admin.getPassword())) {
				return new BaseModel(Constant.SUCCESS, targetAdmin);
			} else {
				return new BaseModel(Constant.FAIL, "登录失败，密码错误");
			}
		} else {
			return new BaseModel(Constant.FAIL, "登录失败，用户不存在");
		}
	}
}
