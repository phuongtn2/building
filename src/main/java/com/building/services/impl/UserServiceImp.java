package com.building.services.impl;

import com.building.dto.login.UserDto;
import com.building.dto.login.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import com.building.mapper.UserMapper;
import com.building.services.UserService;
import com.dropbox.core.ServerException;
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
    public int insertUser(UserDto userDto) throws ServerException {
        return managerUserMapper.insertUser(userDto);
    }

    @Override
    public List<UserDto> findAllUser() throws ServerException {
        return managerUserMapper.findAllUser();
    }

    @Override
    public UserDto findUserById(long id) throws ServerException {
        return managerUserMapper.findUserById(id);
    }

    @Override
    public void updateUser(UserDto userDto) throws ServerException {
        managerUserMapper.updateUser(userDto);
    }

    @Override
    public void deleteUserById(long id) throws ServerException {
        managerUserMapper.deleteUserById(id);
    }

    @Override
    public int insertUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServerException {
        return managerUserMapper.insertUserRoleGroup(userRoleGroupDto);
    }

    @Override
    public List<UserRoleGroupDto> findAllUserRoleGroup() throws ServerException {
        return managerUserMapper.findAllUserRoleGroup();
    }

    @Override
    public UserRoleGroupDto findUserRoleGroupById(long id) throws ServerException {
        return managerUserMapper.findUserRoleGroupById(id);
    }

    @Override
    public UserRoleGroupDto findUserRoleGroupByUserId(long id) throws ServerException {
        return managerUserMapper.findUserRoleGroupByUserId(id);
    }

    @Override
    public void updateUserRoleGroup(UserRoleGroupDto userRoleGroupDto) throws ServerException {
        managerUserMapper.updateUserRoleGroup(userRoleGroupDto);
    }

    @Override
    public void deleteUserRoleGroupById(long id) throws ServerException {
        managerUserMapper.deleteUserRoleGroupById(id);
    }

    @Override
    public void deleteUserRoleGroupByUserId(long id) throws ServerException {
        managerUserMapper.deleteUserRoleGroupByUserId(id);
    }

    @Override
    public long insertUserRoom(UserRoomDto userRoomDto) throws ServerException {
        return managerUserMapper.insertUserRoom(userRoomDto);
    }

    @Override
    public void deleteUserRoomById(long id) throws ServerException {
        managerUserMapper.deleteUserRoomById(id);
    }

    @Override
    public List<UserRoomDto> findAllUserRoom() throws ServerException {
        return managerUserMapper.findAllUserRoom();
    }

    @Override
    public List<UserRoomDto> findUserRoomByUserId(long id)throws ServerException {
        return managerUserMapper.findUserRoomByUserId(id);
    }

    @Override
    public UserRoomDto findUserRoomById(long id) throws ServerException {
        return managerUserMapper.findUserRoomById(id);
    }

    @Override
    public void updateUserRoom(UserRoomDto userRoomDto) throws ServerException {
        managerUserMapper.updateUserRoom(userRoomDto);
    }

    @Override
    public void deleteUserRoomByUserId(long id) throws ServerException {
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
