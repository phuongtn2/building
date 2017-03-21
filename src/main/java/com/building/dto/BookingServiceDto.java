package com.building.dto;

import com.building.dto.common.DefaultObjectDto;

import java.util.Date;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class BookingServiceDto extends DefaultObjectDto {
    private Long bookingServiceCode;
    private Integer userId;
    private Date bookFrom;
    private Date bookTo;
    private Byte status;
    private Byte followStatus;
    private Float totalPrice;
    private byte option;

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    private String memo;

    public Long getBookingServiceCode() {
        return bookingServiceCode;
    }

    public void setBookingServiceCode(Long bookingServiceCode) {
        this.bookingServiceCode = bookingServiceCode;
    }



    public String getText(){
        return memo;
    }

    public Date getStart_date(){
        return bookFrom;
    }

    public Date getEnd_date(){
        return bookTo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public byte getOption() {
        return option;
    }

    public void setOption(byte option) {
        this.option = option;
    }
}
