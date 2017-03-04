package com.building.dto;

import com.building.dto.common.DefaultObjectDto;
import com.building.dto.master.MasterNewsDto;

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
public class NewsDetailDto extends DefaultObjectDto {
    private Long newsDetailCode;
    private Long newsCode;
    private Long refNewsCode;
    private MasterNewsDto masterNewsDto;

    public Long getNewsDetailCode() {
        return newsDetailCode;
    }

    public void setNewsDetailCode(Long newsDetailCode) {
        this.newsDetailCode = newsDetailCode;
    }

    public Long getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(Long newsCode) {
        this.newsCode = newsCode;
    }

    public Long getRefNewsCode() {
        return refNewsCode;
    }

    public void setRefNewsCode(Long refNewsCode) {
        this.refNewsCode = refNewsCode;
    }

    public MasterNewsDto getMasterNewsDto() {
        return masterNewsDto;
    }

    public void setMasterNewsDto(MasterNewsDto masterNewsDto) {
        this.masterNewsDto = masterNewsDto;
    }
}
