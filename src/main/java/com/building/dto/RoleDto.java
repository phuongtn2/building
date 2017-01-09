package com.building.dto;

/**
 * T_ROLEのDTO
 *
 * @author PhuongTN2
 *
 */
public class RoleDto {

	private int roleId;
	private String roleName;
	private String roleMemo;

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleMemo() {
		return roleMemo;
	}
	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}
}
