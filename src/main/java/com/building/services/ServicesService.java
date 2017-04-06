package com.building.services;

import com.building.dto.master.MasterAssetDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterServicesDto;
import com.building.services.error.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */

public interface ServicesService {
    long insertMasterServices(MasterServicesDto masterServicesDto) throws ServiceException;
    List<MasterServicesDto> findAll() throws ServiceException;
    MasterServicesDto findById(long id) throws ServiceException;
    void update(MasterServicesDto masterServicesDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;
    List<MasterBuildingDto> findAllBuilding() throws ServiceException;
}
