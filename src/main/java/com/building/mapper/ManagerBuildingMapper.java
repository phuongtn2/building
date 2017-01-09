package com.building.mapper;

import com.building.dto.BuildingDto;
import com.building.dto.FloorDto;
import com.building.dto.RoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ManagerBuildingMapper {
    long insertBuilding(@Param("dto")BuildingDto buildingDto);
    List<BuildingDto> findAll();
    BuildingDto findById(@Param("id") long id);
    void update(@Param("dto") BuildingDto buildingDto);
    void deleteById(@Param("id") long id);
    //Floor
    List<FloorDto> findAllFloorByBuildingId(@Param("id") long buildingId);
    long insertFloor(@Param("dto") FloorDto floorDto);
    FloorDto findFloorById(@Param("id") long id);
    void updateFloor(@Param("dto") FloorDto floorDto);
    void deleteFloorById(@Param("id") long id);
    List<Long> findAllFloorIdByBuildingId(@Param("id") long id);
    void deleteFloorByBuildingId(@Param("id") long id);
    //Room
    List<RoomDto> findAllRoomByFloorId(@Param("id") long floorId);
    long insertRoom(@Param("dto") RoomDto roomDto);
    RoomDto findRoomById(@Param("id") long id);
    void updateRoom(@Param("dto") RoomDto roomDto);
    void deleteRoomById(@Param("id") long id);
    void deleteRoomByFloorId(@Param("listId") List<Long> listId);
}
