package com.netzoom.servicezuul.apimanager.admin.controller;

import com.netzoom.servicezuul.apimanager.admin.service.AdminService;
import com.netzoom.servicezuul.apimanager.model.Admin;
import com.netzoom.servicezuul.apimanager.model.BaseModel;
import com.netzoom.servicezuul.apimanager.model.SuccessModel;
import com.netzoom.servicezuul.apimanager.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员相关
 * @author tanzj
 */
@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public BaseModel login(HttpServletRequest request,@RequestBody Admin admin) {

		BaseModel loginResult = null;
		try {
			loginResult = adminService.doLogin(admin);
			if (Constant.SUCCESS.equals(loginResult.getResult())) {
				Admin loginAdmin = (Admin) loginResult.getMessage();
				HttpSession session = request.getSession();
				session.setAttribute("userId", loginAdmin.getUserId());
				return new SuccessModel("登录成功");
			} else {
				//密码错误等情况
				return loginResult;
			}
		} catch (Exception e) {
			logger.error("登录失败",e);
			return new BaseModel(Constant.FAIL,"网络异常，登录失败");
		}
	}
}
