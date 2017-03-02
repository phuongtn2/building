package com.building.dto;

import com.building.dto.common.DefaultObjectDto;
import com.building.dto.master.MasterNewsDto;

import java.util.List;

/**
 * Created by Phuongtn2 on 11/15/2016.
 */
public class NewsNewsDetailDto extends DefaultObjectDto {
    private MasterNewsDto masterNewsDto;
    private List<NewsDetailDto> newsDetailDtoList;

    public MasterNewsDto getMasterNewsDto() {
        return masterNewsDto;
    }

    public void setMasterNewsDto(MasterNewsDto masterNewsDto) {
        this.masterNewsDto = masterNewsDto;
    }

    public List<NewsDetailDto> getNewsDetailDtoList() {
        return newsDetailDtoList;
    }

    public void setNewsDetailDtoList(List<NewsDetailDto> newsDetailDtoList) {
        this.newsDetailDtoList = newsDetailDtoList;
    }
}
