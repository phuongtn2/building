package com.building.services.impl;

import com.building.dto.master.MasterAssetDto;
import com.building.mapper.AssetMapper;
import com.building.services.AssetService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class MasterAssetServiceImpl implements AssetService {
    @Autowired
    private AssetMapper masterAssetMapper;

    @Override
    public long insertMasterAsset(MasterAssetDto masterAssetDto) throws ServiceException {
        return masterAssetMapper.insertMasterAsset(masterAssetDto);
    }

    @Override
    public List<MasterAssetDto> findAll() throws ServiceException {
        return masterAssetMapper.findAll();
    }

    @Override
    public MasterAssetDto findById(long id) throws ServiceException {
        return masterAssetMapper.findById(id);
    }

    @Override
    public void update(MasterAssetDto masterAssetDto) throws ServiceException {
        masterAssetMapper.update(masterAssetDto);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        masterAssetMapper.deleteById(id);
    }

    @Override
    public List<MasterAssetDto> findAssetsByBuildingCode(long id) throws ServiceException {
        return masterAssetMapper.findAssetsByBuildingCode(id);
    }

    @Override
    public List<MasterAssetDto> findByAssetName(String assetName) throws ServiceException {
        return masterAssetMapper.findByAssetName(assetName);
    }
    
    @Override
    public List<MasterAssetDto> listByServiceCode(long serviceCode) throws ServiceException{
        return masterAssetMapper.listByServiceCode(serviceCode);
    }
}
