package com.building.mapper;

import com.building.dto.NewsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface NewsMapper {
    long insertNews(@Param("dto") NewsDto newsDto);
    List<NewsDto> findAll();
    NewsDto findById(@Param("id") long id);
    void update(@Param("dto") NewsDto newsDto);
    void deleteById(@Param("id") long id);

}
