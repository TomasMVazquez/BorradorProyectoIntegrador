package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.dao.database.DaoSerieDB;
import com.example.digital.borradorproyectointegrador.dao.database.DatabaseHelper;
import com.example.digital.borradorproyectointegrador.dao.database.MyDatabase;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.SerieAdaptador;
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


public class SeriesFragment extends Fragment implements SerieAdaptador.AdapterSerieInterface {

    private SerieAdaptador serieAdaptador;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_series, container, false);
        //Usuarios
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();

        //Adaptador
        serieAdaptador = new SerieAdaptador(view.getContext(),new ArrayList<Serie>(),this);

        //recycler favoritos
        RecyclerView recyclerViewFav = view.findViewById(R.id.recylcerViewFavoritosS);
        recyclerViewFav.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFav.setLayoutManager(llm);
        recyclerViewFav.setAdapter(serieAdaptador);

        //Recycler view
        //Lista
        final RecyclerView recyclerViewSeri = view.findViewById(R.id.recylcerViewSeries);

        final ControllerSerie controllerSerie = new ControllerSerie(getContext());

        controllerSerie.entregarSerie(view.getContext(), new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecyclerGrid(view.getContext(),recyclerViewSeri,Resultado,SeriesFragment.this);
            }
        });

        if (currentUser!=null){
            final List<Serie> favoritasS = new ArrayList<>();
            final TextView textSinFav = view.findViewById(R.id.textSinFavS);

            if (Util.hayInternet(getContext())) {
                DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
                usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);

                        if (usuario.getSeriesFavoritas() != null) {
                            for (int i = 0; i < usuario.getSeriesFavoritas().size(); i++) {
                                controllerSerie.entregarUnaSerie(usuario.getSeriesFavoritas().get(i), view.getContext(), new ResultListener<Serie>() {
                                    @Override
                                    public void finish(Serie Resultado) {
                                        favoritasS.add(Resultado);
                                        MyDatabase database = DatabaseHelper.getInstance(getContext().getApplicationContext());
                                        DaoSerieDB daoSerieDB = database.getDaoSerieDB();
                                        daoSerieDB.insertarSeries(favoritasS);
                                        serieAdaptador.setSerieList(favoritasS);
                                        if (favoritasS.size() > 0) {
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
                controllerSerie.entregarSerie(getContext(), new ResultListener<List<Serie>>() {
                    @Override
                    public void finish(List<Serie> Resultado) {
                        serieAdaptador.setSerieList(Resultado);
                        textSinFav.setText("");
                    }
                });
            }

        }

        return view;
    }

    @Override
    public void irTrailer(Serie serie) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TrailerActivity.KEY_TIPO,1);
        Integer cantEstr = Math.getExponent(Math.round(((serie.getVote_average() * 5) / 10)));
        bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS,cantEstr);
        bundle.putString(TrailerActivity.KEY_NOMBRE, serie.getName());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), serie.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, serie.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,serie.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void cargarRecyclerGrid(Context context, RecyclerView recyclerView,List<Serie> series, SerieAdaptador.AdapterSerieInterface escuchador){
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        recyclerView.setLayoutManager(glm);
        SerieAdaptador serieAdaptador = new SerieAdaptador(context,series,escuchador);
        recyclerView.setAdapter(serieAdaptador);
    }
}
