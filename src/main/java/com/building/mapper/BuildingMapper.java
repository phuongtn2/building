package com.building.mapper;

import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface BuildingMapper {
    long insertBuilding(@Param("dto")MasterBuildingDto buildingDto);
    List<MasterBuildingDto> findAll();
    MasterBuildingDto findById(@Param("id") long id);
    void update(@Param("dto") MasterBuildingDto buildingDto);
    void deleteById(@Param("id") long id);
    //Floor
    List<MasterFloorDto> findAllFloorByBuildingId(@Param("id") long buildingId);
    long insertFloor(@Param("dto") MasterFloorDto floorDto);
    MasterFloorDto findFloorById(@Param("id") long id);
    void updateFloor(@Param("dto") MasterFloorDto floorDto);
    void deleteFloorById(@Param("id") long id);
    List<Long> findAllFloorIdByBuildingId(@Param("id") long id);
    void deleteFloorByBuildingId(@Param("id") long id);
    //Room
    List<MasterRoomDto> findAllRoomByFloorId(@Param("id") long floorId);
    long insertRoom(@Param("dto") MasterRoomDto roomDto);
    MasterRoomDto findRoomById(@Param("id") long id);
    void updateRoom(@Param("dto") MasterRoomDto roomDto);
    void deleteRoomById(@Param("id") long id);
    void deleteRoomByFloorId(@Param("listId") List<Long> listId);
    List<MasterBuildingDto> findByBuildingName(@Param("buildingName") String buildingName);
}
