package com.building.services;

import com.building.dto.BookingServiceDto;
import com.building.dto.BookingServiceGroup;
import com.building.services.error.ServiceException;
import com.building.dto.master.MasterAssetDto;
import com.building.dto.master.MasterServicesDto;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface RequestBookingService {
    void deleteById(long id);
    List<BookingServiceDto> findAllBooking();
    List<BookingServiceGroup> searchServiceGroupById(long bookingServiceCode, boolean isNew) throws ServiceException;
    List<BookingServiceDto> searchListByUserId(long userId) throws ServiceException;
    List<BookingServiceDto> findAllNewBooking ();
    //List<MasterServicesDto> findAllServiceByBookingId (long id);
    //List<MasterAssetDto> findAllAssetByBookingId (long id);
    List<String> findNameByBookingServiceCode (long id, byte option);

}
