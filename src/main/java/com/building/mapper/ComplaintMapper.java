package com.building.mapper;

import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterComplaintDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ComplaintMapper {
    long insertComplaint(@Param("dto") MasterComplaintDto complaintDto);
    long insertTComplaint(@Param("dto")TransferComplaintDto transferComplaintDto);
    List<MasterComplaintDto> findAllComplaint();
    List<TransferComplaintDto> findAllTComplaint(@Param("complaintCodes") List<Long> complaintCode);
    List<TransferReplyDto> findAllTReply(@Param("parentComplaintCodes") List<Long> parentComplaintCode);
    void updateFollowStatus(@Param("dto") MasterComplaintDto complaintDto);
    MasterComplaintDto findById(@Param("id") long id);
    List<MasterComplaintDto> findAllComplaintHistory(@Param("aui")AuthorizedUserInfo aui, @Param("per") boolean per);
}
