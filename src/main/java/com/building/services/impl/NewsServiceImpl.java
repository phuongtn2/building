package com.building.services.impl;

import com.building.dto.master.MasterNewsDto;
import com.building.mapper.NewsMapper;
import com.building.services.NewsService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public long insertNews(MasterNewsDto newsDto) throws ServiceException {
        return newsMapper.insertNews(newsDto);
    }

    @Override
    public List<MasterNewsDto> findAll() throws ServiceException {
        return newsMapper.findAll();
    }

    @Override
    public MasterNewsDto findById(long id) throws ServiceException {
        return newsMapper.findById(id);
    }

    @Override
    public void update(MasterNewsDto newsDto) throws ServiceException {
        newsMapper.update(newsDto);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        newsMapper.deleteById(id);
    }


}
