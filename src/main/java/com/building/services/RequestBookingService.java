package com.building.services;

import com.building.dto.BookingServiceDto;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface RequestBookingService {
    void deleteById(long id);
    List<BookingServiceDto> findAllBooking ();
    List<String> findAllServiceByBookingId (long id);
    List<String> findAllAssetByBookingId (long id);
}
