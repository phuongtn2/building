package com.building.services.impl;

import com.building.dto.MasterServicesDto;
import com.building.mapper.ManagerMasterServicesMapper;
import com.building.services.ManagerMasterServicesService;

import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
@Service
public class ManagerMasterServicesImp implements ManagerMasterServicesService {
    @Autowired
    private ManagerMasterServicesMapper managerMasterServicesMapper;

    @Override
    public long insertMasterServices(MasterServicesDto masterServicesDto) throws ServerException {
        return managerMasterServicesMapper.insertMasterServices(masterServicesDto);
    }

    @Override
    public List<MasterServicesDto> findAll() throws ServerException {
        return managerMasterServicesMapper.findAll();
    }

    @Override
    public MasterServicesDto findById(long id) throws ServerException {
        return managerMasterServicesMapper.findById(id);
    }

    @Override
    public void update(MasterServicesDto masterServicesDto) throws ServerException {
        managerMasterServicesMapper.update(masterServicesDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        managerMasterServicesMapper.deleteById(id);
    }
}
