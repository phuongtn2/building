package com.building.mapper;

import com.building.dto.MasterServicesDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public interface ManagerMasterServicesMapper {
    long insertMasterServices(@Param("dto")MasterServicesDto masterServicesDto);
    List<MasterServicesDto> findAll();
    MasterServicesDto findById(@Param("id") long id);
    void update(@Param("dto") MasterServicesDto masterServicesDto);
    void deleteById(@Param("id") long id);
}
