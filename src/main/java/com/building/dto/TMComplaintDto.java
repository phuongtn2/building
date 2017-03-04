package com.building.dto;

import com.building.dto.common.DefaultObjectDto;
import com.building.dto.master.MasterComplaintDto;

import java.util.List;

/**
 * Created by phuongtn2 on 11/15/2016.
 */
public class TMComplaintDto extends DefaultObjectDto {
    MasterComplaintDto mComplaint ;
    List<TransferComplaintDto> tComplaintList;

    public MasterComplaintDto getmComplaint() {
        return mComplaint;
    }

    public void setmComplaint(MasterComplaintDto mComplaint) {
        this.mComplaint = mComplaint;
    }

    public List<TransferComplaintDto> gettComplaintList() {
        return tComplaintList;
    }

    public void settComplaintList(List<TransferComplaintDto> tComplaintList) {
        this.tComplaintList = tComplaintList;
    }
}
