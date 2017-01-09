package com.building.mapper;

import com.building.dto.MasterAssetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface MasterAssetMapper {
    long insertMasterAsset(@Param("dto") MasterAssetDto masterAssetDto);
    List<MasterAssetDto> findAll();
    MasterAssetDto findById(@Param("id") long id);
    void update(@Param("dto") MasterAssetDto masterAssetDto);
    void deleteById(@Param("id") long id);
}
