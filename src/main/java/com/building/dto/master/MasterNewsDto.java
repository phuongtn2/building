package com.building.dto.master;

import com.building.dto.common.DefaultObjectDto;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public class MasterNewsDto extends DefaultObjectDto {
    private Long newsCode;
    private Byte newsType;
    private String newsHeader;
    private String newsShorter;
    private String newsContent;

    public Long getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(Long newsCode) {
        this.newsCode = newsCode;
    }

    public Byte getNewsType() {
        return newsType;
    }

    public void setNewsType(Byte newsType) {
        this.newsType = newsType;
    }

    public String getNewsHeader() {
        return newsHeader;
    }

    public void setNewsHeader(String newsHeader) {
        this.newsHeader = newsHeader;
    }

    public String getNewsShorter() {
        return newsShorter;
    }

    public void setNewsShorter(String newsShorter) {
        this.newsShorter = newsShorter;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
