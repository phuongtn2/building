package com.building.mapper;
import com.building.dto.BookingServiceDto;
import com.building.dto.BookingServiceGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RequestBookingMapper {
    void deleteById(@Param("id") long id);
    List<BookingServiceDto> findAllBooking();
    List<BookingServiceGroup> searchServiceGroupById(@Param("bookingServiceCode") long bookingServiceCode, @Param("isNew") boolean isNew);
    List<BookingServiceDto> searchListByUserId(@Param("userId") long userId);
}
