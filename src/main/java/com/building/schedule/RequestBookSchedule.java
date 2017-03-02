package com.building.schedule;

import com.building.dto.BookServiceDto;
import com.building.mapper.RequestBookMapper;
import com.building.services.RequestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface RequestBookSchedule {
    public abstract List<Object> getRequestBookingList();
}
