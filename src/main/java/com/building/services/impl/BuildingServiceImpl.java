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
    private BuildingMapper buildingMapper;
    @Override
    public long insertBuilding(MasterBuildingDto buildingDto) throws ServerException {
        return buildingMapper.insertBuilding(buildingDto);
    }
    @Override
    public List<MasterBuildingDto> findAll() throws ServerException {
        return buildingMapper.findAll();
    }

    @Override
    public MasterBuildingDto findById(long id) throws ServerException {
        return buildingMapper.findById(id);
    }

    @Override
    public void update(MasterBuildingDto buildingDto) throws ServerException {
        buildingMapper.update(buildingDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        buildingMapper.deleteById(id);
    }

    //Floor
    @Override
    public List<MasterFloorDto> findAllFloorByBuildingId(long buildingId) throws ServerException {
        return buildingMapper.findAllFloorByBuildingId(buildingId);
    }

    @Override
    public List<Long> findAllFloorIdByBuildingId(long buildingId) throws ServerException {
        return buildingMapper.findAllFloorIdByBuildingId(buildingId);
    }

    @Override
    public long insertFloor(MasterFloorDto floorDto) throws ServerException {
        return buildingMapper.insertFloor(floorDto);
    }

    @Override
    public MasterFloorDto findFloorById(long id) throws ServerException {
        return buildingMapper.findFloorById(id);
    }

    @Override
    public void updateFloor(MasterFloorDto floorDto) throws ServerException {
        buildingMapper.updateFloor(floorDto);
    }

    @Override
    public void deleteFloorById(long id) throws ServerException {
        buildingMapper.deleteFloorById(id);
    }

    @Override
    public void deleteFloorByBuildingId(long id) throws ServerException {
        buildingMapper.deleteFloorByBuildingId(id);
    }

    //Room

    @Override
    public void deleteRoomById(long id) throws ServerException {
        buildingMapper.deleteRoomById(id);
    }

    @Override
    public void deleteRoomByFloorId(List<Long> listFloorId) throws ServerException {
        buildingMapper.deleteRoomByFloorId(listFloorId);
    }

    @Override
    public List<MasterBuildingDto> findByBuildingName(String buildingName) throws ServerException {
        return buildingMapper.findByBuildingName(buildingName);
    }

    @Override
    public List<MasterRoomDto> findAllRoomByFloorId(long floorId) throws ServerException {
        return buildingMapper.findAllRoomByFloorId(floorId);
    }

    @Override
    public long insertRoom(MasterRoomDto roomDto) throws ServerException {
        return buildingMapper.insertRoom(roomDto);
    }

    @Override
    public MasterRoomDto findRoomById(long id) throws ServerException {
        return buildingMapper.findRoomById(id);
    }

    @Override
    public void updateRoom(MasterRoomDto roomDto) throws ServerException {
        buildingMapper.updateRoom(roomDto);
    }

    @Override
    public String findBuildingNameByBuildingId(long id) throws ServerException {
        return buildingMapper.findBuildingNameByBuildingId(id);
    }

    @Override
    public String findFloorNameByFloorId(long id) throws ServerException {
        return buildingMapper.findFloorNameByFloorId(id);
    }

    @Override
    public String findRoomNameByRoomId(long id) throws ServerException {
        return buildingMapper.findRoomNameByRoomId(id);
    }
}
