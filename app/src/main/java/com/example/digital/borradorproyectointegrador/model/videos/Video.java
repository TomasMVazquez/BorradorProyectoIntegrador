package com.example.digital.borradorproyectointegrador.model.videos;


public class Video {
    private String id;
    private String key;

    public Video(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
