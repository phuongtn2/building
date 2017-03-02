package com.building.schedule.impl;

import com.building.dto.BookServiceDto;
import com.building.mapper.RequestBookMapper;
import com.building.schedule.RequestBookSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Component("requestBookingScheduler")
public class RequestBookScheduleImpl implements RequestBookSchedule {

    @Override
    public List<Object> getRequestBookingList() {
        return null;
    }
}
