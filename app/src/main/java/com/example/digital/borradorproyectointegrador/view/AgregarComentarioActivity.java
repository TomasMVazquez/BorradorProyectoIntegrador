package com.example.digital.borradorproyectointegrador.view;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;

public class AgregarComentarioActivity extends AppCompatActivity {

    private float valoracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comentario);

        this.setFinishOnTouchOutside(false);

        Button btnAgregarComentario = findViewById(R.id.btnAgregarComentario);
        final EditText agregarComentarioEditText = findViewById(R.id.agregarComentarioEditText);
        TextInputLayout agregarComentTextLayout = findViewById(R.id.agregarComentTextLayout);
        RatingBar ratingAgregarComentario = findViewById(R.id.ratingAgregarComentario);

        ratingAgregarComentario.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                valoracion = rating;
            }
        });

        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valoracion > 0){

                    String texto = agregarComentarioEditText.getText().toString();

                    Intent data = TrailerActivity.respuestaAgregarComentario(texto,valoracion);
                    setResult(Activity.RESULT_OK, data);
                    finish();

                }else {
                    Toast.makeText(AgregarComentarioActivity.this, "Aún no has puesto cuantas estrellas de darías =)", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
