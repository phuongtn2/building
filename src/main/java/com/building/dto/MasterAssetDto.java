package com.building.dto;

import java.util.Date;

/**
 * Created by phuongtn2 on 11/14/2016.
 */
public class MasterAssetDto extends DefaultObjectDto{
    private Long buildingCode;
    private Long assetCode;
    private Byte assetType;
    private String assetName;
    private Date dateFrom;
    private Date dateTo;

    public Long getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Long buildingCode) {
        this.buildingCode = buildingCode;
    }

    public Long getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(Long assetCode) {
        this.assetCode = assetCode;
    }

    public Byte getAssetType() {
        return assetType;
    }

    public void setAssetType(Byte assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
