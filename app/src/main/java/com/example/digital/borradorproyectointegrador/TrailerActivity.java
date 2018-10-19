package com.example.digital.borradorproyectointegrador;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
//import android.support.v4.media.VolumeProviderCompatApi21;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Objects;

import retrofit2.http.Url;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppCompatCallback {
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING_BAR = "rating bar";
    public static final String KEY_CANT_ESTRELLAS = "cantEstrellas";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RESUMEN = "resumen";
    public static final String API_KEY = "AIzaSyBwKk1MoedjyracfPjgvI7_0zpSpPan5nU";
    public static  String VIDEO_ID = "videoKey";
    private AppCompatDelegate delegate;



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




        //Llamar a la action bar para mostrar
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarTrailer);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
//
//        if(getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }





        // COMUNICACION ENTRE FRAGMENT/ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // DATOS
        String imageData = bundle.getString(KEY_IMAGE);
        Integer cantEstrellasData = bundle.getInt(KEY_CANT_ESTRELLAS);
        String nombre = bundle.getString(KEY_NOMBRE);
        Integer resumen = bundle.getInt(KEY_RESUMEN);
        String videoId = bundle.getString(VIDEO_ID);


        // COMPONENTES
        RatingBar ratingBar = findViewById(R.id.rbShowRoom);
        TextView textViewNombre = findViewById(R.id.textViewTituloTrailer);
        TextView textViewResumen = findViewById(R.id.textViewResumenDetalle);


        // Seteo
        ratingBar.setRating(cantEstrellasData);
        textViewNombre.setText(nombre);
        textViewResumen.setText(resumen);
        VIDEO_ID = videoId;


        /** Initializing YouTube Player View **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        youTubePlayerView.initialize(API_KEY, this);
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
            case R.id.item1:
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subItem1:
                Toast.makeText(this, "Sub Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subItem2:
                Toast.makeText(this, "Sub Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
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

