package com.example.digital.borradorproyectointegrador;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LogIn extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Llamo a los views (edittexts, botones, etc)

        //EDIT TEXTS
        EditText etUsuarioLogin             = findViewById(R.id.etUsuarioLogin);
        final EditText etContrasenaLogin          = findViewById(R.id.etContrasenaLogin);
        final EditText etRepitaContrasenaLogin    = findViewById(R.id.etRepitaContrasenaLogin);

        //IMAGE VIEWS
        final ImageView ivVisibilityOff_1         = findViewById(R.id.ivVisibilityOff_1);
        final ImageView ivVisibilityOff_2         = findViewById(R.id.ivVisibilityOff_2);

        //BUTTONS
        Button btnIngresarLogin             = findViewById(R.id.btnIngresarLogin);


        //COMIENZO ---- Setear las imagenes de los ojos para mostrar o no la contrasena

        ivVisibilityOff_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        etContrasenaLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        etContrasenaLogin.setSelection(etContrasenaLogin.getText().length());
                        ivVisibilityOff_1.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;

                    case MotionEvent.ACTION_UP:
                        etContrasenaLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        etContrasenaLogin.setSelection(etContrasenaLogin.getText().length());
                        ivVisibilityOff_1.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        return true;
                }
                return false;
            }
        });

        ivVisibilityOff_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        etRepitaContrasenaLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        etRepitaContrasenaLogin.setSelection(etRepitaContrasenaLogin.getText().length());
                        ivVisibilityOff_2.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;

                    case MotionEvent.ACTION_UP:
                        etRepitaContrasenaLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        etRepitaContrasenaLogin.setSelection(etRepitaContrasenaLogin.getText().length());
                        ivVisibilityOff_2.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        return true;
                }
                return false;
            }
        });

        //Fin ---- ---------------------------------------------------------------------

    }
}
