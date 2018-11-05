package com.example.digital.borradorproyectointegrador.model.videos;

import java.util.List;

public class VideoContainer {
    private List<Video> results;

    public VideoContainer(List<Video> results) {
        this.results = results;
    }

    public List<Video> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "VideoContainer{" +
                "results=" + results +
                '}';
    }
}
