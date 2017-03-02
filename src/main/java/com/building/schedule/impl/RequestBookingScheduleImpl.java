package com.building.schedule.impl;

import com.building.schedule.RequestBookingSchedule;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Component("requestBookingScheduler")
public class RequestBookingScheduleImpl implements RequestBookingSchedule {

    @Override
    public List<Object> getRequestBookingList() {
        return null;
    }
}
