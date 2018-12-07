package com.example.digital.borradorproyectointegrador.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.controller.ControllerUsuarioPerfil;
import com.example.digital.borradorproyectointegrador.controller.ControllerVideo;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.model.videos.Video;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.MyViewPagerAdapter;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.SerieAdaptador;
import com.example.digital.borradorproyectointegrador.view.Fragments.ComentariosFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.FiltroFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.PeliculasFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.SeriesFragment;
import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements PeliculasFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener, FiltroFragment.FragmentInterface,PeliculaAdaptador.AdapterPeliInterface,SerieAdaptador.AdapterSerieInterface {

    SearchView searchView;

    private List<Integer> listaFiltros;
    private Integer tabFiltros;
    private MyViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView recyclerViewFav;
    private PeliculaAdaptador peliculaAdaptador;
    private SerieAdaptador serieAdaptador;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Util.printHash(this);
        //Gerente
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),new ArrayList<Fragment>());
        peliculaAdaptador = new PeliculaAdaptador(this,new ArrayList<Peliculas>(),this);
        serieAdaptador = new SerieAdaptador(this,new ArrayList<Serie>(),this);

        //Llamar a la action bar para mostrar
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        }

        //LLamar Navigation View
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //recycler
        recyclerViewFav = findViewById(R.id.recylcerViewFavoritos);
        recyclerViewFav.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFav.setLayoutManager(llm);

        llamarFragments();
        cargarViewPager();

        final Integer tab  = viewPager.getCurrentItem();
        final LinearLayout llFav = findViewById(R.id.llFav);

        if (currentUser!=null) {
            DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
            usuarioPerfilDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cargarFavoritos();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //LLAMAR AL FAB BUTTON
        FloatingActionButton fabFiltros = findViewById(R.id.fabFiltro);
        final FiltroFragment filtroFragment = new FiltroFragment();

        fabFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Bundle bundle = new Bundle();
               bundle.putInt(FiltroFragment.KEY_TAB,tab);
               filtroFragment.setArguments(bundle);

               cargarFiltros(filtroFragment);

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 0){
                    llFav.setVisibility(View.VISIBLE);
                    recyclerViewFav.setAdapter(peliculaAdaptador);
                }else if (position == 1){
                    llFav.setVisibility(View.VISIBLE);
                    recyclerViewFav.setAdapter(serieAdaptador);
                }else {
                    llFav.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0){
                    llFav.setVisibility(View.VISIBLE);
                    recyclerViewFav.setAdapter(peliculaAdaptador);
                }else if (position == 1){
                    llFav.setVisibility(View.VISIBLE);
                    recyclerViewFav.setAdapter(serieAdaptador);
                }else {
                    llFav.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void cargarFavoritos(){

        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();

        if (currentUser!=null){
            final List<Peliculas> favoritas = new ArrayList<>();
            final List<Serie> favoritasS = new ArrayList<>();
            final TextView textSinFav = findViewById(R.id.textSinFav);

            final ControllerPelicula controllerPelicula = new ControllerPelicula();
            final ControllerSerie controllerSerie = new ControllerSerie();
            DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
            usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);

                    if (usuario.getPeliculasFavoritas()!=null){
                        for (int i = 0; i < usuario.getPeliculasFavoritas().size(); i++) {
                            controllerPelicula.entregarUnaPelicula(usuario.getPeliculasFavoritas().get(i), MainActivity.this, new ResultListener<Peliculas>() {
                                @Override
                                public void finish(Peliculas Resultado) {
                                    favoritas.add(Resultado);
                                    peliculaAdaptador.setPeliculas(favoritas);
                                    if (favoritas.size()>0){
                                        textSinFav.setText("");
                                    }else {
                                        textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                                    }
                                }
                            });
                        }
                    }else{
                        textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                    }
                    if (usuario.getSeriesFavoritas()!=null){
                        for (int i = 0; i < usuario.getSeriesFavoritas().size();i++){
                            controllerSerie.entregarUnaSerie(usuario.getSeriesFavoritas().get(i), MainActivity.this, new ResultListener<Serie>() {
                                @Override
                                public void finish(Serie Resultado) {
                                    favoritasS.add(Resultado);
                                    serieAdaptador.setSerieList(favoritasS);
                                    if (favoritasS.size()>0){
                                        textSinFav.setText("");
                                    }else {
                                        textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                                    }
                                }
                            });
                        }
                    }else{
                        textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
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
        switch (item.getItemId()){
            case R.id.itemAccount:
                // Si hay usuario logueado, va a la pantalla de perfil. Si no, va a la pantalla de Login
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goPerfilUsuario();
                    } else {
                    goLoginScreen();
                    }
//                Intent intentAccount = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intentAccount);
                return true;
//            case R.id.itemSearch:
//
//                Toast.makeText(this, "Item Search Selected", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFragmentInteraction() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {

        } else if (id == R.id.nav_signin) {
            Intent intentAccount = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentAccount);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cargarFiltros(DialogFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager,"filtro");
    }

    public void cargarViewPager(){
        //ViewPager
        viewPager = findViewById(R.id.viewPager);
        //Adapter
        adapter.setFragmentList(fragmentList);
        viewPager.setAdapter(adapter);
        //TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        //Asociar al view pager
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_movie_tab);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tv_tab);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_group_tab);

    }

    public void llamarFragments(){
        //Llamar al FragmentPeliculas
        PeliculasFragment peliculasFragment = new PeliculasFragment();
        ComentariosFragment comentariosFragment = new ComentariosFragment();
        SeriesFragment seriesFragment = new SeriesFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(peliculasFragment);
        fragmentList.add(seriesFragment);
        fragmentList.add(comentariosFragment);
    }

    @Override
    public void dameListaFiltro(Integer filtro, Integer tab, final String nombre) {
        switch (tab){
            case 0:
                final RecyclerView recyclerView = findViewById(R.id.recylcerViewPeliculas);
                ControllerPelicula controllerPelicula = new ControllerPelicula();
                controllerPelicula.entregarPeliculasGeneros(MainActivity.this, filtro, new ResultListener<List<Peliculas>>() {
                    @Override
                    public void finish(List<Peliculas> Resultado) {
                        cargarRecyclerGrid(MainActivity.this, recyclerView, Resultado, new PeliculaAdaptador.AdapterPeliInterface() {
                            @Override
                            public void irTrailer(Peliculas peliculas) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreFiltro = findViewById(R.id.peliculasTodas);
                        nombreFiltro.setText(nombre);
                    }
                });
                break;
            case 1:
                final RecyclerView recyclerViewSeries = findViewById(R.id.recylcerViewSeries);
                ControllerSerie controllerSerie = new ControllerSerie();
                controllerSerie.entregarSerieGeneros(MainActivity.this, filtro, new ResultListener<List<Serie>>() {
                    @Override
                    public void finish(List<Serie> Resultado) {
                        cargarRecyclerGridSeries(MainActivity.this, recyclerViewSeries, Resultado, new SerieAdaptador.AdapterSerieInterface() {
                            @Override
                            public void irTrailer(Serie Serie) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(TrailerActivity.KEY_NOMBRE, Serie.getName());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), Serie.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, Serie.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreSerieFiltro = findViewById(R.id.SeriesTodas);
                        nombreSerieFiltro.setText(nombre);
                    }
                });
                break;
            case 2:

                break;
        }

    }

    @Override
    public void mostrarTodos(Integer tab) {
        switch (tab){
            case 0:
                final RecyclerView recyclerView = findViewById(R.id.recylcerViewPeliculas);
                ControllerPelicula controllerPelicula = new ControllerPelicula();
                controllerPelicula.entregarPeliculas(MainActivity.this, new ResultListener<List<Peliculas>>() {
                    @Override
                    public void finish(List<Peliculas> Resultado) {
                        cargarRecyclerGrid(MainActivity.this, recyclerView, Resultado, new PeliculaAdaptador.AdapterPeliInterface() {
                            @Override
                            public void irTrailer(Peliculas peliculas) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreFiltro = findViewById(R.id.peliculasTodas);
                        nombreFiltro.setText(getResources().getString(R.string.peliculasTodas));
                    }
                });
                break;
            case 1:
                final RecyclerView recyclerViewSeries = findViewById(R.id.recylcerViewSeries);
                ControllerSerie controllerSerie = new ControllerSerie();
                controllerSerie.entregarSerie(MainActivity.this, new ResultListener<List<Serie>>() {
                    @Override
                    public void finish(List<Serie> Resultado) {
                        cargarRecyclerGridSeries(MainActivity.this, recyclerViewSeries, Resultado, new SerieAdaptador.AdapterSerieInterface() {
                            @Override
                            public void irTrailer(Serie Serie) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(TrailerActivity.KEY_NOMBRE, Serie.getName());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), Serie.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, Serie.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreSerieFiltro = findViewById(R.id.SeriesTodas);
                        nombreSerieFiltro.setText(getResources().getString(R.string.seriesTodas));
                    }
                });
                break;
            case 2:

                break;
        }
    }

    public void cargarRecyclerGrid(Context context, RecyclerView recyclerView,List<Peliculas> peliculas, PeliculaAdaptador.AdapterPeliInterface escuchador){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        recyclerView.setLayoutManager(glm);

        PeliculaAdaptador peliculaAdaptador = new PeliculaAdaptador(context,peliculas,escuchador);
        recyclerView.setAdapter(peliculaAdaptador);
    }
    public void cargarRecyclerGridSeries(Context context, RecyclerView recyclerView,List<Serie> series, SerieAdaptador.AdapterSerieInterface escuchador){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        recyclerView.setLayoutManager(glm);

        SerieAdaptador serieAdaptador = new SerieAdaptador(context,series,escuchador);
        recyclerView.setAdapter(serieAdaptador);
    }

    // LOGIN METHODS

    private void goPerfilUsuario(){
        Intent intent = new Intent(MainActivity.this, PerfilUsuarioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goLoginScreen(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void irTrailer(Peliculas peliculas) {
        Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TrailerActivity.KEY_TIPO,1);
        bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,peliculas.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void irTrailer(Serie Serie) {
        Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TrailerActivity.KEY_TIPO,2);
        bundle.putString(TrailerActivity.KEY_NOMBRE, Serie.getName());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), Serie.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, Serie.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,Serie.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
