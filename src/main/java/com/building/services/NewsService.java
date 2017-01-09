package com.building.services;

import com.building.dto.NewsDto;
import com.dropbox.core.ServerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface NewsService {
    long insertNews(NewsDto newsDto) throws ServerException;
    List<NewsDto> findAll() throws ServerException;
    NewsDto findById(long id) throws ServerException;
    void update(NewsDto newsDto) throws ServerException;
    void deleteById(long id) throws ServerException;
}
