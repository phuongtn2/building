package com.building.dto;

/**
 * Created by Giang.DaoTu on 1/10/2017.
 */
public class BookingServiceService {
    private Integer t_bookingServiceServiceCode;
    private Integer t_bookingServiceCode;
    private Integer serviceCode;
    private String assetCode;

    public Integer getT_bookingServiceServiceCode() {
        return t_bookingServiceServiceCode;
    }

    public void setT_bookingServiceServiceCode(Integer t_bookingServiceServiceCode) {
        this.t_bookingServiceServiceCode = t_bookingServiceServiceCode;
    }

    public Integer getT_bookingServiceCode() {
        return t_bookingServiceCode;
    }

    public void setT_bookingServiceCode(Integer t_bookingServiceCode) {
        this.t_bookingServiceCode = t_bookingServiceCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }
}
