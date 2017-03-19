package com.building.services;

import com.building.dto.BookingServiceDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by phuongtn on 12/29/2016.
 */
public interface FcmService {
    void sendNotification(int userId, String fcmToken);

    void sendNotification(BookingServiceDto bookingServiceDto, List<Integer> bookingUserIds);

    void sendNotification(BookingServiceDto bookingServiceDto, List<Integer> bookingUserIds, String bookingContent);

    int storeToken(AuthorizedUserInfo authorizedUserInfo, String token) throws ServiceException;

    int removeToken(AuthorizedUserInfo authorizedUserInfo,String token) throws ServiceException;
}
