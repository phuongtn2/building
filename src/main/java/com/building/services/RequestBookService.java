package com.building.services;

import com.building.dto.BookServiceDto;
import com.building.dto.NewsDto;
import com.dropbox.core.ServerException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface RequestBookService {
    long insertBook(BookServiceDto bookServiceDto);
    List<BookServiceDto> findAllBook();
    void updateBook(BookServiceDto bookServiceDto);
    BookServiceDto findById(long id);
    void deleteById(long id);
}
