package com.example.administrator.myapplication;

public class Comment {
    private String commentcontent;
    private String commentdate;
    private String userid;

    public Comment(String commentcontent, String commentdate) {
        this.commentcontent = commentcontent;
        this.commentdate = commentdate;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }
}
