package com.example.digital.borradorproyectointegrador.dao.internet.dao_video_tv;

import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceVideoTV {

    @GET("tv/{tv_id}/videos")
    Call<VideoContainer> getVideos(@Path("tv_id") Integer id,
                                   @Query("api_key") String api_key,
                                   @Query("language") String language

                                   );

}
