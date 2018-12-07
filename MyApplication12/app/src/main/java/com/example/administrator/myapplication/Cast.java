package com.example.administrator.myapplication;

public class Cast {
    private String imgCast;
    private String idCast;

    public Cast(String imgCast, String id) {
        this.imgCast = imgCast;
        this.idCast = id;
    }

    public String getImgCast() {
        return imgCast;
    }

    public void setImgCast(String imgCast) {
        this.imgCast = imgCast;
    }

    public String getId() {
        return idCast;
    }

    public void setId(String id) {
        this.idCast = id;
    }
}
