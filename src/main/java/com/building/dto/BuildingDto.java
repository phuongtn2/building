package com.building.dto;

import java.util.Date;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public class BuildingDto extends DefaultObjectDto{
    private Long buildingCode;
    private String buildingName;
    private Integer totalFloor;
    private Integer totalRoom;
    private String description;

    public Long getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Long buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
