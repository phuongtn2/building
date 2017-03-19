package com.building.dto;

import com.building.dto.common.DefaultObjectDto;

import java.util.List;


/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class DetailBookingServiceDto extends DefaultObjectDto {
    private BookingServiceDto bookingServiceDto;
    private List <String> ServiceName;
    private List <String> AssetName;

    public BookingServiceDto getBookingServiceDto() {
        return bookingServiceDto;
    }

    public void setBookingServiceDto(BookingServiceDto bookingServiceDto) {
        this.bookingServiceDto = bookingServiceDto;
    }

    public List<String> getServiceName() {
        return ServiceName;
    }

    public void setServiceName(List<String> serviceName) {
        ServiceName = serviceName;
    }

    public List<String> getAssetName() {
        return AssetName;
    }

    public void setAssetName(List<String> assetName) {
        AssetName = assetName;
    }
}
