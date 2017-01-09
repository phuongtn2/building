package com.building.dto;

import java.util.Date;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class BookServiceDto extends DefaultObjectDto {
    private Long t_bookServiceCode;
    private Long serviceCode;
    private Integer userId;
    private Long assetCode;
    private Date bookFrom;
    private Date bookTo;
    private Byte status;
    private Byte followStatus;
    private String servicePrice;
    private String memo;

    public Date getStart_date(){
        return bookFrom;
    }
    public Date getEnd_date(){
        return bookTo;
    }
    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Long getT_bookServiceCode() {
        return t_bookServiceCode;
    }

    public void setT_bookServiceCode(Long t_bookServiceCode) {
        this.t_bookServiceCode = t_bookServiceCode;
    }

    public Long getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Long serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(Long assetCode) {
        this.assetCode = assetCode;
    }

    public Date getBookFrom() {
        return bookFrom;
    }

    public void setBookFrom(Date bookFrom) {
        this.bookFrom = bookFrom;
    }

    public Date getBookTo() {
        return bookTo;
    }

    public void setBookTo(Date bookTo) {
        this.bookTo = bookTo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Byte followStatus) {
        this.followStatus = followStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
