package com.example.administrator.myapplication;

public class Trailer {
    private String ImageTrailer;
    private String KeyTrailer;

    public Trailer(String imageTrailer, String keyTrailer) {
        ImageTrailer = imageTrailer;
        KeyTrailer = keyTrailer;
    }

    public String getImageTrailer() {
        return ImageTrailer;
    }

    public void setImageTrailer(String imageTrailer) {
        ImageTrailer = imageTrailer;
    }

    public String getKeyTrailer() {
        return KeyTrailer;
    }

    public void setKeyTrailer(String keyTrailer) {
        KeyTrailer = keyTrailer;
    }
}
