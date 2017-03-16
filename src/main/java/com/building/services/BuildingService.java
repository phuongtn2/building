package com.building.services;

import com.building.dto.ProfileDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.dropbox.core.ServerException;
import sun.java2d.cmm.Profile;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface BuildingService {
    long insertBuilding(MasterBuildingDto buildingDto) throws ServerException;
    List<MasterBuildingDto> findAll() throws ServerException;
    MasterBuildingDto findById(long id) throws ServerException;
    void update(MasterBuildingDto buildingDto) throws ServerException;
    void deleteById(long id) throws ServerException;
    //Floor
    List<MasterFloorDto> findAllFloorByBuildingId(long buildingId) throws ServerException;
    List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServerException;
    long insertFloor(MasterFloorDto floorDto) throws  ServerException;
    MasterFloorDto findFloorById(long id) throws ServerException;
    void updateFloor(MasterFloorDto floorDto) throws ServerException;
    void deleteFloorById(long id) throws ServerException;
    void deleteFloorByBuildingId(long id) throws ServerException;
    //Room
    List<MasterRoomDto> findAllRoomByFloorId(long floorId) throws ServerException;
    long insertRoom(MasterRoomDto roomDto) throws  ServerException;
    MasterRoomDto findRoomById(long id) throws ServerException;
    void updateRoom(MasterRoomDto roomDto) throws ServerException;
    void deleteRoomById(long id) throws ServerException;
    void deleteRoomByFloorId(List<Long> listFloorId) throws ServerException;
    List<MasterBuildingDto> findByBuildingName(String buildingName) throws ServerException;
    //For Profile
    ProfileDto findAllName (long buildingCode, long floorCode, long roomCode) throws ServerException;

}
