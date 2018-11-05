package com.example.digital.borradorproyectointegrador.dao.dao_video;

import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceVideo {

    @GET("movie/{movie_id}/videos")
    Call<VideoContainer> getVideos();
//            @Path("movie_id") int id,
//            @Query("api_key") String apiKEy,
//            @Query("language") String language
//    );

}
