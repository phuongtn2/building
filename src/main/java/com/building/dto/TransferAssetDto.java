package com.building.dto;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class TransferAssetDto {
    private long assetCode;
    private int userId;
    private int tranSeq;
    private String message;
    private byte followStatus;

    public long getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(long assetCode) {
        this.assetCode = assetCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTranSeq() {
        return tranSeq;
    }

    public void setTranSeq(int tranSeq) {
        this.tranSeq = tranSeq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(byte followStatus) {
        this.followStatus = followStatus;
    }
}
