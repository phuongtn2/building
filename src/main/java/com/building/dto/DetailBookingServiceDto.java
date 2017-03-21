package com.building.dto;

import com.building.dto.common.DefaultObjectDto;

import java.util.List;


/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class DetailBookingServiceDto extends DefaultObjectDto {
    private BookingServiceDto bookingServiceDto;
    private String serviceOrAssetName;

    public BookingServiceDto getBookingServiceDto() {
        return bookingServiceDto;
    }

    public void setBookingServiceDto(BookingServiceDto bookingServiceDto) {
        this.bookingServiceDto = bookingServiceDto;
    }

    public String getServiceOrAssetName() {
        return serviceOrAssetName;
    }

    public void setServiceOrAssetName(String serviceOrAssetName) {
        this.serviceOrAssetName = serviceOrAssetName;
    }
}
