package com.netzoom.servicezuul.apimanager.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User implements UserDetails {

	/**
	 * 服务id
	 */
	@JSONField(name = "serviceId")
	private Integer userId;

	/**
	 * 服务名称
	 */
	@JSONField(name = "serviceName")
	private String username;

	/**
	 * 服务签名
	 */
	@JSONField(name = "sign")
	private String password;

	/**
	 * 服务状态
	 */
	private int state;

	/**
	 * 角色列表
	 */
	private List<Role> authority;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 创建人
	 */
	private String createUser;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 修改用户
	 */
	private String updateUser;


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@JSONField(serialize = false)
	public List<Role> roleList;

	@JSONField(serialize = false)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@JSONField(name = "sign")
	@Override
	public String getPassword() {
		return password;
	}

	@JSONField(name = "serviceName")
	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * 用户账号是否过期
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 用户账号是否被锁定
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 用户密码是否过期
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 用户是否可用
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String password, List authority) {
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	public List<Role> getAuthority() {
		return authority;
	}

	public User() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setAuthority(List authority) {
		this.authority = authority;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
