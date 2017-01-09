package com.building.services.impl;

import com.building.dto.MasterAssetDto;
import com.building.mapper.MasterAssetMapper;
import com.building.services.MasterAssetService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class MasterAssetServiceImpl implements MasterAssetService {
    @Autowired
    private MasterAssetMapper masterAssetMapper;

    @Override
    public long insertMasterAsset(MasterAssetDto masterAssetDto) throws ServerException {
        return masterAssetMapper.insertMasterAsset(masterAssetDto);
    }

    @Override
    public List<MasterAssetDto> findAll() throws ServerException {
        return masterAssetMapper.findAll();
    }

    @Override
    public MasterAssetDto findById(long id) throws ServerException {
        return masterAssetMapper.findById(id);
    }

    @Override
    public void update(MasterAssetDto masterAssetDto) throws ServerException {
        masterAssetMapper.update(masterAssetDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        masterAssetMapper.deleteById(id);
    }
}
