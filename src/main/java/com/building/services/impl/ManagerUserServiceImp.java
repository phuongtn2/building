package com.building.services.impl;

import com.building.dto.UserDto;
import com.building.dto.UserRoleGroupDto;
import com.building.dto.UserRoomDto;
import com.building.mapper.ManagerUserMapper;
import com.building.services.ManagerUserService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */
@Service
public class ManagerUserServiceImp implements ManagerUserService {

    @Autowired
    private ManagerUserMapper managerUserMapper;

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
}
