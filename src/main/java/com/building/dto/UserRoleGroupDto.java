package com.building.dto;

import java.io.Serializable;
import java.util.Date;

public class UserRoleGroupDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer userRoleGroupId;
	private Integer userId;
	private Integer roleGroupId;
	private Date startDay;
	private Date endDay;
	private Integer divisionId;
	private Integer managerId;
	private Integer centerManagerId;
	private String memo;
	private Date created;
	private Integer createId;
	private Date lastUpdate;
	private Integer updateId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getUserRoleGroupId() {
		return userRoleGroupId;
	}

	public void setUserRoleGroupId(Integer userRoleGroupId) {
		this.userRoleGroupId = userRoleGroupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(Integer roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Integer getCenterManagerId() {
		return centerManagerId;
	}

	public void setCenterManagerId(Integer centerManagerId) {
		this.centerManagerId = centerManagerId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
}