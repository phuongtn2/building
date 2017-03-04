package com.building.mapper;
import org.apache.ibatis.annotations.Param;


public interface RequestBookingMapper {
    void deleteById(@Param("id") long id);
}
