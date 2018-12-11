package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

public class PeliculaAdaptador extends RecyclerView.Adapter {

    private List<Peliculas> peliculas;
    private Context contextAdapter;
    private AdapterPeliInterface escuchador;

    public PeliculaAdaptador(Context context, List<Peliculas> peliculas, AdapterPeliInterface escuchador) {
        this.peliculas = peliculas;
        this.contextAdapter = context;
        this.escuchador = escuchador;
    }

    public void setPeliculas(List<Peliculas> peliculas) {
        this.peliculas = peliculas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_view_show_room,viewGroup,false);

        PeliculaViewHolder peliculaViewHolder = new PeliculaViewHolder(view);

        return peliculaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Peliculas pelicula = peliculas.get(i);
        PeliculaViewHolder peliculaViewHolder = (PeliculaViewHolder) viewHolder;
        peliculaViewHolder.cargar(pelicula,contextAdapter);

    }

    public interface AdapterPeliInterface {
        void irTrailer(Peliculas peliculas);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public class PeliculaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private RatingBar ratingBar;
        private Integer vote_average;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.cvShowRoom);
            ratingBar=itemView.findViewById(R.id.rbShowRoom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Peliculas peli = peliculas.get(getAdapterPosition());
                    escuchador.irTrailer(peli);
                }
            });

        }

        public void cargar(Peliculas peliculas, Context context){

            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context)
                    .load(context.getResources().getString(R.string.poster_path) + peliculas.getPoster_path())
                    .apply(requestOptions)
                    .into(imagen);

            long cantEstr = Math.round((peliculas.getVote_average() * 5) / 10);
            
            ratingBar.setRating(cantEstr);
            ratingBar.setNumStars(5);

        }

    }

}
