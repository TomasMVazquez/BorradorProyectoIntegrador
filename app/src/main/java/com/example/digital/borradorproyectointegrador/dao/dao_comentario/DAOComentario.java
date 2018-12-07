package com.example.digital.borradorproyectointegrador.dao.dao_comentario;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
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

    public void dameComentarios(Context context, final ResultListener<List<Comentario>> listResultListener){
        //Gerente
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();
        DatabaseReference comentariosDB = mReference.child(context.getResources().getString(R.string.child_base_comentarios));

        final List<Comentario> comentarioList = new ArrayList<>();

        listResultListener.finish(comentarioList);
    }

}
