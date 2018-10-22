package com.example.digital.borradorproyectointegrador.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digital.borradorproyectointegrador.Adaptadores.AdaptadorRecyclerPeliculaSerie;
import com.example.digital.borradorproyectointegrador.PeliculaSerie;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.TrailerActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PeliculasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PeliculasFragment extends Fragment implements AdaptadorRecyclerPeliculaSerie.AdapterInterface {

    private OnFragmentInteractionListener mListener;

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peliculas, container, false);


        //Recycler view
        //Datos

//        ImageView cars = new ImageView(this.getContext());
//        ImageView coco = new ImageView(this.getContext());
//        ImageView starwars = new ImageView(this.getContext());
//        ImageView tomorrowland = new ImageView(this.getContext());
//        cars.setImageResource(R.drawable.cars);
//        coco.setImageResource(R.drawable.coco);
//        starwars.setImageResource(R.drawable.starwars);
//        tomorrowland.setImageResource(R.drawable.tomorrowland);
//
//        List<PeliculaSerie> peliculaSeries = new ArrayList<>();
//        peliculaSeries.add(new PeliculaSerie(cars, 2));
//        peliculaSeries.add(new PeliculaSerie(coco, 1));
//        peliculaSeries.add(new PeliculaSerie(starwars, 4));
//        peliculaSeries.add(new PeliculaSerie(tomorrowland, 5));
//        peliculaSeries.add(new PeliculaSerie(cars, 2));
//        peliculaSeries.add(new PeliculaSerie(coco, 1));
//        peliculaSeries.add(new PeliculaSerie(starwars, 4));
//        peliculaSeries.add(new PeliculaSerie(tomorrowland, 5));

        List<PeliculaSerie> peliculaSeries = new ArrayList<>();
        peliculaSeries.add(new PeliculaSerie(R.drawable.cars, "Cars", 3, "2LeOH9AGJQM", R.string.resumen_CARS));
        peliculaSeries.add(new PeliculaSerie(R.drawable.coco, "Coco", 1, "Ga6RYejo6Hk", R.string.resumen_COCO));
        peliculaSeries.add(new PeliculaSerie(R.drawable.starwars, "Star Wars", 4, "Q0CbN8sfihY", R.string.resumen_STAR_WARS));
        peliculaSeries.add(new PeliculaSerie(R.drawable.tomorrowland, "Tomorrowland", 5, "lWZ7O-RrATY", R.string.resumen_TOMORROWLAND));
        peliculaSeries.add(new PeliculaSerie(R.drawable.cars, "Cars", 3, "2LeOH9AGJQM", R.string.resumen_CARS));
        peliculaSeries.add(new PeliculaSerie(R.drawable.coco, "Coco", 1, "Ga6RYejo6Hk", R.string.resumen_COCO));
        peliculaSeries.add(new PeliculaSerie(R.drawable.starwars, "Star Wars", 4, "Q0CbN8sfihY", R.string.resumen_STAR_WARS));
        peliculaSeries.add(new PeliculaSerie(R.drawable.tomorrowland, "Tomorrowland", 5, "lWZ7O-RrATY", R.string.resumen_TOMORROWLAND));




        //Lista
        RecyclerView recyclerViewPrimero = view.findViewById(R.id.recylcerViewPrimero);
        RecyclerView recyclerViewSegundo = view.findViewById(R.id.recylcerViewSegundo);
        RecyclerView recyclerViewTercero = view.findViewById(R.id.recylcerViewTercero);
        RecyclerView recyclerViewCuarto = view.findViewById(R.id.recylcerViewCuarto);
        RecyclerView recyclerViewQuinto = view.findViewById(R.id.recylcerViewQuinto);

        //Mejora el desempeño si el tamaño del recycler no cambia durante la ejecucion
        recyclerViewPrimero.setHasFixedSize(true);
        recyclerViewSegundo.setHasFixedSize(true);
        recyclerViewTercero.setHasFixedSize(true);
        recyclerViewCuarto.setHasFixedSize(true);
        recyclerViewQuinto.setHasFixedSize(true);

        //Como se muestra
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPrimero.setLayoutManager(layoutManager1);
        recyclerViewSegundo.setLayoutManager(layoutManager2);
        recyclerViewTercero.setLayoutManager(layoutManager3);
        recyclerViewCuarto.setLayoutManager(layoutManager4);
        recyclerViewQuinto.setLayoutManager(layoutManager5);

        //Adapter
        AdaptadorRecyclerPeliculaSerie adaptadorRecyclerPeliculaSerie = new AdaptadorRecyclerPeliculaSerie(peliculaSeries, this);

        recyclerViewPrimero.setAdapter(adaptadorRecyclerPeliculaSerie);
        recyclerViewSegundo.setAdapter(adaptadorRecyclerPeliculaSerie);
        recyclerViewTercero.setAdapter(adaptadorRecyclerPeliculaSerie);
        recyclerViewCuarto.setAdapter(adaptadorRecyclerPeliculaSerie);
        recyclerViewQuinto.setAdapter(adaptadorRecyclerPeliculaSerie);

        return view;


    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed() {
//        if (mListener != null) {
//            mListener.onFragmentInteraction();
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void irTrailer(PeliculaSerie peliculaSerie) {


        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TrailerActivity.VIDEO_ID, peliculaSerie.getVideoId());
        bundle.putString(TrailerActivity.KEY_NOMBRE, peliculaSerie.getNombre());
        bundle.putString(TrailerActivity.KEY_IMAGE, peliculaSerie.getImagen().toString());
        bundle.putInt(TrailerActivity.KEY_CANT_ESTRELLAS, peliculaSerie.getCantEstrellas());
        bundle.putInt(TrailerActivity.KEY_RESUMEN, peliculaSerie.getResumen());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
