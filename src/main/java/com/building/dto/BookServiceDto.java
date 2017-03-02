package com.building.dto;

import com.building.dto.common.DefaultObjectDto;

import java.util.Date;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class BookServiceDto extends DefaultObjectDto {
    private Long bookServiceCode;
    private Integer userId;
    private Date bookFrom;
    private Date bookTo;
    private Byte status;
    private Byte followStatus;
    private String totalPrice;
    private String memo;

    public Long getBookServiceCode() {
        return bookServiceCode;
    }

    public void setBookServiceCode(Long bookServiceCode) {
        this.bookServiceCode = bookServiceCode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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
}
