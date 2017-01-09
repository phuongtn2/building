package com.building.dto;

import java.util.List;

/**
 * Created by phuongtn2 on 11/15/2016.
 */
public class TransferComplaintDto {
    private long tComplaintCode;
    private long complaintCode;
    private long parentComplaintCode;
    private int userId;
    private int tranSeq;
    private String message;
    List<TransferReplyDto> tReplyDtoList;
    private String userName;

    public long gettComplaintCode() {
        return tComplaintCode;
    }

    public void settComplaintCode(long tComplaintCode) {
        this.tComplaintCode = tComplaintCode;
    }

    public long getComplaintCode() {
        return complaintCode;
    }

    public void setComplaintCode(long complaintCode) {
        this.complaintCode = complaintCode;
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

    public long getParentComplaintCode() {
        return parentComplaintCode;
    }

    public void setParentComplaintCode(long parentComplaintCode) {
        this.parentComplaintCode = parentComplaintCode;
    }

    public List<TransferReplyDto> gettReplyDtoList() {
        return tReplyDtoList;
    }

    public void settReplyDtoList(List<TransferReplyDto> tReplyDtoList) {
        this.tReplyDtoList = tReplyDtoList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
