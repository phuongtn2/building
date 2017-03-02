package com.building.services;


import com.building.dto.master.MasterNewsDto;
import com.building.services.error.ServiceException;
import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface NewsService {
    long insertNews(MasterNewsDto newsDto) throws ServiceException;
    List<MasterNewsDto> findAll() throws ServiceException;
    MasterNewsDto findById(long id) throws ServiceException;
    void update(MasterNewsDto newsDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;
}
