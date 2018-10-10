package com.example.digital.borradorproyectointegrador;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import org.json.JSONObject;
import java.util.Arrays;
import static android.provider.ContactsContract.Intents.Insert.EMAIL;
import static android.provider.Telephony.Carriers.AUTH_TYPE;

public class LogIn extends AppCompatActivity {
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";

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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

//Llamo a los views (edittexts, botones, etc)

        //EDIT TEXTS
        final EditText etUsuarioLogin                   = findViewById(R.id.etUsuarioLogin);
        final EditText etContrasenaLogin          = findViewById(R.id.etContrasenaLogin);
        //final EditText etRepitaContrasenaLogin    = findViewById(R.id.etRepitaContrasenaLogin);

        //TEXTINPUTLAYOUT
        final TextInputLayout tilUsuarioLogin             = findViewById(R.id.tilUsuarioLogin);
        final TextInputLayout tilContrasenaLogin          = findViewById(R.id.tilContrasenaLogin);
        //final TextInputLayout tilRepitaContrasenaLogin    = findViewById(R.id.tilRepitaContrasenaLogin);

        //BUSCAR LOS ERRORES
        final String errorUsuarioLogin = getResources().getString(R.string.tilUsuarioLoginError);
        final String errorConstrasenaLogin = getResources().getString(R.string.tilContrasenaLoginError);
        //final String errorRepitaContrasenaLogin = getResources().getString(R.string.tilRepitaContrasenaLoginError);

        //IMAGE VIEWS
        final ImageView ivVisibilityOff_1         = findViewById(R.id.ivVisibilityOff_1);
        //final ImageView ivVisibilityOff_2         = findViewById(R.id.ivVisibilityOff_2);

        //BUTTONS
//        Button btnIngresarLogin             = findViewById(R.id.btnIngresarLogin);

        // ACA EMPIEZAN LOS LOGINS
        callbackManager = CallbackManager.Factory.create();
        //Facebook LoginButton
        LoginButton loginButtonFacebook = findViewById(R.id.login_button);

        // Register a callback to respond to the user
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




        // ACA TERMINA LOS LOGINS



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

//        ivVisibilityOff_2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        etRepitaContrasenaLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                        etRepitaContrasenaLogin.setSelection(etRepitaContrasenaLogin.getText().length());
//                        ivVisibilityOff_2.setImageResource(R.drawable.ic_visibility_black_24dp);
//                        return true;
//
//                    case MotionEvent.ACTION_UP:
//                        etRepitaContrasenaLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        etRepitaContrasenaLogin.setSelection(etRepitaContrasenaLogin.getText().length());
//                        ivVisibilityOff_2.setImageResource(R.drawable.ic_visibility_off_black_24dp);
//                        return true;
//                }
//                return false;
//            }
//        });

        //Fin ---- ---------------------------------------------------------------------

        //COMIENZO DE SETEO PARA LOS EDIT TEXT------------------------------------------------------------

        etUsuarioLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etUsuarioLogin.getText().toString().contains("@") && etUsuarioLogin.getText().toString().contains(".com")){
                    tilUsuarioLogin.setError(null);
                }else {
                    tilUsuarioLogin.setError(errorUsuarioLogin);
                }
            }
        });

        etContrasenaLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etContrasenaLogin.getText().length() < 10) {
                    tilContrasenaLogin.setError(errorConstrasenaLogin);
                } else {
                    tilContrasenaLogin.setError(null);
                }
            }
        });

//        etRepitaContrasenaLogin.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (etRepitaContrasenaLogin.getText().equals(etContrasenaLogin.getText())){
//                    tilRepitaContrasenaLogin.setError(null);
//                }else {
//                    tilRepitaContrasenaLogin.setError(errorRepitaContrasenaLogin);
//                }
//            }
//        });
        //Fin-------------------------------------------------------------------------------------------

    }
}
