package com.building.services.impl;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.ComplaintDto;
import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import com.building.mapper.ComplaintMapper;
import com.building.services.ComplaintService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhuongTN1 on 11/4/2016.
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;
    @Override
    public long insertComplaint(ComplaintDto complaintDto) throws ServerException {
        return complaintMapper.insertComplaint(complaintDto);
    }

    @Override
    public List<ComplaintDto> findAllComplaint() throws ServerException {
        return complaintMapper.findAllComplaint();
    }

    @Override
    public List<TransferComplaintDto> findAllTComplaint(List<Long> complaintCode) throws ServerException {
        return complaintMapper.findAllTComplaint(complaintCode);
    }

    @Override
    public List<TransferReplyDto> findAllTReply(List<Long> parentComplaintCode) throws ServerException {
        return complaintMapper.findAllTReply(parentComplaintCode);
    }

    @Override
    public long updateFollowStatus(ComplaintDto complaintDto) throws ServerException {
        complaintMapper.updateFollowStatus(complaintDto);
        return 1L;
    }

    @Override
    public ComplaintDto findById(long id) throws ServerException {
        return complaintMapper.findById(id);
    }

    @Override
    public List<ComplaintDto> findAllComplaintHistory(AuthorizedUserInfo aui, boolean per) throws ServerException {
        return complaintMapper.findAllComplaintHistory(aui,per);
    }

    @Override
    public long insertTComplaint(TransferComplaintDto transferComplaintDto) throws ServerException {
        return complaintMapper.insertTComplaint(transferComplaintDto);
    }

/*@Override
    public void update(NewsDto newsDto) throws ServerException {
        newsMapper.update(newsDto);
    }

    @Override
    public void deleteById(long id) throws ServerException {
        newsMapper.deleteById(id);
    }*/
}
