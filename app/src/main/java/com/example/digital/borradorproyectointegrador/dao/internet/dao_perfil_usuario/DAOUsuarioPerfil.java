package com.example.digital.borradorproyectointegrador.dao.internet.dao_perfil_usuario;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioPerfil {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Peliculas> peliculasFavoritas = new ArrayList<>();

    public void damePeliFavoritas(Context context, FirebaseUser user, final ResultListener<List<Integer>> listenerController){
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();
        DatabaseReference usuarioPerfilDB = mReference.child(context.getResources().getString(R.string.child_usuarios)).child(user.getUid());
        usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);
                listenerController.finish(usuario.getPeliculasFavoritas());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void dameSerieFavoritas(Context context, FirebaseUser user, final ResultListener<List<Integer>> listenerController){
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();
        DatabaseReference usuarioPerfilDB = mReference.child(context.getResources().getString(R.string.child_usuarios)).child(user.getUid());
        usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);
                listenerController.finish(usuario.getSeriesFavoritas());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
