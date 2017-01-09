package com.building.dto;

import java.util.Date;

/**
 * Created by phuongtn2 on 11/14/2016.
 */
public class DefaultObjectDto {
    private Date created;
    private int createId;
    private Date lastUpdate;
    private int updateId;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }
}
