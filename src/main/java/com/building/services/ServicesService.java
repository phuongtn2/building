package com.building.services;

import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterServicesDto;
import com.dropbox.core.ServerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */

public interface ServicesService {
    long insertMasterServices(MasterServicesDto masterServicesDto) throws ServerException;
    List<MasterServicesDto> findAll() throws ServerException;
    MasterServicesDto findById(long id) throws ServerException;
    void update(MasterServicesDto masterServicesDto) throws ServerException;
    void deleteById(long id) throws ServerException;
    List<MasterBuildingDto> findAllBuilding() throws ServerException;
}
