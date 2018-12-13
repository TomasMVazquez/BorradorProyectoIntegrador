package com.example.digital.borradorproyectointegrador.dao.internet.dao_video_tv;

import android.util.Log;

import com.example.digital.borradorproyectointegrador.dao.internet.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class DAOVideoTV extends DaoHelper {
    private ServiceVideoTV serviceVideoTV;


    public DAOVideoTV() {
        super("https://api.themoviedb.org/3/");
        serviceVideoTV = retrofit.create(ServiceVideoTV.class);
    }

    public void traerVideos(Integer tvId, final ResultListener<List<Video>> listResultListener){
        retrofit2.Call<VideoContainer> call = serviceVideoTV.getVideos(tvId, DaoHelper.API_KEY, DaoHelper.LANGUAGE);
        call.enqueue(new Callback<VideoContainer>() {
            @Override
            public void onResponse(retrofit2.Call<VideoContainer> call, Response<VideoContainer> response) {

                VideoContainer videoContainer = response.body();

                List<Video> videos = videoContainer.getResults();

                listResultListener.finish(videos);

            }

            @Override
            public void onFailure(retrofit2.Call<VideoContainer> call, Throwable t) {
                Log.e("MIERRROR----------", t.toString());
            }
        });

    }


}
