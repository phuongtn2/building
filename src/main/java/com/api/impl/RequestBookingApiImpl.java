/*
package com.api.impl;

import com.api.RequestBookingApi;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.BookingServiceDto;
import com.building.services.RequestBookingService;
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

*/
/**
 * Created by Giang.DaoTu on 1/3/2017.
 *//*

@Component
public class RequestBookingApiImpl implements RequestBookingApi {
    @Autowired
    RequestBookingService requestBookingService;
    @Override
    public long addRequestBooking(@Context HttpServletRequest request, MultivaluedMap<String, String> form) throws SerialException, ServiceException {
        final AuthorizedUserInfo authorizedUserInfo = AuthorizeUtil.requireAuthorizedUserInfo(request);
        final BookingServiceDto bookServiceDto = MultivaluedMapBeanConverter.convert(BookingServiceDto.class, form);
        bookServiceDto.setUserId(authorizedUserInfo.getUserId());
        bookServiceDto.setCreateId(authorizedUserInfo.getUserId());
        bookServiceDto.setUpdateId(authorizedUserInfo.getUserId());
        */
/*return requestBookingService.insertBook(bookServiceDto);*//*

        return 1;
    }

    @Override
    public List<BookingServiceDto> findAllBooking(@Context HttpServletRequest request, String from, String to) throws ServerException {
        return requestBookingService.findAllBook();
    }
}
*/
