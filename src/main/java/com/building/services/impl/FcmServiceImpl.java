package com.building.services.impl;

import com.building.dto.BookingServiceDto;
import com.building.dto.BookingServiceGroup;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.services.FcmService;
import com.building.services.RequestBookingService;
import com.building.services.UserService;
import com.building.services.error.ServiceException;
import com.building.util.fcm.FcmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import java.io.IOException;
import java.util.*;

/**
 * Created by phuongtn on 12/29/2016.
 */
@Service
public class FcmServiceImpl implements FcmService {

    @Autowired
    private RequestBookingService requestBookingService;

    @Autowired
    private UserService userService;
    @Override
    public void sendNotification(int userId, String fcmToken) {
        try {
            List<BookingServiceDto> bookingServiceDtos = requestBookingService.searchListByUserId(userId);
            if (bookingServiceDtos != null && !bookingServiceDtos.isEmpty()) {
                String apiKey = "AAAAjr6ZX2Q:APA91bF0VTJRo-8jmXqT6hfiv9q13lGJk5JH77Cn_RSRcVgqmBYOF64ZUOAPXQlsgn-gmlio7DK8gH60Ep35KqCQwkX3qxDRfRArfp9rWAA487suX8aOvWr2GNHhYD0riqiw6SxLZXGQ";
                for (BookingServiceDto bookingServiceDto : bookingServiceDtos) {
                    Map<String, String> data = buildData(bookingServiceDto, false);
                    long ttl = ((new Date().getTime() + (60*60*60*24)) - new Date().getTime()) / 1000;
                    FcmClient.sendNotification(apiKey, data, fcmToken, ttl);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotification(BookingServiceDto bookingServiceDto, List<Integer> bookingUserIds) {
        Map<String, String> data = buildData(bookingServiceDto, true);
        executeSendingNotification(bookingServiceDto, data, bookingUserIds);
    }

    @Override
    public void sendNotification(BookingServiceDto bookingServiceDto, List<Integer> bookingUserIds, String bookingContent) {
        Map<String, String> data = buildData(bookingServiceDto, bookingContent, true);
        executeSendingNotification(bookingServiceDto, data, bookingUserIds);
    }

    private void executeSendingNotification(BookingServiceDto bookingServiceDto, Map<String, String> data, List<Integer> bookingUserIds ) {
        String apiKey = "AAAAjr6ZX2Q:APA91bF0VTJRo-8jmXqT6hfiv9q13lGJk5JH77Cn_RSRcVgqmBYOF64ZUOAPXQlsgn-gmlio7DK8gH60Ep35KqCQwkX3qxDRfRArfp9rWAA487suX8aOvWr2GNHhYD0riqiw6SxLZXGQ";
        for(int userId:bookingUserIds){
            List<String> fcmTokens = userService.getFcmToken(userId);
            if (!fcmTokens.isEmpty()) {
                long ttl = ((new Date().getTime() + (60*60*60*24)) - new Date().getTime()) / 1000;
                for (String fcmToken : fcmTokens) {
                    try {
                        FcmClient.sendNotification(apiKey, data, fcmToken, ttl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Map<String, String> buildData(BookingServiceDto bookingServiceDto, boolean isNew) {
        String serviceContent = getServiceContent(bookingServiceDto);
        return buildData(bookingServiceDto, serviceContent, isNew);
    }

    private Map<String,String> buildData(BookingServiceDto bookingServiceDto, String contentService, boolean isNew) {
        Map<String, String> data = new HashMap<>();
        data.put("content", contentService);
        data.put("subject", bookingServiceDto.getMemo());
        data.put("bookingServiceCode", String.valueOf(bookingServiceDto.getBookingServiceCode()));
        data.put("managerFullName", "");
        data.put("totalPrice", String.valueOf(bookingServiceDto.getTotalPrice()));
        data.put("userId", String.valueOf(bookingServiceDto.getUserId()));
        data.put("fullName", "");
        data.put("isNew", String.valueOf(isNew));
        return data;
    }

    private String getServiceContent(BookingServiceDto bookingServiceDto) {
        try {
            String content = "";
            List<BookingServiceGroup> serviceGroups = requestBookingService.searchServiceGroupById(bookingServiceDto.getBookingServiceCode(), true);
            for (BookingServiceGroup serviceGroup : serviceGroups) {
                String contentService = serviceGroup.getContent();
                if (contentService != null && !contentService.isEmpty())
                    content += (content == "" ? "" : "\r\n") + contentService;
            }
            return content;
        } catch (ServiceException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int storeToken(AuthorizedUserInfo userInfo, @FormParam("token") String fcmToken) throws ServiceException {
        String authToken = userInfo.getToken();
        int effected = userService.updateFcmToken(userInfo.getUserId(), fcmToken);
        if (effected > 0) {
            sendNotification(userInfo.getUserId(), fcmToken);
        }
        return effected;
    }

    @Override
    public int removeToken(AuthorizedUserInfo userInfo, @FormParam("token") String fcmToken) throws ServiceException {
        int effected = userService.removeFcmToken(userInfo.getUserId(), fcmToken);
        return effected;
    }

}
