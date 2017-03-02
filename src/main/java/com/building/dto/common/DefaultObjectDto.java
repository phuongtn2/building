package com.building.dto.common;

import java.util.Date;

/**
 * Created by phuongtn2 on 11/14/2016.
 */
public class DefaultObjectDto {
    private Date created;
    private Integer createId;
    private Date lastUpdate;
    private Integer updateId;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}
