package com.api;

import com.building.dto.BookServiceDto;
import com.building.services.annotation.PermissionBinding;
import com.building.services.error.ServiceException;
import com.dropbox.core.ServerException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Giang.DaoTu on 1/3/2017.
 */
@Path("/request_booking")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@PermissionBinding
public interface RequestBookingApi {
    @POST
    @Path("/regist")
    long addRequestBooking(@Context HttpServletRequest request, MultivaluedMap<String, String> form) throws SerialException, ServiceException;

    @GET
    @Path("/list/self")
    List<BookServiceDto> findAllBooking(@Context HttpServletRequest request,@QueryParam("from") String from,@QueryParam("to") String to) throws ServerException;
}
