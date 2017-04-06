package com.building.services;

import com.building.dto.login.UserDto;
import com.building.dto.login.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */
public interface UserService {
    int insertUser(UserDto userDto) throws ServiceException;
    List<UserDto> findAllUser() throws ServiceException;
    UserDto findUserById(long id) throws ServiceException;
    void updateUser(UserDto userDto) throws ServiceException;
    void deleteUserById(long id) throws ServiceException;

    int insertUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServiceException;
    List<UserRoleGroupDto> findAllUserRoleGroup() throws ServiceException;
    UserRoleGroupDto findUserRoleGroupById(long id) throws ServiceException;
    UserRoleGroupDto findUserRoleGroupByUserId(long id) throws ServiceException;
    void updateUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServiceException;
    void deleteUserRoleGroupById(long id) throws ServiceException;
    void deleteUserRoleGroupByUserId(long id) throws ServiceException;

    long insertUserRoom(UserRoomDto userRoomDto) throws ServiceException;
    List<UserRoomDto> findAllUserRoom() throws ServiceException;
    List<UserRoomDto> findUserRoomByUserId(long id) throws ServiceException;
    UserRoomDto findUserRoomById(long id) throws ServiceException;
    void updateUserRoom(UserRoomDto userRoomDto) throws ServiceException;
    void deleteUserRoomById(long id) throws ServiceException;
    void deleteUserRoomByUserId(long id) throws ServiceException;

    int updateFcmToken(int userId, String fcmToken);

    List<String> getFcmToken(int userId);

    int removeFcmToken(int userId, String fcmToken);

    void updatePassword(UserDto userDto) throws ServerException;
}
