package com.example.digital.borradorproyectointegrador.dao.dao_video;

import com.example.digital.borradorproyectointegrador.model.videos.Video;

import java.util.List;

public interface OnGetVideoCallback {
    void onSuccess(List<Video> videos);

    void onError();
}
