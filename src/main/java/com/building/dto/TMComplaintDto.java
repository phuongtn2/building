package com.building.dto;

import java.util.List;

/**
 * Created by phuongtn2 on 11/15/2016.
 */
public class TMComplaintDto extends DefaultObjectDto {
    ComplaintDto mComplaint ;
    List<TransferComplaintDto> tComplaintList;

    public ComplaintDto getmComplaint() {
        return mComplaint;
    }

    public void setmComplaint(ComplaintDto mComplaint) {
        this.mComplaint = mComplaint;
    }

    public List<TransferComplaintDto> gettComplaintList() {
        return tComplaintList;
    }

    public void settComplaintList(List<TransferComplaintDto> tComplaintList) {
        this.tComplaintList = tComplaintList;
    }
}
