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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdaptadorRecyclerComentariosCompletos extends RecyclerView.Adapter {

    //atributos
    private List<Comentario> comentarioList;
    private Context context;
    private ComentarioInterface comentarioInterface;
    private FirebaseUser user;

    //constructor
    public AdaptadorRecyclerComentariosCompletos(FirebaseUser user,Context context, ComentarioInterface comentarioInterface, List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
        this.comentarioInterface = comentarioInterface;
        this.user = user;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

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

    public interface ComentarioInterface{
        public void irPerfil(Comentario comentario);
        public void botonesComentario(Integer boton,FirebaseUser user, Comentario comentario);
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder{

        //Atributos
        private ImageView ivImagenPeliculaComentario;
        private ImageView ivImagenUsuarioComentario;
        private TextView tvUsuarioComentario;
        private RatingBar rbUsuarioAPeliculaComentario;
        private TextView tvComentarioComentario;
        private ImageView ivMeGustaComentario;
        private ImageView ivNoMeGustaComentario;
        private ImageView ivCompartirComentario;
        private TextView tvCantMeGusta;

        //constructor
        public ViewHolderComentario(View itemView) {
            super(itemView);
            ivImagenPeliculaComentario = itemView.findViewById(R.id.ivImagenPeliculaComentario);
            ivImagenUsuarioComentario = itemView.findViewById(R.id.ivImagenUsuarioComentario);
            tvUsuarioComentario = itemView.findViewById(R.id.tvUsuarioComentario);
            rbUsuarioAPeliculaComentario = itemView.findViewById(R.id.rbUsuarioAPeliculaComentario);
            tvComentarioComentario = itemView.findViewById(R.id.tvComentarioComentario);
            ivMeGustaComentario = itemView.findViewById(R.id.ivMeGustaComentario);
            ivNoMeGustaComentario = itemView.findViewById(R.id.ivNoMeGustaComentario);
            ivCompartirComentario = itemView.findViewById(R.id.ivCompartirComentario);
            tvCantMeGusta = itemView.findViewById(R.id.tvCantMeGusta);

            tvUsuarioComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comentario comentario = comentarioList.get(getAdapterPosition());
                    comentarioInterface.irPerfil(comentario);
                }
            });

            if (user!=null) {
                ivMeGustaComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(0,user,comentario);
                    }
                });
                ivNoMeGustaComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(1,user,comentario);
                    }
                });
                ivCompartirComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comentario comentario = comentarioList.get(getAdapterPosition());
                        comentarioInterface.botonesComentario(2,user,comentario);
                    }
                });
            }else {
                Toast.makeText(itemView.getContext(), "Debes estar logeado", Toast.LENGTH_SHORT).show();
            }

        }

        //metodo para cargar data
        public void cargar(Comentario comentario){

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.play_movie_icon_sketch);
            requestOptions.error(R.drawable.play_movie_icon_sketch);
            Glide.with(context)
                    .load(context.getResources().getString(R.string.poster_path) + comentario.getIvImagenPeliculaComentario())
                    .apply(requestOptions)
                    .into(ivImagenPeliculaComentario);

            Glide.with(context)
                    .load(comentario.getIvImagenUsuarioComentario())
                    .apply(requestOptions)
                    .into(ivImagenUsuarioComentario);

            tvUsuarioComentario.setText(comentario.getTvUsuarioComentario());
            tvComentarioComentario.setText(comentario.getTvComentarioComentario());
            rbUsuarioAPeliculaComentario.setRating(comentario.getCantidadEstrellasAPelicula());
            rbUsuarioAPeliculaComentario.setNumStars(5);
            if (comentario.getTvCantMeGusta()!=null) {
                tvCantMeGusta.setText(String.valueOf(comentario.getTvCantMeGusta()));
            }else {
                tvCantMeGusta.setText("0");
            }
        }

    }

}
