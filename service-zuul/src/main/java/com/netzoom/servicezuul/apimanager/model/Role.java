package com.netzoom.servicezuul.apimanager.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 角色实体类
 * @author tanzj
 */
public class Role implements GrantedAuthority {

	private String id;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 角色状态
	 */
	private int state;
	/**
	 * 角色描述
	 */
	private String roleDetail;
	/**
	 * 创建时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 为角色授权的权限列表
	 */
	private List<Permission> permissionList;
	/**
	 * 授权的权限id
	 */
	private String permissionId;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRoleDetail() {
		return roleDetail;
	}

	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (o == null || getClass() != o.getClass()) {return false;}
		Role role = (Role) o;
		return Objects.equals(roleId, role.roleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleId);
	}
}
