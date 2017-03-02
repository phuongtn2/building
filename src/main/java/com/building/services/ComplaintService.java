package com.building.services;

import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterComplaintDto;
import com.dropbox.core.ServerException;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ComplaintService {
    long insertComplaint(MasterComplaintDto complaintDto) throws ServerException;
    List<MasterComplaintDto> findAllComplaint() throws ServerException;
    List<TransferComplaintDto> findAllTComplaint(List<Long> complaintCode) throws ServerException;
    List<TransferReplyDto> findAllTReply(List<Long> parentComplaintCode) throws ServerException;
    long updateFollowStatus(MasterComplaintDto complaintDto) throws ServerException;
    MasterComplaintDto findById(long id) throws ServerException;
   /* void update(NewsDto newsDto) throws ServerException;
    void deleteById(long id) throws ServerException;*/
    List<MasterComplaintDto> findAllComplaintHistory(AuthorizedUserInfo aui, boolean per) throws ServerException;
    long insertTComplaint(TransferComplaintDto transferComplaintDto) throws ServerException;
}
