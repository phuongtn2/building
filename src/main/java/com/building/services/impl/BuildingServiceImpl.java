package com.building.services.impl;

import com.building.dto.ProfileDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.building.mapper.BuildingMapper;
import com.building.services.BuildingService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingMapper buildingMapper;
    @Override
    public long insertBuilding(MasterBuildingDto buildingDto) throws ServiceException {
        return buildingMapper.insertBuilding(buildingDto);
    }
    @Override
    public List<MasterBuildingDto> findAll() throws ServiceException {
        return buildingMapper.findAll();
    }

    @Override
    public MasterBuildingDto findById(long id) throws ServiceException {
        return buildingMapper.findById(id);
    }

    @Override
    public void update(MasterBuildingDto buildingDto) throws ServiceException {
        buildingMapper.update(buildingDto);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        buildingMapper.deleteById(id);
    }

    //Floor
    @Override
    public List<MasterFloorDto> findAllFloorByBuildingId(long buildingId) throws ServiceException {
        return buildingMapper.findAllFloorByBuildingId(buildingId);
    }

    @Override
    public List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServiceException {
        return buildingMapper.findAllFloorIdByBuildingId(buildingId);
    }

    @Override
    public long insertFloor(MasterFloorDto floorDto) throws ServiceException {
        return buildingMapper.insertFloor(floorDto);
    }

    @Override
    public MasterFloorDto findFloorById(long id) throws ServiceException {
        return buildingMapper.findFloorById(id);
    }

    @Override
    public void updateFloor(MasterFloorDto floorDto) throws ServiceException {
        buildingMapper.updateFloor(floorDto);
    }

    @Override
    public void deleteFloorById(long id) throws ServiceException {
        buildingMapper.deleteFloorById(id);
    }

    @Override
    public void deleteFloorByBuildingId(long id) throws ServiceException {
        buildingMapper.deleteFloorByBuildingId(id);
    }

    //Room

    @Override
    public void deleteRoomById(long id) throws ServiceException {
        buildingMapper.deleteRoomById(id);
    }

    @Override
    public void deleteRoomByFloorId(List<Long> listFloorId) throws ServiceException {
        buildingMapper.deleteRoomByFloorId(listFloorId);
    }

    @Override
    public List<MasterBuildingDto> findByBuildingName(String buildingName) throws ServiceException {
        return buildingMapper.findByBuildingName(buildingName);
    }

    @Override
    public List<MasterRoomDto> findAllRoomByFloorId(long floorId) throws ServiceException {
        return buildingMapper.findAllRoomByFloorId(floorId);
    }

    @Override
    public long insertRoom(MasterRoomDto roomDto) throws ServiceException {
        return buildingMapper.insertRoom(roomDto);
    }

    @Override
    public MasterRoomDto findRoomById(long id) throws ServiceException {
        return buildingMapper.findRoomById(id);
    }

    @Override
    public void updateRoom(MasterRoomDto roomDto) throws ServiceException {
        buildingMapper.updateRoom(roomDto);
    }

    @Override
    public ProfileDto findAllName(long buildingCode, long floorCode, long roomCode) throws ServiceException {
        return buildingMapper.findAllName(buildingCode, floorCode, roomCode);
    }

}
