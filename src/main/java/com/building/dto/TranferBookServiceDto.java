package com.building.dto;

/**
 * Created by Giang.DaoTu on 11/29/2016.
 */
public class TranferBookServiceDto {
    private String servicePrice;
    private BookingServiceDto bookServiceDto;

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BookingServiceDto getBookServiceDto() {
        return bookServiceDto;
    }

    public void setBookServiceDto(BookingServiceDto bookServiceDto) {
        this.bookServiceDto = bookServiceDto;
    }
}
