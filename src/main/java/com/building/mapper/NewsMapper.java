package com.building.mapper;

import com.building.dto.NewsDetailDto;
import com.building.dto.NewsNewsDetailDto;
import com.building.dto.master.MasterNewsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface NewsMapper {
    long insertNews(@Param("dto") MasterNewsDto newsDto);
    List<MasterNewsDto> findAll();
    List<NewsDetailDto> findByNewsId(@Param("id") long id);
    MasterNewsDto findById(@Param("id") long id);
    void update(@Param("dto") MasterNewsDto newsDto);
    void deleteById(@Param("id") long id);

}
