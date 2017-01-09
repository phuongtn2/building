package com.building.mapper;

import com.building.dto.UserDto;
import com.building.dto.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */
public interface ManagerUserMapper {
    int insertUser(@Param("dto") UserDto userDto);
    List<UserDto> findAllUser();
    UserDto findUserById(@Param("id") long id);
    void updateUser(@Param("dto") UserDto userDto);
    void deleteUserById(@Param("id") long id);


    int insertUserRoleGroup(@Param("dto") UserRoleGroupDto userRoleGroupDto );
    List<UserRoleGroupDto> findAllUserRoleGroup();
    UserRoleGroupDto findUserRoleGroupById(@Param("id") long id);
    UserRoleGroupDto findUserRoleGroupByUserId(@Param("id") long id);
    void updateUserRoleGroup(@Param("dto") UserRoleGroupDto userRoleGroupDto);
    void deleteUserRoleGroupById(@Param("id") long id);
    void deleteUserRoleGroupByUserId(@Param("id") long id);


    long insertUserRoom(@Param("dto") UserRoomDto userRoomDto);
    List<UserRoomDto> findAllUserRoom();
    List<UserRoomDto> findUserRoomByUserId(@Param("id") long id);
    UserRoomDto findUserRoomById(@Param("id") long id);
    void updateUserRoom(@Param("dto") UserRoomDto userRoomDto);
    void deleteUserRoomById(@Param("id") long id);
    void deleteUserRoomByUserId(@Param("id") long id);
}
