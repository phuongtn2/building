package com.building.dto;

/**
 * Created by Giang.DaoTu on 1/10/2017.
 */
public class ServiceAsset {
    private Integer serviceAssetCode;
    private Integer serviceCode;
    private Integer assetCode;

    public Integer getServiceAssetCode() {
        return serviceAssetCode;
    }

    public void setServiceAssetCode(Integer serviceAssetCode) {
        this.serviceAssetCode = serviceAssetCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(Integer assetCode) {
        this.assetCode = assetCode;
    }
}
