package com.example.digital.borradorproyectointegrador.model.videos;


public class Video {
    private String key;

    public Video(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Video{" +
                "key='" + key + '\'' +
                '}';
    }
}
