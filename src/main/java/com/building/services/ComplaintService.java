package com.building.services;

import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterComplaintDto;
import com.building.services.error.ServiceException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ComplaintService {
    long insertComplaint(MasterComplaintDto complaintDto) throws ServiceException;
    List<MasterComplaintDto> findAllComplaint() throws ServiceException;
    List<TransferComplaintDto> findAllTComplaint(List<Long> complaintCode) throws ServiceException;
    List<TransferReplyDto> findAllTReply(List<Long> parentComplaintCode) throws ServiceException;
    long updateFollowStatus(MasterComplaintDto complaintDto) throws ServiceException;
    MasterComplaintDto findById(long id) throws ServiceException;
   /* void update(NewsDto newsDto) throws ServiceException;
    void deleteById(long id) throws ServiceException;*/
    List<MasterComplaintDto> findAllComplaintHistory(AuthorizedUserInfo aui, boolean per) throws ServiceException;
    long insertTComplaint(TransferComplaintDto transferComplaintDto) throws ServiceException;
}
