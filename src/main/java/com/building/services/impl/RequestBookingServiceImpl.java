package com.building.services.impl;

import com.building.dto.BookingServiceDto;
import com.building.dto.master.MasterAssetDto;
import com.building.dto.master.MasterServicesDto;
import com.building.mapper.RequestBookingMapper;
import com.building.services.RequestBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class RequestBookingServiceImpl implements RequestBookingService {
    @Autowired
    private RequestBookingMapper requestBookingMapper;

    @Override
    public void deleteById(long id) {
        requestBookingMapper.deleteById(id);
    }

    @Override
    public List<BookingServiceDto> findAllBooking() {
        return requestBookingMapper.findAllBooking();
    }

    @Override
    public List<String> findNameByBookingServiceCode(long id, byte option) {
        return requestBookingMapper.findNameByBookingServiceCode(id, option);
    }
}
