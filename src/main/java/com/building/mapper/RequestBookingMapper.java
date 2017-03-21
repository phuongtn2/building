package com.building.mapper;
import com.building.dto.BookingServiceDto;
import com.building.dto.master.MasterAssetDto;
import com.building.dto.master.MasterServicesDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RequestBookingMapper {
    void deleteById(@Param("id") long id);

    List<BookingServiceDto> findAllBooking();

    //List<MasterServicesDto> findAllServiceByBookingId (@Param("id") long id);
    //List<MasterAssetDto> findAllAssetByBookingId (@Param("id") long id);
    List<String> findNameByBookingServiceCode (@Param("bookingServiceCode") long bookingServiceCode, @Param("option") byte option);

}
