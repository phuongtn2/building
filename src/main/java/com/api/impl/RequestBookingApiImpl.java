package com.api.impl;

import com.api.RequestBookingApi;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.BookServiceDto;
import com.building.services.RequestBookService;
import com.building.services.error.ServiceException;
import com.building.util.AuthorizeUtil;
import com.building.util.jaxrs.MultivaluedMapBeanConverter;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Giang.DaoTu on 1/3/2017.
 */
@Component
public class RequestBookingApiImpl implements RequestBookingApi {
    @Autowired
    RequestBookService requestBookService;
    @Override
    public long addRequestBooking(@Context HttpServletRequest request, MultivaluedMap<String, String> form) throws SerialException, ServiceException {
        final AuthorizedUserInfo authorizedUserInfo = AuthorizeUtil.requireAuthorizedUserInfo(request);
        final BookServiceDto bookServiceDto = MultivaluedMapBeanConverter.convert(BookServiceDto.class, form);
        bookServiceDto.setUserId(authorizedUserInfo.getUserId());
        bookServiceDto.setCreateId(authorizedUserInfo.getUserId());
        bookServiceDto.setUpdateId(authorizedUserInfo.getUserId());
        return requestBookService.insertBook(bookServiceDto);
    }

    @Override
    public List<BookServiceDto> findAllBooking(@Context HttpServletRequest request, String from, String to) throws ServerException {
        return requestBookService.findAllBook();
    }
}
