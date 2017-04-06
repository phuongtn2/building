package com.building.services;


import com.building.dto.master.MasterAssetDto;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface AssetService {
    long insertMasterAsset(MasterAssetDto masterAssetDto) throws ServiceException;
    List<MasterAssetDto> findAll() throws ServiceException;
    MasterAssetDto findById(long id) throws ServiceException;
    void update(MasterAssetDto masterAssetDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;
    List<MasterAssetDto> findAssetsByBuildingCode(long id) throws ServiceException;
    List<MasterAssetDto> findByAssetName(String assetName) throws ServiceException;
    List<MasterAssetDto> listByServiceCode(long serviceCode) throws ServiceException;
}
