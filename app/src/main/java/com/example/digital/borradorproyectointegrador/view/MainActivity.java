package com.example.digital.borradorproyectointegrador.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.MyViewPagerAdapter;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.SerieAdaptador;
import com.example.digital.borradorproyectointegrador.view.Fragments.ComentariosFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.FiltroFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.PeliculasFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.SeriesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FiltroFragment.FragmentInterface,PeliculaAdaptador.AdapterPeliInterface,SerieAdaptador.AdapterSerieInterface {

    private MyViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Util.printHash(this);

        //adaptadores
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),new ArrayList<Fragment>());

        //Llamar a la action bar para mostrar
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        }

        //LLamar Navigation View
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //cargar Main
        llamarFragments();
        cargarViewPager();

        //LLAMAR AL FAB BUTTON
        FloatingActionButton fabFiltros = findViewById(R.id.fabFiltro);
        final FiltroFragment filtroFragment = new FiltroFragment();

        fabFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Integer tab  = viewPager.getCurrentItem();
               Bundle bundle = new Bundle();
               bundle.putInt(FiltroFragment.KEY_TAB,tab);
               filtroFragment.setArguments(bundle);

               cargarFiltros(filtroFragment);

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
        switch (item.getItemId()){
            case R.id.itemAccount:
                // Si hay usuario logueado, va a la pantalla de perfil. Si no, va a la pantalla de Login
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goPerfilUsuario();
                    } else {
                    goLoginScreen();
                    }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                ControllerPelicula controllerPelicula = new ControllerPelicula(this);

                controllerPelicula.entregarPeliculasGeneros(MainActivity.this, filtro, new ResultListener<List<Peliculas>>() {
                    @Override
                    public void finish(List<Peliculas> Resultado) {
                        cargarRecyclerGrid(MainActivity.this, recyclerView, Resultado, new PeliculaAdaptador.AdapterPeliInterface() {
                            @Override
                            public void irTrailer(Peliculas peliculas) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                Integer cantEstr = Math.getExponent(Math.round(((peliculas.getVote_average() * 5) / 10)));
                                bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
                                bundle.putInt(TrailerActivity.KEY_TIPO,0);
                                bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreFiltro = findViewById(R.id.tituloPeliculas);
                        nombreFiltro.setText(nombre);
                    }
                });
                break;
            case 1:
                final RecyclerView recyclerViewSeries = findViewById(R.id.recylcerViewSeries);
                ControllerSerie controllerSerie = new ControllerSerie(this);
                controllerSerie.entregarSerieGeneros(MainActivity.this, filtro, new ResultListener<List<Serie>>() {
                    @Override
                    public void finish(List<Serie> Resultado) {
                        cargarRecyclerGridSeries(MainActivity.this, recyclerViewSeries, Resultado, new SerieAdaptador.AdapterSerieInterface() {
                            @Override
                            public void irTrailer(Serie Serie) {
                                Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                                Bundle bundle = new Bundle();
                                Integer cantEstr = Math.getExponent(Math.round(((Serie.getVote_average() * 5) / 10)));
                                bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
                                bundle.putInt(TrailerActivity.KEY_TIPO,1);
                                bundle.putString(TrailerActivity.KEY_NOMBRE, Serie.getName());
                                bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), Serie.getId());
                                bundle.putString(TrailerActivity.KEY_RESUMEN, Serie.getOverview());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        TextView nombreFiltro = findViewById(R.id.tituloSerie);
                        nombreFiltro.setText(nombre);
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
                ControllerPelicula controllerPelicula = new ControllerPelicula(this);
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
                        TextView nombreFiltro = findViewById(R.id.tituloPeliculas);
                        nombreFiltro.setText(getResources().getString(R.string.peliculasTodas));
                    }
                });
                break;
            case 1:
                final RecyclerView recyclerViewSeries = findViewById(R.id.recylcerViewSeries);
                ControllerSerie controllerSerie = new ControllerSerie(this);
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
                        TextView nombreSerieFiltro = findViewById(R.id.tituloSerie);
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
        Integer cantEstr = Math.getExponent(Math.round(((peliculas.getVote_average() * 5) / 10)));
        bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
        bundle.putInt(TrailerActivity.KEY_TIPO,0);
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
        Integer cantEstr = Math.getExponent(Math.round(((Serie.getVote_average() * 5) / 10)));
        bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
        bundle.putInt(TrailerActivity.KEY_TIPO,1);
        bundle.putString(TrailerActivity.KEY_NOMBRE, Serie.getName());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), Serie.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, Serie.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,Serie.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
