package com.building.services;

import com.building.dto.NewsDto;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface NewsService {
    long insertNews(NewsDto newsDto) throws ServiceException;
    List<NewsDto> findAll() throws ServiceException;
    NewsDto findById(long id) throws ServiceException;
    void update(NewsDto newsDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;
}
