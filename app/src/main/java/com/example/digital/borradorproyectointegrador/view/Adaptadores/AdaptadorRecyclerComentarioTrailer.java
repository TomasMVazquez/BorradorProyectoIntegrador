package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.view.PerfilUsuarioActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdaptadorRecyclerComentarioTrailer extends RecyclerView.Adapter {

    //Atributos
    private List<Comentario> comentarioTrailerList;
    private Context context;
    private ComentarioInterface comentarioInterface;
    private FirebaseUser user;

    public AdaptadorRecyclerComentarioTrailer(FirebaseUser user,Context context,ComentarioInterface comentarioInterface,List<Comentario> comentarioTrailerList) {
        this.user = user;
        this.comentarioTrailerList = comentarioTrailerList;
        this.comentarioInterface = comentarioInterface;
    }

    public void setComentarioTrailerList(List<Comentario> comentarioTrailerList) {
        this.comentarioTrailerList = comentarioTrailerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_view_comentario_trailer,parent,false);

        ViewHolderComentTrailer viewHolderComentTrailer = new ViewHolderComentTrailer(view);

        return viewHolderComentTrailer;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Comentario comentarioTrailer =comentarioTrailerList.get(position);
        ViewHolderComentTrailer viewHolderComentTrailer = (ViewHolderComentTrailer) holder;
        viewHolderComentTrailer.cargar(comentarioTrailer);

    }

    @Override
    public int getItemCount() {
        return comentarioTrailerList.size();
    }

    public interface ComentarioInterface{
        public void irPerfil(Comentario comentario);
        public void botonesComentario(Integer boton, FirebaseUser user, Comentario comentario);
    }

    public class ViewHolderComentTrailer extends RecyclerView.ViewHolder{

        //Atributos
        private ImageView ivImagenUsuarioComentarioTrailer;
        private TextView tvUsuarioComentarioTrailer;
        private RatingBar rbUsuarioAPeliculaComentarioTrailer;
        private TextView tvComentarioComentarioTrailer;
        private ImageView ivMeGustaComentario;
        private ImageView ivNoMeGustaComentario;
        private ImageView ivCompartirComentario;
        private TextView tvCantMeGusta;

        public ViewHolderComentTrailer(View itemView) {
            super(itemView);
            ivImagenUsuarioComentarioTrailer = itemView.findViewById(R.id.ivImagenUsuarioComentarioTrailer);
            tvUsuarioComentarioTrailer = itemView.findViewById(R.id.tvUsuarioComentarioTrailer);
            rbUsuarioAPeliculaComentarioTrailer = itemView.findViewById(R.id.rbUsuarioAPeliculaComentarioTrailer);
            tvComentarioComentarioTrailer = itemView.findViewById(R.id.tvComentarioComentarioTrailer);
            tvCantMeGusta = itemView.findViewById(R.id.tvCantMeGustaTrailer);
            ivMeGustaComentario = itemView.findViewById(R.id.ivMeGustaComentarioT);
            ivNoMeGustaComentario = itemView.findViewById(R.id.ivNoMeGustaComentarioT);
            ivCompartirComentario = itemView.findViewById(R.id.ivCompartirComentarioT);

            tvUsuarioComentarioTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comentario comentario = comentarioTrailerList.get(getAdapterPosition());
                    comentarioInterface.irPerfil(comentario);
                }
            });

            if (user!=null) {
                ivMeGustaComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioTrailerList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(0,user,comentario);
                    }
                });
                ivNoMeGustaComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioTrailerList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(1,user,comentario);
                    }
                });
                ivCompartirComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioTrailerList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(2,user,comentario);
                    }
                });
            }else {
                Toast.makeText(itemView.getContext(), "Debes estar logeado", Toast.LENGTH_SHORT).show();
            }

        }

        //metodo para cargar data
        @SuppressLint("CheckResult")
        public void cargar(Comentario comentarioTrailer){

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.play_movie_icon_sketch);
            requestOptions.error(R.drawable.play_movie_icon_sketch);

                Glide.with(context)
                        .load(comentarioTrailer.getIvImagenUsuarioComentario())
                        .apply(requestOptions)
                        .into(ivImagenUsuarioComentarioTrailer);

            tvUsuarioComentarioTrailer.setText(comentarioTrailer.getTvUsuarioComentario());
            tvComentarioComentarioTrailer.setText(comentarioTrailer.getTvComentarioComentario());
            rbUsuarioAPeliculaComentarioTrailer.setRating(comentarioTrailer.getCantidadEstrellasAPelicula());
            rbUsuarioAPeliculaComentarioTrailer.setNumStars(5);
            if (comentarioTrailer.getTvCantMeGusta()!=null) {
                tvCantMeGusta.setText(String.valueOf(comentarioTrailer.getTvCantMeGusta()));
            }else {
                tvCantMeGusta.setText("0");
            }
        }
    }

}
