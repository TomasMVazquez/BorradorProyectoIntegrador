package com.example.digital.borradorproyectointegrador.view.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.controller.ControllerUsuarioPerfil;
import com.example.digital.borradorproyectointegrador.dao.database.DaoPeliculaDB;
import com.example.digital.borradorproyectointegrador.dao.database.DatabaseHelper;
import com.example.digital.borradorproyectointegrador.dao.database.MyDatabase;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.util.Util;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.AgregarComentarioActivity;
import com.example.digital.borradorproyectointegrador.view.MainActivity;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PeliculasFragment extends Fragment implements PeliculaAdaptador.AdapterPeliInterface {

    private PeliculaAdaptador peliculaAdaptador;
    private ControllerPelicula controllerPelicula;
    private DaoPeliculaDB daoPeliculaDB;

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_peliculas, container, false);
        //Usuarios
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();

        //Adaptador Peliculas
        peliculaAdaptador = new PeliculaAdaptador(view.getContext(),new ArrayList<Peliculas>(),this);

        //recycler favoritos
        RecyclerView recyclerViewFav = view.findViewById(R.id.recylcerViewFavoritosP);
        recyclerViewFav.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFav.setLayoutManager(llm);
        recyclerViewFav.setAdapter(peliculaAdaptador);

        //Recycler view de Peliculas
        //Lista
        final RecyclerView recyclerViewPeli = view.findViewById(R.id.recylcerViewPeliculas);
        //Controller
        controllerPelicula = new ControllerPelicula(getContext());

        controllerPelicula.entregarPeliculas(view.getContext(), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                cargarRecyclerGrid(view.getContext(),recyclerViewPeli,Resultado,PeliculasFragment.this);
            }
        });

        //Favoritos

        if (currentUser !=null){
            final List<Peliculas> favoritas = new ArrayList<>();
            final TextView textSinFav = view.findViewById(R.id.textSinFavP);

            if (Util.hayInternet(getContext())) {
                DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
                usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);

                        if (usuario.getPeliculasFavoritas() != null) {
                            for (int i = 0; i < usuario.getPeliculasFavoritas().size(); i++) {
                                controllerPelicula.entregarUnaPelicula(usuario.getPeliculasFavoritas().get(i), view.getContext(), new ResultListener<Peliculas>() {
                                    @Override
                                    public void finish(Peliculas Resultado) {
                                        favoritas.add(Resultado);
                                        MyDatabase database = DatabaseHelper.getInstance(getContext().getApplicationContext());
                                        daoPeliculaDB = database.getDaoPeliculaDB();
                                        daoPeliculaDB.insertarPeliculas(favoritas);
                                        peliculaAdaptador.setPeliculas(favoritas);
                                        if (favoritas.size() > 0) {
                                            textSinFav.setText("");
                                        } else {
                                            textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                                        }
                                    }
                                });
                            }
                        } else {
                            textSinFav.setText(getResources().getString(R.string.favoritosVacio));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }else {
                controllerPelicula.entregarPeliculas(getContext(), new ResultListener<List<Peliculas>>() {
                    @Override
                    public void finish(List<Peliculas> Resultado) {
                        peliculaAdaptador.setPeliculas(Resultado);
                        textSinFav.setText("");
                    }
                });
            }
        }
        return view;
    }

    public void cargarRecyclerGrid(Context context, RecyclerView recyclerView,List<Peliculas> peliculas, PeliculaAdaptador.AdapterPeliInterface escuchador){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        recyclerView.setLayoutManager(glm);

        PeliculaAdaptador peliculaAdaptador = new PeliculaAdaptador(context,peliculas,escuchador);
        recyclerView.setAdapter(peliculaAdaptador);
    }

    @Override
    public void irTrailer(Peliculas peliculas) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        Integer cantEstr = Math.getExponent(Math.round(((peliculas.getVote_average() * 5) / 10)));
        bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
        bundle.putInt(TrailerActivity.KEY_TIPO,0);
        bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,peliculas.getPoster_path());
        bundle.putString(TrailerActivity.KEY_RELEASE_DATE, peliculas.getRelease_date());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
