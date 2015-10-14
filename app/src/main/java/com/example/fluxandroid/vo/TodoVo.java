package com.example.fluxandroid.vo;

/**
 * @author hjx
 * @date 10/8/2015
 * @time 16:10
 * @description
 */
public class TodoVo implements Cloneable, Comparable<TodoVo> {

    private long id;
    private String text;
    private boolean completed;
    private long createTime;

//    public TodoVo(long id, String text) {
//        this.id = id;
//        this.text = text;
//        completed = false;
//    }

    public TodoVo() {}

    public TodoVo(long id, String text, boolean completed, long createTime) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public TodoVo clone() {
        return new TodoVo(id, text, completed, createTime);
    }

    @Override
    public int compareTo(TodoVo another) {
        if (createTime == another.getCreateTime()) {
            return 0;
        } else if (createTime < another.getCreateTime()) {
            return -1;
        } else {
            return 1;
        }
    }
}
