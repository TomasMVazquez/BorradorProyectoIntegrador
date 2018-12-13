package com.example.digital.borradorproyectointegrador.dao.internet.dao_comentario;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAOComentario {

    private List<Comentario> comentarioList;

    public void dameComentariosPeli(String id, Context context, final ResultListener<List<Comentario>> listResultListener){
        //Gerente
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();
        DatabaseReference comentariosDB = mReference.child(context.getResources().getString(R.string.child_base_comentarios));

        final List<Comentario> comentarioList = new ArrayList<>();

        comentariosDB.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    comentarioList.add(child.getValue(Comentario.class));
                }
                listResultListener.finish(comentarioList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void dameComentarios(final Context context, final ResultListener<List<Comentario>> listResultListener){
        //Gerente
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();
        DatabaseReference comentariosDB = mReference.child(context.getResources().getString(R.string.child_base_comentarios));

        final List<Comentario> comentarioList = new ArrayList<>();
        final List<String> ids = new ArrayList<>();

        comentariosDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    comentarioList.add(child.getValue(Comentario.class));
                    listResultListener.finish(comentarioList);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void dameMisComentarios(final String user, final Context context, final ResultListener<List<Comentario>> listResultListener){
        //Gerente
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();
        DatabaseReference comentariosDB = mReference.child(context.getResources().getString(R.string.child_base_comentarios));

        final List<Comentario> comentarioList = new ArrayList<>();

        comentariosDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    if (String.valueOf(child.getKey()).equals(user)) {
                        comentarioList.add(child.getValue(Comentario.class));
                        listResultListener.finish(comentarioList);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
