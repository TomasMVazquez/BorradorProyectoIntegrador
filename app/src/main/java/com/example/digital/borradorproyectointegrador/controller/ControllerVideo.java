package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.dao.internet.dao_video.DAOVideo;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;

import java.util.List;

public class ControllerVideo {

    public void entregarVideos(Context context, Integer movieId, final ResultListener<List<Video>> listResultListener) {

        if (Util.hayInternet(context)) {

            final DAOVideo daoVideo = new DAOVideo();

            daoVideo.traerVideos(movieId, new ResultListener<List<Video>>() {
                @Override
                public void finish(List<Video> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });
        } else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();

        }

    }
}
