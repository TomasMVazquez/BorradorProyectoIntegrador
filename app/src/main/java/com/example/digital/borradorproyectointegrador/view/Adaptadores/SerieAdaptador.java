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
import com.example.digital.borradorproyectointegrador.model.serie.Serie;

import java.util.List;

public class SerieAdaptador extends RecyclerView.Adapter {

    private List<Serie> serieList;
    private Context contextAdapter;
    private AdapterSerieInterface escuchador;

    public SerieAdaptador(Context contextAdapter, List<Serie> serieList,  AdapterSerieInterface escuchador) {
        this.serieList = serieList;
        this.contextAdapter = contextAdapter;
        this.escuchador = escuchador;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_view_show_room,parent,false);

        SerieViewHolder serieViewHolder = new SerieViewHolder(view);

        return serieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Serie serie = serieList.get(position);
        SerieViewHolder serieViewHolder = (SerieViewHolder) holder;
        serieViewHolder.cargar(serie,contextAdapter);
    }

    @Override
    public int getItemCount() {
        return serieList.size();
    }

    public interface AdapterSerieInterface {
        void irTrailer(Serie Serie);
    }

    public class SerieViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private RatingBar ratingBar;

        public SerieViewHolder(View itemView) {
            super(itemView);
            this.imagen = itemView.findViewById(R.id.cvShowRoom);
            this.ratingBar = itemView.findViewById(R.id.rbShowRoom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Serie serie = serieList.get(getAdapterPosition());
                    escuchador.irTrailer(serie);
                }
            });

        }

        public void cargar(Serie serie,Context context){
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context)
                    .load(context.getResources().getString(R.string.poster_path) + serie.getPoster_path())
                    .apply(requestOptions)
                    .into(imagen);

            long cantEstr = Math.round((serie.getVote_average() * 5) / 10);

            ratingBar.setRating(cantEstr);
            ratingBar.setNumStars(5);
        }

    }
}
