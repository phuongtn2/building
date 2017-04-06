package com.building.services.impl;

import com.building.dto.TransferComplaintDto;
import com.building.dto.TransferReplyDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterComplaintDto;
import com.building.mapper.ComplaintMapper;
import com.building.services.ComplaintService;
import com.building.services.error.ServiceException;
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
    public long insertComplaint(MasterComplaintDto complaintDto) throws ServiceException {
        return complaintMapper.insertComplaint(complaintDto);
    }

    @Override
    public List<MasterComplaintDto> findAllComplaint() throws ServiceException {
        return complaintMapper.findAllComplaint();
    }

    @Override
    public List<TransferComplaintDto> findAllTComplaint(List<Long> complaintCode) throws ServiceException {
        return complaintMapper.findAllTComplaint(complaintCode);
    }

    @Override
    public List<TransferReplyDto> findAllTReply(List<Long> parentComplaintCode) throws ServiceException {
        return complaintMapper.findAllTReply(parentComplaintCode);
    }

    @Override
    public long updateFollowStatus(MasterComplaintDto complaintDto) throws ServiceException {
        complaintMapper.updateFollowStatus(complaintDto);
        return 1L;
    }

    @Override
    public MasterComplaintDto findById(long id) throws ServiceException {
        return complaintMapper.findById(id);
    }

    @Override
    public List<MasterComplaintDto> findAllComplaintHistory(AuthorizedUserInfo aui, boolean per) throws ServiceException {
        return complaintMapper.findAllComplaintHistory(aui,per);
    }

    @Override
    public long insertTComplaint(TransferComplaintDto transferComplaintDto) throws ServiceException {
        return complaintMapper.insertTComplaint(transferComplaintDto);
    }

/*@Override
    public void update(NewsDto newsDto) throws ServiceException {
        newsMapper.update(newsDto);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        newsMapper.deleteById(id);
    }*/
}
