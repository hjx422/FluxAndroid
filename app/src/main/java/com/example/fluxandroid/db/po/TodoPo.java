package com.example.fluxandroid.db.po;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 13:42
 * @description
 */
public class TodoPo extends BasePo {

    public static final String TEXT = "text";
    public static final String COMPLETED = "completed";
    public static final String LAST_DELETED = "last_deleted";

    @DatabaseField(columnName = TEXT, useGetSet = true)
    private String text;

    @DatabaseField(columnName = COMPLETED, useGetSet = true)
    private boolean completed;

    @DatabaseField(columnName = LAST_DELETED, useGetSet = true)
    private boolean lastDeleted;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isLastDeleted() {
        return lastDeleted;
    }

    public void setLastDeleted(boolean lastDeleted) {
        this.lastDeleted = lastDeleted;
    }
}
