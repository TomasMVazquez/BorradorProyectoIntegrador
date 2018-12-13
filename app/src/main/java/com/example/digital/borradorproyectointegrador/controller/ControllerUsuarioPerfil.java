package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.dao.internet.dao_perfil_usuario.DAOUsuarioPerfil;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ControllerUsuarioPerfil {

    public void entregarPeliFavoritas(Context context, FirebaseUser user, final ResultListener<List<Integer>> resultListener){

        if (Util.hayInternet(context)){
            DAOUsuarioPerfil daoUsuarioPerfil = new DAOUsuarioPerfil();
            daoUsuarioPerfil.damePeliFavoritas(context, user, new ResultListener<List<Integer>>() {
                @Override
                public void finish(List<Integer> Resultado) {
                    resultListener.finish(Resultado);
                }
            });
        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }
    }

    public void entregarSerieFavoritas(Context context, FirebaseUser user, final ResultListener<List<Integer>> resultListener){

        if (Util.hayInternet(context)){
            DAOUsuarioPerfil daoUsuarioPerfil = new DAOUsuarioPerfil();
            daoUsuarioPerfil.dameSerieFavoritas(context, user, new ResultListener<List<Integer>>() {
                @Override
                public void finish(List<Integer> Resultado) {
                    resultListener.finish(Resultado);
                }
            });
        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }
    }
}
