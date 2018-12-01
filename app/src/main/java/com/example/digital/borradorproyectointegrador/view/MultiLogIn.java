package com.example.digital.borradorproyectointegrador.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.usuario_perfil.UsuarioPerfil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MultiLogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseUser currentUser;
    private FirebaseStorage mStorage;
    private CallbackManager callbackManager;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_logins);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //Gerente
        mDatabase = FirebaseDatabase.getInstance();
        mReference  = mDatabase.getReference();
        final StorageReference raiz = mStorage.getReference();

        Toolbar toolbar = findViewById(R.id.toolbarMultiLogin);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LoginButton btnFb2 = findViewById(R.id.btnFb2);
        btnFb2.setReadPermissions("email", "public_profile");

        //Esta parte es para que responda el login al intentar cmpartir o algo
        btnFb2.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (currentUser!= null) {
                    if (mReference.child(getResources().getString(R.string.child_usuarios)).child(currentUser.getEmail()) != null) {
                        Intent volver = TrailerActivity.respuestaLogin();
                        setResult(TrailerActivity.RESULT_OK);
                        finish();
                    } else {
                        //La idea aca es que cada persona tenga un perfil en la base desde la cual podamos ver sus favoritos y listas
                        final Uri uriTemp = currentUser.getPhotoUrl();
                        StorageReference fotoPerfil = raiz.child(getResources().getString(R.string.child_fotos_usuarios)).child(uriTemp.getLastPathSegment());
                        UploadTask uploadTask = fotoPerfil.putFile(uriTemp);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                agregarPerfilABaseDeDatos(currentUser.getEmail(), uriTemp.getLastPathSegment(), currentUser.getDisplayName());
                            }
                        });
                        Intent volver = TrailerActivity.respuestaLogin();
                        setResult(TrailerActivity.RESULT_OK);
                        finish();
                    }
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //Aca termina la parte que responda el login.....


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void agregarPerfilABaseDeDatos(String email, String imagen,String nombre){
        DatabaseReference id = mReference.child(getResources().getString(R.string.child_usuarios)).child(email).push();
        id.setValue(new UsuarioPerfil(email,imagen, nombre,0,0,new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));
    }

}
