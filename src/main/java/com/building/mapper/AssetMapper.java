package com.building.mapper;

import com.building.dto.master.MasterAssetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface AssetMapper {
    long insertMasterAsset(@Param("dto") MasterAssetDto masterAssetDto);
    List<MasterAssetDto> findAll();
    MasterAssetDto findById(@Param("id") long id);
    void update(@Param("dto") MasterAssetDto masterAssetDto);
    void deleteById(@Param("id") long id);
    List<MasterAssetDto>findAssetsByBuildingCode(@Param("id") long id);
    List<MasterAssetDto>findByAssetName(@Param("assetName") String assetName);
    List<MasterAssetDto>listByServiceCode(@Param("serviceCode") long serviceCode);
}
