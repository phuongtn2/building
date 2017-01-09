package com.building.dto;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class FloorDto extends DefaultObjectDto{
    private Long floorCode;
    private Integer floorSeq;
    private String floorAlias;
    private Byte floorType;
    private Long buildingCode;
    private Integer totalRoom;

    public Long getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(Long floorCode) {
        this.floorCode = floorCode;
    }

    public Integer getFloorSeq() {
        return floorSeq;
    }

    public void setFloorSeq(Integer floorSeq) {
        this.floorSeq = floorSeq;
    }

    public String getFloorAlias() {
        return floorAlias;
    }

    public void setFloorAlias(String floorAlias) {
        this.floorAlias = floorAlias;
    }

    public Byte getFloorType() {
        return floorType;
    }

    public void setFloorType(Byte floorType) {
        this.floorType = floorType;
    }

    public Long getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Long buildingCode) {
        this.buildingCode = buildingCode;
    }

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }
}
