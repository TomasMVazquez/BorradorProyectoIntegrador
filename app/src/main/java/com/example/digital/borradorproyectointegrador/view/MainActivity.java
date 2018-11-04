package com.example.digital.borradorproyectointegrador.view;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.MyViewPagerAdapter;
import com.example.digital.borradorproyectointegrador.view.Fragments.ComentariosFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.PeliculasFragment;
import com.example.digital.borradorproyectointegrador.view.Fragments.SeriesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements PeliculasFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    SearchView searchView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llamar a la action bar para mostrar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        //LLamar Navigation View
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Llamar el Search View
//        searchView = findViewById(R.id.itemSearch);


        //Llamar al FragmentPeliculas
        PeliculasFragment peliculasFragment = new PeliculasFragment();
        ComentariosFragment comentariosFragment = new ComentariosFragment();
        SeriesFragment seriesFragment = new SeriesFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(peliculasFragment);
        fragmentList.add(seriesFragment);
        fragmentList.add(comentariosFragment);

        List<String> titulos = new ArrayList<>();
        titulos.add(getResources().getString(R.string.movie_tab));
        titulos.add(getResources().getString(R.string.serie_tab));
        titulos.add(getResources().getString(R.string.comunidad_tab));

        //ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);

        //TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        //Asociar al view pager
        tabLayout.setupWithViewPager(viewPager);

        //Adapter
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList,titulos);
        viewPager.setAdapter(adapter);
<<<<<<< Updated upstream
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_movie_tab);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tv_tab);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_group_tab);
=======
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_movie_tab);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group_tab);
>>>>>>> Stashed changes


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
                Intent intentAccount = new Intent(MainActivity.this, MultiLogIn.class);
                startActivity(intentAccount);
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemSearch:

                Toast.makeText(this, "Item Search Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onFragmentInteraction() {
        Intent intent = new Intent(this,MultiLogIn.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {

        } else if (id == R.id.nav_signin) {
            Intent intentAccount = new Intent(MainActivity.this, MultiLogIn.class);
            startActivity(intentAccount);
        } else if (id == R.id.nav_encuestas) {

        } else if (id == R.id.nav_listas) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cargarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager,fragment);
        fragmentTransaction.commit();
    }

}
