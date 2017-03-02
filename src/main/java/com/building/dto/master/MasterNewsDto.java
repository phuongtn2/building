package com.building.dto.master;

import com.building.dto.common.DefaultObjectDto;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public class MasterNewsDto extends DefaultObjectDto {
    private Long newCode;
    private Byte newType;
    private String newHeader;
    private String newShorter;
    private String newContent;

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public Long getNewCode() {
        return newCode;
    }

    public void setNewCode(Long newCode) {
        this.newCode = newCode;
    }

    public Byte getNewType() {
        return newType;
    }

    public void setNewType(Byte newType) {
        this.newType = newType;
    }

    public String getNewHeader() {
        return newHeader;
    }

    public void setNewHeader(String newHeader) {
        this.newHeader = newHeader;
    }

    public String getNewShorter() {
        return newShorter;
    }

    public void setNewShorter(String newShorter) {
        this.newShorter = newShorter;
    }


}
