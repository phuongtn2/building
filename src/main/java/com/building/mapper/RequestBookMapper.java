package com.building.mapper;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.BookServiceDto;
import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RequestBookMapper {
    long insertBook(@Param("dto") BookServiceDto bookServiceDto);
    List<BookServiceDto> findAllBook();
    void updateBook(@Param("dto") BookServiceDto bookServiceDto);
    BookServiceDto findById(@Param("id") long id);
    void deleteById(@Param("id") long id);
}
