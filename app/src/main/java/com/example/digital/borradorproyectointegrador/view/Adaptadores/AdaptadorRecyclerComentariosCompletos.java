package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.Comentario;

import java.util.List;

public class AdaptadorRecyclerComentariosCompletos extends RecyclerView.Adapter {

    //atributos
    private List<Comentario> comentarioList;


    //constructor
    public AdaptadorRecyclerComentariosCompletos(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_view_comentario_comunidad,parent,false);

        ViewHolderComentario viewHolderComentario = new ViewHolderComentario(view);

        return viewHolderComentario;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Comentario comentario = comentarioList.get(position);
        ViewHolderComentario viewHolderComentario = (ViewHolderComentario) holder;
        viewHolderComentario.cargar(comentario);

    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder{

        //Atributos
        private ImageView ivImagenPeliculaComentario;
        private ImageView ivImagenUsuarioComentario;
        private TextView tvUsuarioComentario;
        private RatingBar rbUsuarioAPeliculaComentario;
        private TextView tvComentarioComentario;

        //constructor
        public ViewHolderComentario(View itemView) {
            super(itemView);
            ivImagenPeliculaComentario = itemView.findViewById(R.id.ivImagenPeliculaComentario);
            ivImagenUsuarioComentario = itemView.findViewById(R.id.ivImagenUsuarioComentario);
            tvUsuarioComentario = itemView.findViewById(R.id.tvUsuarioComentario);
            rbUsuarioAPeliculaComentario = itemView.findViewById(R.id.rbUsuarioAPeliculaComentario);
            tvComentarioComentario = itemView.findViewById(R.id.tvComentarioComentario);
        }

        //metodo para cargar data
        public void cargar(Comentario comentario){
            ivImagenPeliculaComentario.setImageResource(comentario.getIvImagenPeliculaComentario());
            ivImagenUsuarioComentario.setImageResource(comentario.getIvImagenUsuarioComentario());
            tvUsuarioComentario.setText(comentario.getTvUsuarioComentario());
            tvComentarioComentario.setText(comentario.getTvComentarioComentario());
            rbUsuarioAPeliculaComentario.setRating(comentario.getCantidadEstrellasAPelicula());
            rbUsuarioAPeliculaComentario.setNumStars(5);
        }

    }

}
