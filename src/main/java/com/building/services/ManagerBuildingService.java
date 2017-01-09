package com.building.services;

import com.building.dto.BuildingDto;
import com.building.dto.FloorDto;
import com.building.dto.RoomDto;
import com.dropbox.core.ServerException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ManagerBuildingService {
    long insertBuilding(BuildingDto buildingDto) throws ServerException;
    List<BuildingDto> findAll() throws ServerException;
    BuildingDto findById(long id) throws ServerException;
    void update(BuildingDto buildingDto) throws ServerException;
    void deleteById(long id) throws ServerException;
    //Floor
    List<FloorDto> findAllFloorByBuildingId(long buildingId) throws ServerException;
    List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServerException;
    long insertFloor(FloorDto floorDto) throws  ServerException;
    FloorDto findFloorById(long id) throws ServerException;
    void updateFloor(FloorDto floorDto) throws ServerException;
    void deleteFloorById(long id) throws ServerException;
    void deleteFloorByBuildingId(long id) throws ServerException;
    //Room
    List<RoomDto> findAllRoomByFloorId(long floorId) throws ServerException;
    long insertRoom(RoomDto roomDto) throws  ServerException;
    RoomDto findRoomById(long id) throws ServerException;
    void updateRoom(RoomDto roomDto) throws ServerException;
    void deleteRoomById(long id) throws ServerException;
    void deleteRoomByFloorId(List<Long> listFloorId) throws ServerException;
}
