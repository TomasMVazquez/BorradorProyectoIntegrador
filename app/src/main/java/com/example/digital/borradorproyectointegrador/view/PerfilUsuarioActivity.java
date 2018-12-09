package com.example.digital.borradorproyectointegrador.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentarioTrailer;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentariosCompletos;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerfilUsuarioActivity extends AppCompatActivity {

    public static final String KEY_USER="user";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private DatabaseReference usuarioPerfilDB;
    private CallbackManager callbackManager;
    private Toolbar toolbar;

    private Integer sumarUno;
    private Integer restarUno;
    private AdaptadorRecyclerComentariosCompletos adaptadorRecyclerComentariosCompletos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        //usuario
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //Gerente
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();

        //adaptador
        adaptadorRecyclerComentariosCompletos = new AdaptadorRecyclerComentariosCompletos(currentUser, this, new AdaptadorRecyclerComentariosCompletos.ComentarioInterface() {
            @Override
            public void irPerfil(Comentario comentario) {
                Toast.makeText(PerfilUsuarioActivity.this, "Ya estas en el perfil", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PerfilUsuarioActivity.this, "Gracias por participar!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PerfilUsuarioActivity.this, "Gracias por participar!", Toast.LENGTH_SHORT).show();
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
                        String titulo = getResources().getString(R.string.compartir_comentario_titulo) + " " + comentario.getPeliculaComentada() + ": ";
                        String mensaje = titulo + " " + comentario.getTvUsuarioComentario() + " " + getResources().getString(R.string.compartir_comentario_quien) + " " + comentario.getTvComentarioComentario();

                        Toast.makeText(PerfilUsuarioActivity.this, getResources().getString(R.string.toast_inicio), Toast.LENGTH_SHORT).show();
                        //Creamos un share de tipo ACTION_SENT
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                        //Indicamos que voy a compartir texto
                        share.setType("text/plain");
                        //Le agrego un título
                        share.putExtra(Intent.EXTRA_SUBJECT, titulo);
                        //Le agrego el texto a compartir (Puede ser un link tambien)
                        share.putExtra(Intent.EXTRA_TEXT, mensaje);
                        //Hacemos un start para que comparta el contenido.
                        startActivity(Intent.createChooser(share, getResources().getString(R.string.toast_inicio)));
                        break;
                }
                recargarRecycler(comentario.getUserId());
            }
        }, new ArrayList<Comentario>());


        // TOOLBAR
        toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String user = bundle.getString(KEY_USER);
        String usuarioComentairios = null;

        if (user!=null){
            usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(user);
        }else{
            usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getUid());
        }

        //cargar datos
        final CircularImageView perfilImagen = findViewById(R.id.perfilImagen);
        final TextView nombrePerfil = findViewById(R.id.nombrePerfil);
        final TextView cantMeGustaPerfil = findViewById(R.id.cantMeGustaPerfil);
        final TextView cantCompartirPerfil = findViewById(R.id.cantCompartirPerfil);
        final TextView cantComentariosPerfil = findViewById(R.id.cantComentariosPerfil);
        RecyclerView recyclerComentariosPerfil = findViewById(R.id.recyclerComentariosPerfil);

        usuarioPerfilDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsuarioPerfil usuario = dataSnapshot.getValue(UsuarioPerfil.class);
                cantComentariosPerfil.setText(String.valueOf(usuario.getCantidadComentarios()));
                cantMeGustaPerfil.setText(String.valueOf(usuario.getCantidadMeGusta()));
                cantCompartirPerfil.setText(String.valueOf(usuario.getCantidadCompartidos()));

                String photo = usuario.getImagenPerfil() + "?height=500";
                Glide.with(PerfilUsuarioActivity.this).load(photo).into(perfilImagen);
                nombrePerfil.setText(usuario.getNombre());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //recycler de comentarios
        recyclerComentariosPerfil.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerComentariosPerfil.setLayoutManager(llm);
        recyclerComentariosPerfil.setAdapter(adaptadorRecyclerComentariosCompletos);
        if (user!=null){
            recargarRecycler(user);
        }else{
            recargarRecycler(currentUser.getUid());
        }

    }

    public void recargarDatosPerfil(){

    }

    public void recargarRecycler(String user){

        //datos
        ComentariosController comentariosController = new ComentariosController();
        comentariosController.entregarMisComentarios(user, PerfilUsuarioActivity.this, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                adaptadorRecyclerComentariosCompletos.setComentarioList(Resultado);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_profile_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == android.R.id.home)
//            finish();

        switch (item.getItemId()) {

            case R.id.itemLogout:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_lightbulb_outline_black_24dp)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you want to log out? You will not be able to view your favorites")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

//            case R.id.itemLogout:
//                logout();
//                break;
            default:
                    return false;




        }


    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goMainActivity();
        finish();
    }

    private void goMainActivity(){
        Intent intent = new Intent(PerfilUsuarioActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        goMainActivity();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        goMainActivity();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

}
