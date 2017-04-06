package com.building.services.impl;

import com.building.dto.login.UserDto;
import com.building.dto.login.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import com.building.mapper.UserMapper;
import com.building.services.UserService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper managerUserMapper;

    @Override
    public int insertUser(UserDto userDto) throws ServiceException {
        return managerUserMapper.insertUser(userDto);
    }

    @Override
    public List<UserDto> findAllUser() throws ServiceException {
        return managerUserMapper.findAllUser();
    }

    @Override
    public UserDto findUserById(long id) throws ServiceException {
        return managerUserMapper.findUserById(id);
    }

    @Override
    public void updateUser(UserDto userDto) throws ServiceException {
        managerUserMapper.updateUser(userDto);
    }

    @Override
    public void deleteUserById(long id) throws ServiceException {
        managerUserMapper.deleteUserById(id);
    }

    @Override
    public int insertUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServiceException {
        return managerUserMapper.insertUserRoleGroup(userRoleGroupDto);
    }

    @Override
    public List<UserRoleGroupDto> findAllUserRoleGroup() throws ServiceException {
        return managerUserMapper.findAllUserRoleGroup();
    }

    @Override
    public UserRoleGroupDto findUserRoleGroupById(long id) throws ServiceException {
        return managerUserMapper.findUserRoleGroupById(id);
    }

    @Override
    public UserRoleGroupDto findUserRoleGroupByUserId(long id) throws ServiceException {
        return managerUserMapper.findUserRoleGroupByUserId(id);
    }

    @Override
    public void updateUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServiceException {
        managerUserMapper.updateUserRoleGroup(userRoleGroupDto);
    }

    @Override
    public void deleteUserRoleGroupById(long id) throws ServiceException {
        managerUserMapper.deleteUserRoleGroupById(id);
    }

    @Override
    public void deleteUserRoleGroupByUserId(long id) throws ServiceException {
        managerUserMapper.deleteUserRoleGroupByUserId(id);
    }

    @Override
    public long insertUserRoom(UserRoomDto userRoomDto) throws ServiceException {
        return managerUserMapper.insertUserRoom(userRoomDto);
    }

    @Override
    public void deleteUserRoomById(long id) throws ServiceException {
        managerUserMapper.deleteUserRoomById(id);
    }

    @Override
    public List<UserRoomDto> findAllUserRoom() throws ServiceException {
        return managerUserMapper.findAllUserRoom();
    }

    @Override
    public List<UserRoomDto> findUserRoomByUserId(long id)throws ServiceException {
        return managerUserMapper.findUserRoomByUserId(id);
    }

    @Override
    public UserRoomDto findUserRoomById(long id) throws ServiceException {
        return managerUserMapper.findUserRoomById(id);
    }

    @Override
    public void updateUserRoom(UserRoomDto userRoomDto) throws ServiceException {
        managerUserMapper.updateUserRoom(userRoomDto);
    }

    @Override
    public void deleteUserRoomByUserId(long id) throws ServiceException {
        managerUserMapper.deleteUserRoomByUserId(id);
    }

    @Override
    public int updateFcmToken(int userId, String fcmToken) {
        String currentToken = managerUserMapper.getFcmToken(userId);
        if(currentToken != null && !currentToken.isEmpty()){
            if(currentToken.indexOf(fcmToken) < 0){
                fcmToken = currentToken + "," + fcmToken;
                return managerUserMapper.updateFcmToken(userId, fcmToken);
            } else {
                return 2;
            }
        } else {
            return managerUserMapper.updateFcmToken(userId, fcmToken);
        }
    }

    @Override
    public List<String> getFcmToken(int userId) {
        String fcmTokens =  managerUserMapper.getFcmToken(userId);
        if(fcmTokens!=null && !fcmTokens.isEmpty()){
            String[] tokens = fcmTokens.split(",");
            if(tokens != null && tokens.length > 0)
                return Arrays.asList(tokens);
        }
        return new ArrayList<String>();
    }

    @Override
    public int removeFcmToken(int userId, String fcmToken) {
        String currentToken = managerUserMapper.getFcmToken(userId);
        if (currentToken != null && !currentToken.isEmpty()) {
            if (currentToken.indexOf(fcmToken + ",") >= 0) {
                currentToken = currentToken.replace(fcmToken + ",", "");
            } else if (currentToken.indexOf("," + fcmToken) >= 0) {
                currentToken = currentToken.replace("," + fcmToken, "");
            }else if (currentToken.indexOf(fcmToken) >= 0) {
                currentToken = currentToken.replace(fcmToken, "");
            }
            return managerUserMapper.updateFcmToken(userId, currentToken);
        }
        return 2;
    }

    @Override
    public void updatePassword(UserDto userDto){
        managerUserMapper.updatePassword(userDto);
    }
}
