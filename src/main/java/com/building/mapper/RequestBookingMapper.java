package com.building.mapper;
import com.building.dto.BookingServiceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RequestBookingMapper {
    void deleteById(@Param("id") long id);

    List<BookingServiceDto> findAllBooking();

    List<String> findAllServiceByBookingId (@Param("id") long id);
    List<String> findAllAssetByBookingId (@Param("id") long id);

}
