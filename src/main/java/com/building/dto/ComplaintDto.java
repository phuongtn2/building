package com.building.dto;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class ComplaintDto extends DefaultObjectDto{
    private long complaintCode;
    private String title;
    private byte followStatus;
    private long serviceCode;
    private String userName;

    public long getComplaintCode() {
        return complaintCode;
    }

    public void setComplaintCode(long complaintCode) {
        this.complaintCode = complaintCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(byte followStatus) {
        this.followStatus = followStatus;
    }

    public long getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(long serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
