package com.building.dto;

/**
 * Created by Giang.DaoTu on 12/15/2016.
 */
public class RoomDto extends DefaultObjectDto{
    private Long roomCode;
    private String roomAlias;
    private Long floorCode;
    private Integer count;
    private Byte status;

    public Long getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Long roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomAlias() {
        return roomAlias;
    }

    public void setRoomAlias(String roomAlias) {
        this.roomAlias = roomAlias;
    }

    public Long getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(Long floorCode) {
        this.floorCode = floorCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
