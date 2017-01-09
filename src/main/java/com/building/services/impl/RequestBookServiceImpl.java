package com.building.services.impl;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.BookServiceDto;
import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;

import com.building.mapper.RequestBookMapper;
import com.building.services.RequestBookService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class RequestBookServiceImpl implements RequestBookService {
    @Autowired
    private RequestBookMapper requestBookMapper;

    @Override
    public long insertBook(BookServiceDto bookServiceDto) {
        return requestBookMapper.insertBook(bookServiceDto);
    }

    @Override
    public List<BookServiceDto> findAllBook() {
        return requestBookMapper.findAllBook();
    }

    @Override
    public void updateBook(BookServiceDto bookServiceDto) {
        requestBookMapper.updateBook(bookServiceDto);
    }

    @Override
    public BookServiceDto findById(long id) {
        return requestBookMapper.findById(id);
    }

    @Override
    public void deleteById(long id) {
        requestBookMapper.deleteById(id);
    }
}
