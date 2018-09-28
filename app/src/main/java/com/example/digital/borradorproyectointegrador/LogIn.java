package com.example.digital.borradorproyectointegrador;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;
import static android.provider.Telephony.Carriers.AUTH_TYPE;

public class LogIn extends AppCompatActivity {

    //Atributos
    private CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Llamo a los views (edittexts, botones, etc)

        //EDIT TEXTS
        EditText etUsuarioLogin                   = findViewById(R.id.etUsuarioLogin);
        final EditText etContrasenaLogin          = findViewById(R.id.etContrasenaLogin);
        final EditText etRepitaContrasenaLogin    = findViewById(R.id.etRepitaContrasenaLogin);

        //IMAGE VIEWS
        final ImageView ivVisibilityOff_1         = findViewById(R.id.ivVisibilityOff_1);
        final ImageView ivVisibilityOff_2         = findViewById(R.id.ivVisibilityOff_2);

        //BUTTONS
        Button btnIngresarLogin             = findViewById(R.id.btnIngresarLogin);

        //Login Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButtonFacebook = findViewById(R.id.login_button);

        // Register a callback to respond to the user
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setResult(RESULT_OK);
                Toast.makeText(LogIn.this, "Estas logeado con Facebook", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                Toast.makeText(LogIn.this, "Log off de Facebook", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LogIn.this, "ERROR", Toast.LENGTH_SHORT).show();
                Toast.makeText(LogIn.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                // Handle exception
            }
        });








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
