package com.building.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by phuongtn2 on 7/11/2016.
 */
public class UserRoomDto{

	private Long userRoomId;
	private Integer userId;
	private Long roomCode;
	private Long floorCode;
	private Long buildingCode;

	public Long getUserRoomId() {
		return userRoomId;
	}

	public void setUserRoomId(Long userRoomId) {
		this.userRoomId = userRoomId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(Long roomCode) {
		this.roomCode = roomCode;
	}

	public Long getFloorCode() {
		return floorCode;
	}

	public void setFloorCode(Long floorCode) {
		this.floorCode = floorCode;
	}

	public Long getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(Long buildingCode) {
		this.buildingCode = buildingCode;
	}
}
