package com.building.services.impl;

import com.building.dto.BuildingDto;

import com.building.dto.FloorDto;
import com.building.dto.RoomDto;
import com.building.mapper.ManagerBuildingMapper;
import com.building.services.ManagerBuildingService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class ManagerBuildingServiceImpl implements ManagerBuildingService {
    @Autowired
    private ManagerBuildingMapper managerBuildingMapper;
    @Override
    public long insertBuilding(BuildingDto buildingDto) throws ServerException {
        return managerBuildingMapper.insertBuilding(buildingDto);
    }
    @Override
    public List<BuildingDto> findAll() throws ServerException {
        return managerBuildingMapper.findAll();
    }

    @Override
    public BuildingDto findById(long id) throws ServerException {
        return managerBuildingMapper.findById(id);
    }

    @Override
    public void update(BuildingDto buildingDto) throws ServerException {
        managerBuildingMapper.update(buildingDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        managerBuildingMapper.deleteById(id);
    }

    //Floor
    @Override
    public List<FloorDto> findAllFloorByBuildingId(long buildingId) throws ServerException {
        return managerBuildingMapper.findAllFloorByBuildingId(buildingId);
    }

    @Override
    public List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServerException {
        return managerBuildingMapper.findAllFloorIdByBuildingId(buildingId);
    }

    @Override
    public long insertFloor(FloorDto floorDto) throws ServerException {
        return managerBuildingMapper.insertFloor(floorDto);
    }

    @Override
    public FloorDto findFloorById(long id) throws ServerException {
        return managerBuildingMapper.findFloorById(id);
    }

    @Override
    public void updateFloor(FloorDto floorDto) throws ServerException {
        managerBuildingMapper.updateFloor(floorDto);
    }

    @Override
    public void deleteFloorById(long id) throws ServerException {
        managerBuildingMapper.deleteFloorById(id);
    }

    @Override
    public void deleteFloorByBuildingId(long id) throws ServerException {
        managerBuildingMapper.deleteFloorByBuildingId(id);
    }

    //Room

    @Override
    public void deleteRoomById(long id) throws ServerException {
        managerBuildingMapper.deleteRoomById(id);
    }

    @Override
    public void deleteRoomByFloorId(List<Long> listFloorId) throws ServerException {
        managerBuildingMapper.deleteRoomByFloorId(listFloorId);
    }

    @Override
    public List<RoomDto> findAllRoomByFloorId(long floorId) throws ServerException {
        return managerBuildingMapper.findAllRoomByFloorId(floorId);
    }

    @Override
    public long insertRoom(RoomDto roomDto) throws ServerException {
        return managerBuildingMapper.insertRoom(roomDto);
    }

    @Override
    public RoomDto findRoomById(long id) throws ServerException {
        return managerBuildingMapper.findRoomById(id);
    }

    @Override
    public void updateRoom(RoomDto roomDto) throws ServerException {
        managerBuildingMapper.updateRoom(roomDto);
    }
}
