package com.building.mapper;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.ComplaintDto;
import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
public interface ComplaintMapper {
    long insertComplaint(@Param("dto") ComplaintDto complaintDto);
    long insertTComplaint(@Param("dto")TransferComplaintDto transferComplaintDto);
    List<ComplaintDto> findAllComplaint();
    List<TransferComplaintDto> findAllTComplaint(@Param("complaintCodes") List<Long> complaintCode);
    List<TransferReplyDto> findAllTReply(@Param("parentComplaintCodes") List<Long> parentComplaintCode);
    void updateFollowStatus(@Param("dto") ComplaintDto complaintDto);
    ComplaintDto findById(@Param("id") long id);
    List<ComplaintDto> findAllComplaintHistory(@Param("aui")AuthorizedUserInfo aui, @Param("per") boolean per);
}
