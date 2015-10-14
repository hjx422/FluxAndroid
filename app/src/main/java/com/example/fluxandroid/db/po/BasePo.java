package com.example.fluxandroid.db.po;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 13:43
 * @description
 */
public class BasePo {

    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";

    @DatabaseField(generatedId = true, useGetSet = true)
    private long id;

    @DatabaseField(columnName = CREATE_TIME, useGetSet = true)
    private long createTime;

    @DatabaseField(columnName = UPDATE_TIME, useGetSet = true)
    private long updateTime;

    @DatabaseField(columnName = DELETED, useGetSet = true)
    private boolean deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
