package com.building.dto;

import com.building.dto.login.UserDto;

/**
 * Created by phuongtn2 on 7/11/2016.
 */
public class ProfileDto {

	private UserDto userDto;
	private String roomAlias;
	private String floorAlias;
	private String buildingName;

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getRoomAlias() {
		return roomAlias;
	}

	public void setRoomAlias(String roomAlias) {
		this.roomAlias = roomAlias;
	}

	public String getFloorAlias() {
		return floorAlias;
	}

	public void setFloorAlias(String floorAlias) {
		this.floorAlias = floorAlias;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
}
