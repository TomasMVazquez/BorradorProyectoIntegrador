package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentarioTrailer;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentariosCompletos;
import com.example.digital.borradorproyectointegrador.view.PerfilUsuarioActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {

    private Integer sumarUno;
    private Integer restarUno;
    private AdaptadorRecyclerComentariosCompletos adaptadorRecyclerComentariosCompletos;

    public ComentariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentarios, container, false);
        //usuario
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Gerente
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mReference  = mDatabase.getReference();

        //adaptador
        adaptadorRecyclerComentariosCompletos = new AdaptadorRecyclerComentariosCompletos(currentUser, view.getContext(), new AdaptadorRecyclerComentariosCompletos.ComentarioInterface() {
            @Override
            public void irPerfil(Comentario comentario) {
                String user = comentario.getUserId();
                Intent intent = new Intent(getActivity(),PerfilUsuarioActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(PerfilUsuarioActivity.KEY_USER,user);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void botonesComentario(Integer boton, FirebaseUser user, Comentario comentario) {
                final DatabaseReference usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(comentario.getUserId()).child(getResources().getString(R.string.child_usuario_perfil_cant_me_gusta));
                DatabaseReference comentariosDB = mReference.child(getResources().getString(R.string.child_base_comentarios));
                switch (boton){
                    case 0: //BOTON ME GUSTA
                        if (comentario.getTvCantMeGusta()!=null) {
                            sumarUno = comentario.getTvCantMeGusta() + 1;
                        }else {
                            sumarUno = 1;
                        }
                        comentariosDB.child(comentario.getIdPelioSerie().toString()).child(comentario.getUserId()).child("tvCantMeGusta").setValue(sumarUno);
                        Toast.makeText(getContext(), "Gracias por participar!", Toast.LENGTH_SHORT).show();
                        usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                usuarioPerfilDB.setValue(Integer.valueOf(String.valueOf(dataSnapshot.getValue()))+1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 1: //BOTON NO ME GUSTA
                        if (comentario.getTvCantMeGusta()!=null) {
                            restarUno = comentario.getTvCantMeGusta() - 1;
                        }else {
                            restarUno = -1;
                        }
                        comentariosDB.child(comentario.getIdPelioSerie().toString()).child(comentario.getUserId()).child("tvCantMeGusta").setValue(restarUno);
                        Toast.makeText(getContext(), "Gracias por participar!", Toast.LENGTH_SHORT).show();
                        usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                usuarioPerfilDB.setValue(Integer.valueOf(String.valueOf(dataSnapshot.getValue()))-1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 2: //BOTON COMPARTIR
                        String titulo = getContext().getResources().getString(R.string.compartir_comentario_titulo) + " " + comentario.getPeliculaComentada() + ": ";

                        String mensaje = titulo + " " + comentario.getTvUsuarioComentario() + " " + getContext().getResources().getString(R.string.compartir_comentario_quien) + " " + comentario.getTvComentarioComentario();

                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_inicio), Toast.LENGTH_SHORT).show();
                        //Creamos un share de tipo ACTION_SENT
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                        //Indicamos que voy a compartir texto
                        share.setType("text/plain");
                        //Le agrego un título
                        share.putExtra(Intent.EXTRA_SUBJECT, titulo);
                        //Le agrego el texto a compartir (Puede ser un link tambien)
                        share.putExtra(Intent.EXTRA_TEXT, mensaje);
                        //Hacemos un start para que comparta el contenido.
                        startActivity(Intent.createChooser(share, getContext().getResources().getString(R.string.toast_inicio)));
                        break;
                }
                recargarRecycler();
            }
        }, new ArrayList<Comentario>());


        //Recycler
        final RecyclerView recyclerViewComentario = view.findViewById(R.id.recyclerComentarios);
        recyclerViewComentario.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewComentario.setLayoutManager(llm);

        recargarRecycler();


        recyclerViewComentario.setAdapter(adaptadorRecyclerComentariosCompletos);
        return view;
    }

    public void recargarRecycler(){

        //datos
        ComentariosController comentariosController = new ComentariosController();
        comentariosController.entregarListaComentarios(getContext(), new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                adaptadorRecyclerComentariosCompletos.setComentarioList(Resultado);
            }
        });
    }

}
