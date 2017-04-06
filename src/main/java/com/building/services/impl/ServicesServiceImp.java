package com.building.services.impl;

import com.building.dto.master.MasterAssetDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterServicesDto;
import com.building.mapper.ServicesMapper;
import com.building.services.ServicesService;

import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
@Service
public class ServicesServiceImp implements ServicesService {
    @Autowired
    private ServicesMapper managerMasterServicesMapper;

    @Override
    public long insertMasterServices(MasterServicesDto masterServicesDto) throws ServiceException {
        return managerMasterServicesMapper.insertMasterServices(masterServicesDto);
    }

    @Override
    public List<MasterServicesDto> findAll() throws ServiceException {
        return managerMasterServicesMapper.findAll();
    }

    @Override
    public MasterServicesDto findById(long id) throws ServiceException {
        return managerMasterServicesMapper.findById(id);
    }

    @Override
    public void update(MasterServicesDto masterServicesDto) throws ServiceException {
        managerMasterServicesMapper.update(masterServicesDto);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        managerMasterServicesMapper.deleteById(id);
    }

    @Override
    public List<MasterBuildingDto> findAllBuilding() throws ServiceException {
        return managerMasterServicesMapper.findAllBuilding();
    }

}
