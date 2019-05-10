package com.lemonbily.springboot.entity;

import java.io.Serializable;
import java.util.List;

public class VideoDetailUIBean implements Serializable {
    private boolean isCollect;
    private List<CommentUIBean> commentUIBeanList;

    public VideoDetailUIBean() {
    }

    public VideoDetailUIBean(boolean isCollect, List<CommentUIBean> commentUIBeanList) {
        this.isCollect = isCollect;
        this.commentUIBeanList = commentUIBeanList;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public List<CommentUIBean> getCommentUIBeanList() {
        return commentUIBeanList;
    }

    public void setCommentUIBeanList(List<CommentUIBean> commentUIBeanList) {
        this.commentUIBeanList = commentUIBeanList;
    }
}
