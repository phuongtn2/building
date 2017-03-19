package com.building.dto;

/**
 * Created by Giang.DaoTu on 1/10/2017.
 */
public class BookingServiceGroup {
    private Long bookServiceCode;
    private Long serviceCode;
    private Long assetCode;
    private String content;

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

    public Long getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(Long assetCode) {
        this.assetCode = assetCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
