package com.example.digital.borradorproyectointegrador.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_peliculas.DAOPelicula;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_video.DAOVideo;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_video_tv.DAOVideoTV;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentarioTrailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import android.support.v4.media.VolumeProviderCompatApi21;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppCompatCallback, AdaptadorRecyclerComentarioTrailer.ComentarioInterface {

    public static final String KEY_TIPO = "tipo";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING_BAR = "rating bar";
    public static final String KEY_CANT_ESTRELLAS = "cantEstrellas";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RESUMEN = "resumen";
    public static final String KEY_RELEASE_DATE = "release";
    public static final Integer KEY_ID = null;
    public static final String API_KEY = "AIzaSyBwKk1MoedjyracfPjgvI7_0zpSpPan5nU";
    public static String VIDEO_ID = "videoKey";
    private AppCompatDelegate delegate;
    private static DAOVideo daoVideo;
    private static DAOVideoTV daoVideoTV;
    private static DAOPelicula daoPelicula;

    public static final int KEY_OUT_LOGIN_FAVORITOS = 201;
    public static final int KEY_OUT_LOGIN_COMPARTIR = 202;
    public static final int KEY_OUT_LOGIN_COMENTARIOS = 203;
    public static final int KEY_OUT_AGREGAR_COMENTARIOS = 301;

    public static final String KEY_COMENTARIO = "comentario";
    public static final String KEY_RATING = "rating";
    public static final String KEY_POSTER_PATH="poster";

    private Integer tipo;
    private String nombre;
    private String resumen;
    private Integer id;
    private String poster_path;
    private String origLanguage;
    private String release_date;
    private String revenue;
    private String runtime;
    private String tagline;
    private Button btnFavorito;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference usuarioPerfilDB;
    private ShareActionProvider mShareActionProvider;
    private AdaptadorRecyclerComentarioTrailer adaptadorRecyclerComentarioTrailer;
    private ControllerPelicula controllerPelicula;
    private ControllerSerie controllerSerie;
    private Peliculas pelicula;
    private Serie serie;
    private TextView textViewInfoPeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        //usuario
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //Gerente
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();

        //AdaptadorComentario
        adaptadorRecyclerComentarioTrailer = new AdaptadorRecyclerComentarioTrailer(currentUser,this,this,new ArrayList<Comentario>());

        //PARA USAR EL APPCOMPAT JUNTO CON EL DE YOUTUBE
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_trailer);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbarTrailer);
        delegate.setSupportActionBar(toolbar);

        Objects.requireNonNull(delegate.getSupportActionBar()).setTitle("");

        if (delegate.getSupportActionBar() != null) {
            delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // COMUNICACION ENTRE FRAGMENT/ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // DATOS
        tipo = bundle.getInt(KEY_TIPO);
        String imageData = bundle.getString(KEY_IMAGE);
        Integer cantEstrellasData = bundle.getInt(KEY_CANT_ESTRELLAS);
        nombre = bundle.getString(KEY_NOMBRE);
        resumen = bundle.getString(KEY_RESUMEN);
        poster_path = bundle.getString(KEY_POSTER_PATH);

        id = bundle.getInt(String.valueOf(KEY_ID));
        validarSiFavorito(id);






        // COMPONENTES
        RatingBar ratingBar = findViewById(R.id.rbShowRoom);
        TextView textViewResumen = findViewById(R.id.textViewResumenDetalle);
        textViewInfoPeli = findViewById(R.id.textViewInfoPeli);

        // Seteo
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
//        delegate.getSupportActionBar().setTitle("Titulo");
//        delegate.getSupportActionBar().setSubtitle(nombre);
        delegate.getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.title_view, null);

        ((TextView)view.findViewById(R.id.title)).setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ((TextView)view.findViewById(R.id.title)).setFocusable(true);
        ((TextView)view.findViewById(R.id.title)).setFocusableInTouchMode(true);
        ((TextView)view.findViewById(R.id.title)).requestFocus();
        ((TextView)view.findViewById(R.id.title)).setSingleLine(true);
        ((TextView)view.findViewById(R.id.title)).setSelected(true);
        ((TextView)view.findViewById(R.id.title)).setMarqueeRepeatLimit(-1);
        ((TextView)view.findViewById(R.id.title)).setText(nombre);

        delegate.getSupportActionBar().setCustomView(view);
        ratingBar.setRating(cantEstrellasData);
        textViewResumen.setText(resumen);
        controllerPelicula = new ControllerPelicula(this);
        controllerSerie = new ControllerSerie(this);
        traerInfoAdicional(id);







        //Cargar Comentarios
        //Recycler
        RecyclerView recyclerViewComentarioTrailer = findViewById(R.id.recyclerComentariosTrailer);
        recyclerViewComentarioTrailer.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewComentarioTrailer.setLayoutManager(llm);

        recyclerViewComentarioTrailer.setAdapter(adaptadorRecyclerComentarioTrailer);
        recargarRecycler();
        getVideos(id);

        //FAVORITOS Y COMPARTIR
        Button btnCompartir = findViewById(R.id.btnCompartir);
        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartirTrailer(id);
            }
        });

        btnFavorito = findViewById(R.id.btnFavorito);
        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarFavoritos();
            }
        });

        //Agregar Comentarios
        FloatingActionButton fabAgregarComentario = findViewById(R.id.fabAgregarComentario);
        fabAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarComentario();
            }
        });

    }

    // SHARE NATIVO
    public void compartirTrailer(Integer movieId){
        // SI EL USUARIO ESTA LOGUEADO, ENTRA
        if (currentUser!=null){
            // SI EL TIPO ES MOVIE:
            if (tipo == 0){
                Toast.makeText(TrailerActivity.this, "Compartir Pelicula", Toast.LENGTH_SHORT).show();
                //TODO actualizar esta parte:
                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //Indicamos que voy a compartir texto
                share.setType("text/plain");
                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, "Share Link of Movie");
                //Le agrego el texto a compartir (Puede ser un link tambien)
                share.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/" + movieId);
                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, "Share Link!"));
                // SI EL TIPO ES TV:
            } else {
                Toast.makeText(TrailerActivity.this, "Compartir Serie", Toast.LENGTH_SHORT).show();
                //TODO actualizar esta parte:
                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //Indicamos que voy a compartir texto
                share.setType("text/plain");
                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, "Share Link of TV show");
                //Le agrego el texto a compartir (Puede ser un link tambien)
                share.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/tv/" + movieId);
                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, "Share Link!"));
            }


        // SI NO ESTA LOGUEADO, LLEVAR AL SCREEN DE LOGIN:
        } else {
            irAlLogIn(TrailerActivity.KEY_OUT_LOGIN_COMPARTIR);
        }
    }

    public void validarSiFavorito(final Integer id){
        if (currentUser!=null) {
            usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
            usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);
                    if (usuario.getPeliculasFavoritas()!=null && usuario.getSeriesFavoritas()!=null){
                        if (usuario.getPeliculasFavoritas().contains(id) || usuario.getSeriesFavoritas().contains(id)) {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
                        }else {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }else if (usuario.getPeliculasFavoritas()!=null && usuario.getSeriesFavoritas()==null){
                        if (usuario.getPeliculasFavoritas().contains(id)) {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
                        }else {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }else if (usuario.getPeliculasFavoritas()==null && usuario.getSeriesFavoritas()!=null){
                        if (usuario.getSeriesFavoritas().contains(id)) {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
                        }else {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void agregarFavoritos(){
        if (currentUser!=null){
            usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
            usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);
                    if (tipo==0) {
                        List<Integer> pelis = new ArrayList<>();
                        if (usuario.getPeliculasFavoritas()!=null){
                            for (Integer peli:usuario.getPeliculasFavoritas()) {
                                pelis.add(peli);
                            }
                            if (usuario.getPeliculasFavoritas().contains(id)) {
                                btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border));
                                pelis.remove(id);
                            } else {
                                btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
                                pelis.add(id);
                            }
                        }else {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));

                            pelis.add(id);
                        }
                        usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_peliculas_favoritas)).setValue(pelis);
                    }else {
                        List<Integer> series = new ArrayList<>();
                        if (usuario.getSeriesFavoritas()!=null){
                            for (Integer serie:usuario.getSeriesFavoritas()) {
                                series.add(serie);
                            }
                            if (usuario.getSeriesFavoritas().contains(id)) {
                                btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border));
                                series.remove(id);
                            } else {
                                btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
                                series.add(id);
                            }
                        }else {
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));

                            series.add(id);
                        }
                        usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_series_favoritas)).setValue(series);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            btnFavorito.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
        }else {
            irAlLogIn(TrailerActivity.KEY_OUT_LOGIN_FAVORITOS);
        }
    }

    public void agregarComentario(){
        if (currentUser!=null){
            irAgregarComentario(TrailerActivity.KEY_OUT_AGREGAR_COMENTARIOS);
        }else {
            irAlLogIn(TrailerActivity.KEY_OUT_LOGIN_COMENTARIOS);
        }
    }

    public void irAlLogIn(int llave){
        Intent intentAccount = new Intent(TrailerActivity.this, LoginActivity.class);
        startActivityForResult(intentAccount,llave);
        //startActivity(intentAccount);
    }

    public void irAgregarComentario(int llave){
        Intent intentAccount = new Intent(TrailerActivity.this, AgregarComentarioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AgregarComentarioActivity.KEY_ID,id);
        bundle.putInt(AgregarComentarioActivity.KEY_TIPO,tipo);
        bundle.putString(AgregarComentarioActivity.KEY_TITLE,nombre);
        bundle.putString(AgregarComentarioActivity.KEY_POSTER_PATH,poster_path);
        intentAccount.putExtras(bundle);
        startActivityForResult(intentAccount,llave);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TrailerActivity.KEY_OUT_LOGIN_FAVORITOS:
                    agregarFavoritos();
                    break;

                case TrailerActivity.KEY_OUT_LOGIN_COMENTARIOS:
                    agregarComentario();
                    break;
                case TrailerActivity.KEY_OUT_AGREGAR_COMENTARIOS:
                    ComentariosController comentariosController = new ComentariosController();
                    comentariosController.entregarListaComentariosTrailer(String.valueOf(id), this, new ResultListener<List<Comentario>>() {
                        @Override
                        public void finish(List<Comentario> Resultado) {
                            adaptadorRecyclerComentarioTrailer.setComentarioTrailerList(Resultado);
                        }
                    });

                    Toast.makeText(TrailerActivity.this, "Gracias por Compartir", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            Toast.makeText(TrailerActivity.this, "Fallo su LogIn", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent respuestaLogin(){
        Intent intent = new Intent();
        return intent;
    }

    public static Intent respuestaAgregarComentario(){
        Intent intent = new Intent();
        return intent;
    }

    private void getVideos(Integer movieId) {
        if (tipo==0){
            daoVideo = new DAOVideo();
            daoVideo.traerVideos(movieId, new ResultListener<List<Video>>() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void finish(List<Video> videos) {
                    VIDEO_ID = videos.get(0).getKey();
                    /** Initializing YouTube Player View **/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (TrailerActivity.this.isDestroyed()){
                            return;
                        }else {
                            YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
                            youTubePlayerView.initialize(API_KEY, TrailerActivity.this);
                        }
                    }else {
                        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
                        youTubePlayerView.initialize(API_KEY, TrailerActivity.this);
                    }

                }
            });
        }else{
            daoVideoTV = new DAOVideoTV();
            daoVideoTV.traerVideos(movieId, new ResultListener<List<Video>>() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void finish(List<Video> videos) {
                    VIDEO_ID = videos.get(0).getKey();
                    /** Initializing YouTube Player View **/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (TrailerActivity.this.isDestroyed()){
                            return;
                        }else {
                            YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
                            youTubePlayerView.initialize(API_KEY, TrailerActivity.this);
                        }
                    }else {
                        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
                        youTubePlayerView.initialize(API_KEY, TrailerActivity.this);
                    }

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        switch (item.getItemId()) {
            case R.id.itemAccount:
                Intent intentAccount = new Intent(TrailerActivity.this, LoginActivity.class);
                startActivity(intentAccount);
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


    @Override
    public void irPerfil(Comentario comentario) {
        String user = comentario.getUserId();
        Intent intent = new Intent(TrailerActivity.this,PerfilUsuarioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PerfilUsuarioActivity.KEY_USER,user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void botonesComentario(Integer boton, FirebaseUser user, Comentario comentario) {
        final DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(comentario.getUserId()).child(getResources().getString(R.string.child_usuario_perfil_cant_me_gusta));
        DatabaseReference comentariosDB = mReference.child(getResources().getString(R.string.child_base_comentarios));
        Integer sumarUno;
        Integer restarUno;
        switch (boton){
            case 0: //BOTON ME GUSTA
                if (comentario.getTvCantMeGusta()!=null) {
                    sumarUno = comentario.getTvCantMeGusta() + 1;
                }else {
                    sumarUno = 1;
                }
                comentariosDB.child(comentario.getIdPelioSerie().toString()).child(comentario.getUserId()).child("tvCantMeGusta").setValue(sumarUno);
                Toast.makeText(TrailerActivity.this, "Gracias por participar!", Toast.LENGTH_SHORT).show();
                usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usuarioPerfilDB.setValue(Integer.valueOf(String.valueOf(dataSnapshot.getValue()))+1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 1: //BOTON NO ME GUSTA
                if (comentario.getTvCantMeGusta()!=null) {
                    restarUno = comentario.getTvCantMeGusta() - 1;
                }else {
                    restarUno = -1;
                }
                comentariosDB.child(comentario.getIdPelioSerie().toString()).child(comentario.getUserId()).child("tvCantMeGusta").setValue(restarUno);
                Toast.makeText(TrailerActivity.this, "Gracias por participar!", Toast.LENGTH_SHORT).show();
                usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usuarioPerfilDB.setValue(Integer.valueOf(String.valueOf(dataSnapshot.getValue()))-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 2: //BOTON COMPARTIR
                String titulo = getResources().getString(R.string.compartir_comentario_titulo) + " " + comentario.getPeliculaComentada() + ": ";
                String mensaje = titulo + " " + comentario.getTvUsuarioComentario() + " " + getResources().getString(R.string.compartir_comentario_quien) + " " + comentario.getTvComentarioComentario();

                Toast.makeText(TrailerActivity.this, getResources().getString(R.string.toast_inicio), Toast.LENGTH_SHORT).show();
                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //Indicamos que voy a compartir texto
                share.setType("text/plain");
                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, titulo);
                //Le agrego el texto a compartir (Puede ser un link tambien)
                share.putExtra(Intent.EXTRA_TEXT, mensaje);
                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, getResources().getString(R.string.toast_inicio)));
                break;
        }
        recargarRecycler();
    }

    public void recargarRecycler(){
        //Datos
        ComentariosController comentariosController = new ComentariosController();
        comentariosController.entregarListaComentariosTrailer(String.valueOf(id), this, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                adaptadorRecyclerComentarioTrailer.setComentarioTrailerList(Resultado);
            }
        });

    }

    public void traerInfoAdicional(Integer id){
        if (tipo == 0){
            controllerPelicula.entregarUnaPelicula(id, TrailerActivity.this, new ResultListener<Peliculas>() {
                @Override
                public void finish(Peliculas Resultado) {
                    List genres = Resultado.getGenre_ids();
                    origLanguage = Resultado.getOriginal_language();
                    release_date = Resultado.getRelease_date();
                    revenue = Resultado.getRevenue();
                    runtime = Resultado.getRuntime();
                    tagline = Resultado.getTagline();

                    textViewInfoPeli.setText(origLanguage + release_date + revenue + runtime + tagline);

                }
            });
        } else if (tipo == 1){
            controllerSerie.entregarUnaSerie(id, this, new ResultListener<Serie>() {
                @Override
                public void finish(Serie Resultado) {
                    origLanguage = Resultado.getOriginal_language();
                    release_date = Resultado.getFirst_air_date();
                    String homepage = Resultado.getHomepage();
                    String in_production = Resultado.getIn_production();
                    String numberEpisodes = Resultado.getNumber_of_episodes();
                    String numberSeasons = Resultado.getNumber_of_seasons();
                    String status = Resultado.getStatus();


                    textViewInfoPeli.setText(origLanguage + release_date + homepage + in_production + numberEpisodes + numberSeasons + status);
                }
            });
        }
    }

}

