package com.example.digital.borradorproyectointegrador.dao.dao_video;

import android.util.Log;

import com.example.digital.borradorproyectointegrador.dao.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;

public class DAOVideo extends DaoHelper {

    private ServiceVideo serviceVideo;


    public DAOVideo() {
        super("https://api.themoviedb.org/3/movie/");
        serviceVideo = retrofit.create(ServiceVideo.class);
    }

    public void traerVideos(Integer movieId, final ResultListener<List<Video>> listResultListener){
        Call<VideoContainer> call = serviceVideo.getVideos();
                call.enqueue(new Callback<VideoContainer>() {
                    @Override
                    public void onResponse(Call<VideoContainer> call, Response<VideoContainer> response) {

                        VideoContainer videoContainer = response.body();

                        List<Video> videos = videoContainer.getResults();

                        listResultListener.finish(videos);


//                        if (response.isSuccessful()){
//                            VideoContainer videoContainer = response.body();
//                            if (videoContainer != null && videoContainer.getResults() != null){
//                                callback.onSuccess(videoContainer.getResults());
//                            } else {
//                                callback.onError();
//                            }
//                        } else {
//                            callback.onError();
//                        }


                    }

                    @Override
                    public void onFailure(Call<VideoContainer> call, Throwable t) {
                        Log.e("MIERRROR----------", t.toString());
                    }
                });



    }

}
