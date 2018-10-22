package com.example.digital.borradorproyectointegrador.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digital.borradorproyectointegrador.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {


    public ComentariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comentarios, container, false);
    }

}
