package com.building.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by phuongtn2 on 7/11/2016.
 */
public class UserRoleRoomDto extends DefaultObjectDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private UserDto userDto;
	private UserRoomDto userRoomDto;
	private UserRoleGroupDto userRoleGroupDto;
	private Date startDay;
	private Date endDay;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public UserRoomDto getUserRoomDto() {
		return userRoomDto;
	}

	public void setUserRoomDto(UserRoomDto userRoomDto) {
		this.userRoomDto = userRoomDto;
	}

	public UserRoleGroupDto getUserRoleGroupDto() {
		return userRoleGroupDto;
	}

	public void setUserRoleGroupDto(UserRoleGroupDto userRoleGroupDto) {
		this.userRoleGroupDto = userRoleGroupDto;
	}
}
