package com.building.services;

import com.building.dto.ProfileDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface BuildingService {
    long insertBuilding(MasterBuildingDto buildingDto) throws ServiceException;
    List<MasterBuildingDto> findAll() throws ServiceException;
    MasterBuildingDto findById(long id) throws ServiceException;
    void update(MasterBuildingDto buildingDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;
    //Floor
    List<MasterFloorDto> findAllFloorByBuildingId(long buildingId) throws ServiceException;
    List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServiceException;
    long insertFloor(MasterFloorDto floorDto) throws  ServiceException;
    MasterFloorDto findFloorById(long id) throws ServiceException;
    void updateFloor(MasterFloorDto floorDto) throws ServiceException;
    void deleteFloorById(long id) throws ServiceException;
    void deleteFloorByBuildingId(long id) throws ServiceException;
    //Room
    List<MasterRoomDto> findAllRoomByFloorId(long floorId) throws ServiceException;
    long insertRoom(MasterRoomDto roomDto) throws  ServiceException;
    MasterRoomDto findRoomById(long id) throws ServiceException;
    void updateRoom(MasterRoomDto roomDto) throws ServiceException;
    void deleteRoomById(long id) throws ServiceException;
    void deleteRoomByFloorId(List<Long> listFloorId) throws ServiceException;
    List<MasterBuildingDto> findByBuildingName(String buildingName) throws ServiceException;
    //For Profile
    ProfileDto findAllName (long buildingCode, long floorCode, long roomCode) throws ServiceException;

}
