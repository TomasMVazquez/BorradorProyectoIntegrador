package com.example.digital.borradorproyectointegrador.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.digital.borradorproyectointegrador.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.util.Objects;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private DatabaseReference usuarioPerfilDB;

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

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //cargar datos
        CircularImageView perfilImagen = findViewById(R.id.perfilImagen);
        TextView nombrePerfil = findViewById(R.id.nombrePerfil);
        TextView cantMeGustaPerfil = findViewById(R.id.cantMeGustaPerfil);
        TextView cantCompartirPerfil = findViewById(R.id.cantCompartirPerfil);
        TextView cantComentariosPerfil = findViewById(R.id.cantComentariosPerfil);
        RecyclerView recyclerComentariosPerfil = findViewById(R.id.recyclerComentariosPerfil);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(perfilImagen);
        nombrePerfil.setText(currentUser.getDisplayName());
        usuarioPerfilDB = mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getEmail());
        cantMeGustaPerfil.setText((CharSequence) usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_cant_me_gusta)));
        cantCompartirPerfil.setText((CharSequence) usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_cant_compartidos)));
        cantComentariosPerfil.setText((CharSequence) usuarioPerfilDB.child(getResources().getString(R.string.child_usuario_perfil_cant_comentarios)));

        //TODO falta el recycler de comentarios
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        switch (item.getItemId()) {
            case R.id.itemAccount:
                return true;

            case R.id.home:
                Intent intentAccount = new Intent(PerfilUsuarioActivity.this, MainActivity.class);
                startActivity(intentAccount);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
