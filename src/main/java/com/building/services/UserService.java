package com.building.services;

import com.building.dto.login.UserDto;
import com.building.dto.login.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import com.dropbox.core.ServerException;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */
public interface UserService {
    int insertUser(UserDto userDto) throws ServerException;
    List<UserDto> findAllUser() throws ServerException;
    UserDto findUserById(long id) throws ServerException;
    void updateUser(UserDto userDto) throws ServerException;
    void deleteUserById(long id) throws ServerException;

    int insertUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServerException;
    List<UserRoleGroupDto> findAllUserRoleGroup() throws ServerException;
    UserRoleGroupDto findUserRoleGroupById(long id) throws ServerException;
    UserRoleGroupDto findUserRoleGroupByUserId(long id) throws ServerException;
    void updateUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServerException;
    void deleteUserRoleGroupById(long id) throws ServerException;
    void deleteUserRoleGroupByUserId(long id) throws ServerException;

    long insertUserRoom(UserRoomDto userRoomDto) throws ServerException;
    List<UserRoomDto> findAllUserRoom() throws ServerException;
    List<UserRoomDto> findUserRoomByUserId(long id) throws ServerException;
    UserRoomDto findUserRoomById(long id) throws ServerException;
    void updateUserRoom(UserRoomDto userRoomDto) throws ServerException;
    void deleteUserRoomById(long id) throws ServerException;
    void deleteUserRoomByUserId(long id) throws ServerException;

    int updateFcmToken(int userId, String fcmToken);

    List<String> getFcmToken(int userId);

    int removeFcmToken(int userId, String fcmToken);
}
