package com.example.digital.borradorproyectointegrador.view;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
//import android.support.v4.media.VolumeProviderCompatApi21;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.dao.dao_peliculas.DAOPelicula;
import com.example.digital.borradorproyectointegrador.dao.dao_video.DAOVideo;
import com.example.digital.borradorproyectointegrador.dao.dao_video.OnGetVideoCallback;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentarioTrailer;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentariosCompletos;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.Objects;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppCompatCallback {
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING_BAR = "rating bar";
    public static final String KEY_CANT_ESTRELLAS = "cantEstrellas";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RESUMEN = "resumen";
    public static final Integer KEY_ID = null;
    public static final String API_KEY = "AIzaSyBwKk1MoedjyracfPjgvI7_0zpSpPan5nU";
    public static  String VIDEO_ID = "videoKey";
    private AppCompatDelegate delegate;
    private static DAOVideo daoVideo;
    private static DAOPelicula daoPelicula;


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        //PARA USAR EL APPCOMPAT JUNTO CON EL DE YOUTUBE
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_trailer);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbarTrailer);
        delegate.setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(delegate.getSupportActionBar()).setTitle(null);
        }

        if(delegate.getSupportActionBar() != null){
            delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        // COMUNICACION ENTRE FRAGMENT/ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // DATOS
        String imageData = bundle.getString(KEY_IMAGE);
        Integer cantEstrellasData = bundle.getInt(KEY_CANT_ESTRELLAS);
        String nombre = bundle.getString(KEY_NOMBRE);
//        String resumen = String.valueOf(bundle.getInt(KEY_RESUMEN));
        String resumen = bundle.getString(KEY_RESUMEN);
        String videoId = bundle.getString(VIDEO_ID);
//        Integer id = Integer.valueOf(bundle.getString(String.valueOf(KEY_ID)));
        Integer id = bundle.getInt(String.valueOf(KEY_ID));





        // COMPONENTES
        RatingBar ratingBar = findViewById(R.id.rbShowRoom);
        TextView textViewNombre = findViewById(R.id.textViewTituloTrailer);
        TextView textViewResumen = findViewById(R.id.textViewResumenDetalle);






        // Seteo
        ratingBar.setRating(cantEstrellasData);
        textViewNombre.setText(nombre);
        textViewResumen.setText(resumen + id + VIDEO_ID);







        /** Initializing YouTube Player View **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        youTubePlayerView.initialize(API_KEY, this);

        //Cargar Comentarios
            //Datos
        ComentariosController comentariosController = new ComentariosController();

            //Recycler
        RecyclerView recyclerViewComentarioTrailer = findViewById(R.id.recyclerComentariosTrailer);
        recyclerViewComentarioTrailer.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewComentarioTrailer.setLayoutManager(llm);

        AdaptadorRecyclerComentarioTrailer adaptadorRecyclerComentarioTrailer = new AdaptadorRecyclerComentarioTrailer(comentariosController.entregarListaComentariosTrailer(nombre));

        recyclerViewComentarioTrailer.setAdapter(adaptadorRecyclerComentarioTrailer);


    
    }


    private void getVideos(final Peliculas peliculas){
        daoVideo.traerVideos(peliculas.getId(), new ResultListener<List<Video>>() {
            @Override
            public void finish(List<Video> videos) {
                VIDEO_ID = videos.get(0).getKey();
            }
            });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() ==android.R.id.home)
            finish();

        switch (item.getItemId()){
            case R.id.itemAccount:
                Intent intentAccount = new Intent(TrailerActivity.this, LogIn.class);
                startActivity(intentAccount);
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemSearch:

                Toast.makeText(this, "Item Search Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
/** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
/** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

