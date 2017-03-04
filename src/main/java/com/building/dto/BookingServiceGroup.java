package com.building.dto;

/**
 * Created by Giang.DaoTu on 1/10/2017.
 */
public class BookingServiceGroup {
    private Long bookServiceCode;
    private Long serviceCode;


    public Long getBookServiceCode() {
        return bookServiceCode;
    }

    public void setBookServiceCode(Long bookServiceCode) {
        this.bookServiceCode = bookServiceCode;
    }

    public Long getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Long serviceCode) {
        this.serviceCode = serviceCode;
    }
}
