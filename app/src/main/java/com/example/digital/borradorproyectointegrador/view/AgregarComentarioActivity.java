package com.example.digital.borradorproyectointegrador.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class AgregarComentarioActivity extends AppCompatActivity {

    public static final String KEY_ID="id";
    public static final String KEY_TIPO="tipo";
    public static final String KEY_TITLE="title";
    public static final String KEY_POSTER_PATH="poster";

    private Integer idPelioSerie;
    private String title;
    private Integer valoracion;
    private Integer tipo;
    private String posterPath;
    private Integer cantComentarios;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference usuarioPerfilDB;
    private DatabaseReference comentariosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comentario);

        this.setFinishOnTouchOutside(false);

        //Firebase
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
        comentariosDB = mReference.child(getResources().getString(R.string.child_base_comentarios));

        //OBTENER DATOS DE LA PELI O SERIE
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idPelioSerie = bundle.getInt(KEY_ID);
        tipo = bundle.getInt(KEY_TIPO);
        title = bundle.getString(KEY_TITLE);
        posterPath = bundle.getString(KEY_POSTER_PATH);

        //TODO como sacar laimagen

        Button btnAgregarComentario = findViewById(R.id.btnAgregarComentario);
        final EditText agregarComentarioEditText = findViewById(R.id.agregarComentarioEditText);
        TextInputLayout agregarComentTextLayout = findViewById(R.id.agregarComentTextLayout);
        RatingBar ratingAgregarComentario = findViewById(R.id.ratingAgregarComentario);

        ratingAgregarComentario.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                valoracion = Math.round(rating);
            }
        });

        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valoracion != null){

                    final String texto = agregarComentarioEditText.getText().toString();

                    if (currentUser!=null) {

                        comentariosDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String id_1 = String.valueOf(idPelioSerie);
                                final DatabaseReference nuevoComentarioDB = comentariosDB.child(id_1);
                                final String photo = currentUser.getPhotoUrl() + "?height=500";

                                nuevoComentarioDB.child(currentUser.getUid()).setValue(new Comentario(idPelioSerie,tipo,title,posterPath,photo,currentUser.getDisplayName(),valoracion,texto,currentUser.getUid()));

                                usuarioPerfilDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UsuarioPerfil usuarioPerfil = dataSnapshot.getValue(UsuarioPerfil.class);
                                        cantComentarios = usuarioPerfil.getCantidadComentarios();
                                        cantComentarios = cantComentarios + 1;
                                        usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_cant_comentarios)).setValue(cantComentarios);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    Intent data = TrailerActivity.respuestaAgregarComentario();
                    setResult(Activity.RESULT_OK,data);
                    finish();

                }else {
                    Toast.makeText(AgregarComentarioActivity.this, "Aún no has puesto cuantas estrellas de darías =)", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
