package com.building.services.impl;

import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.building.mapper.BuildingMapper;
import com.building.services.BuildingService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingMapper managerBuildingMapper;
    @Override
    public long insertBuilding(MasterBuildingDto buildingDto) throws ServerException {
        return managerBuildingMapper.insertBuilding(buildingDto);
    }
    @Override
    public List<MasterBuildingDto> findAll() throws ServerException {
        return managerBuildingMapper.findAll();
    }

    @Override
    public MasterBuildingDto findById(long id) throws ServerException {
        return managerBuildingMapper.findById(id);
    }

    @Override
    public void update(MasterBuildingDto buildingDto) throws ServerException {
        managerBuildingMapper.update(buildingDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        managerBuildingMapper.deleteById(id);
    }

    //Floor
    @Override
    public List<MasterFloorDto> findAllFloorByBuildingId(long buildingId) throws ServerException {
        return managerBuildingMapper.findAllFloorByBuildingId(buildingId);
    }

    @Override
    public List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServerException {
        return managerBuildingMapper.findAllFloorIdByBuildingId(buildingId);
    }

    @Override
    public long insertFloor(MasterFloorDto floorDto) throws ServerException {
        return managerBuildingMapper.insertFloor(floorDto);
    }

    @Override
    public MasterFloorDto findFloorById(long id) throws ServerException {
        return managerBuildingMapper.findFloorById(id);
    }

    @Override
    public void updateFloor(MasterFloorDto floorDto) throws ServerException {
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
    public List<MasterRoomDto> findAllRoomByFloorId(long floorId) throws ServerException {
        return managerBuildingMapper.findAllRoomByFloorId(floorId);
    }

    @Override
    public long insertRoom(MasterRoomDto roomDto) throws ServerException {
        return managerBuildingMapper.insertRoom(roomDto);
    }

    @Override
    public MasterRoomDto findRoomById(long id) throws ServerException {
        return managerBuildingMapper.findRoomById(id);
    }

    @Override
    public void updateRoom(MasterRoomDto roomDto) throws ServerException {
        managerBuildingMapper.updateRoom(roomDto);
    }
}
