package com.building.dto;

import java.io.Serializable;
import java.util.Date;

public class RoleGroupRoleDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int roleGroupRoleId;
	private int roleGroupId;
	private int roleId;
	private Date created;
	private int createId;
	private Date lastUpdate;
	private int updateId;

	public int getRoleGroupRoleId() {
		return roleGroupRoleId;
	}

	public void setRoleGroupRoleId(int roleGroupRoleId) {
		this.roleGroupRoleId = roleGroupRoleId;
	}

	public int getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(int roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createID) {
		this.createId = createId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}
}