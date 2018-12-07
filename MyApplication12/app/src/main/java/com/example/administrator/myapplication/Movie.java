package com.example.administrator.myapplication;

public class Movie {
    private String images;
    private String id;
    private String title;
    private String overview;

    public Movie(String images, String id, String title, String overview) {
        this.images = images;
        this.id = id;
        this.title = title;
        this.overview = overview;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
