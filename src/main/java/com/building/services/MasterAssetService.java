package com.building.services;


import com.building.dto.MasterAssetDto;
import com.building.dto.MasterServicesDto;
import com.dropbox.core.ServerException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface MasterAssetService {
    long insertMasterAsset(MasterAssetDto masterAssetDto) throws ServerException;
    List<MasterAssetDto> findAll() throws ServerException;
    MasterAssetDto findById(long id) throws ServerException;
    void update(MasterAssetDto masterAssetDto) throws ServerException;
    void deleteById(long id) throws ServerException;

}
