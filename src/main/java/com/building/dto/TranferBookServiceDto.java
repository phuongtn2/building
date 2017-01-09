package com.building.dto;

/**
 * Created by Giang.DaoTu on 11/29/2016.
 */
public class TranferBookServiceDto {
    private String servicePrice;
    private BookServiceDto bookServiceDto;

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BookServiceDto getBookServiceDto() {
        return bookServiceDto;
    }

    public void setBookServiceDto(BookServiceDto bookServiceDto) {
        this.bookServiceDto = bookServiceDto;
    }
}
