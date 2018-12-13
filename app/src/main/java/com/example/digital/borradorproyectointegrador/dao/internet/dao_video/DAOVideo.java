package com.example.digital.borradorproyectointegrador.dao.internet.dao_video;

import android.util.Log;

import com.example.digital.borradorproyectointegrador.dao.internet.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOVideo extends DaoHelper {

    private ServiceVideo serviceVideo;


    public DAOVideo() {
        super("https://api.themoviedb.org/3/");
        serviceVideo = retrofit.create(ServiceVideo.class);
    }

    public void traerVideos(Integer movieId, final ResultListener<List<Video>> listResultListener) {
        Call<VideoContainer> call = serviceVideo.getVideos(movieId, DaoHelper.API_KEY, DaoHelper.LANGUAGE);
        call.enqueue(new Callback<VideoContainer>() {
            @Override
            public void onResponse(Call<VideoContainer> call, Response<VideoContainer> response) {

                VideoContainer videoContainer = response.body();

                List<Video> videos = null;
                if (videoContainer != null) {
                    videos = videoContainer.getResults();
                }

                listResultListener.finish(videos);


            }

            @Override
            public void onFailure(Call<VideoContainer> call, Throwable t) {
                Log.e("MIERRROR----------", t.toString());
            }
        });


    }

}
