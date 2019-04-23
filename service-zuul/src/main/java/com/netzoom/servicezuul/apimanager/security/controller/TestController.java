package com.netzoom.servicezuul.apimanager.security.controller;

import com.netzoom.servicezuul.apimanager.model.BaseModel;
import com.netzoom.servicezuul.apimanager.model.Role;
import com.netzoom.servicezuul.apimanager.model.User;
import com.netzoom.servicezuul.apimanager.security.dao.PermissionDAO;
import com.netzoom.servicezuul.apimanager.security.dao.RoleDAO;
import com.netzoom.servicezuul.apimanager.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class TestController extends BaseController{

    @Autowired
    RoleDAO roleDAO;
    @Autowired
    PermissionDAO permissionDAO;
    @Autowired
    UserService userService;


    @GetMapping(value = "/home")
    public BaseModel queryRolePermission() {
        User user = super.getUserFromContext();
        List list = new ArrayList();
        for (Role role : user.getAuthority()) {
            try {
                list.addAll(permissionDAO.queryRolePermission(role));
            } catch (Exception e) {
                return new BaseModel("fail","查询权限失败");
            }
        }
        return new BaseModel("success", list);
    }


//    @PostMapping(value = "/insertPermission")
//    public BaseModel insertPermission(@RequestBody Permission permission){
//        return userService.insertPermission(permission);
//    }

//    @PostMapping(value = "/insertRolePermission")
//    public BaseModel insertRolePermission(@RequestBody Permission permission){
//    	return userService.insertRolePermission(permission);
//	}

	@PostMapping(value = "/api/testMethod")
	public String testMethod(){
    	return "execute /api/testMethod success";
	}
}
