package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.digital.borradorproyectointegrador.model.PeliculaSerie;
import com.example.digital.borradorproyectointegrador.R;

import java.util.List;

public class AdaptadorRecyclerPeliculaSerie extends RecyclerView.Adapter {

    //Atributo
    private List<PeliculaSerie> peliculaSeries;
    private AdapterInterface escuchador;

    //Constructor
    public AdaptadorRecyclerPeliculaSerie(List<PeliculaSerie> peliculaSeries, AdapterInterface escuchador) {
        this.peliculaSeries = peliculaSeries;
        this.escuchador = escuchador;
    }

    //Metodos de Herencia
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context =parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_view_show_room,parent,false);

        //necesito crear un viewHolder ( ahora el objeto no solo tiene la view sino los objetos de la view )
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //Busco el dato
        PeliculaSerie peliculaSerie = peliculaSeries.get(position);

        //Casteo
        ViewHolder viewHolder = (ViewHolder) holder;

        //Cargo los datos
        viewHolder.cargar(peliculaSerie);
    }

    @Override
    public int getItemCount() {
        return peliculaSeries.size();
    }

    public interface AdapterInterface {
        void irTrailer(PeliculaSerie peliculaSerie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Atributos
        private ImageView imagen;
        private RatingBar ratingBar;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            imagen=itemView.findViewById(R.id.cvShowRoom);
            ratingBar=itemView.findViewById(R.id.rbShowRoom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PeliculaSerie peliculaSerie = peliculaSeries.get(getAdapterPosition());
                    escuchador.irTrailer(peliculaSerie);
                }
            });
        }

        //Metodo para bindear Data
        public  void cargar (PeliculaSerie peliculaSerie){
            imagen.setImageResource(peliculaSerie.getImagen());
//            imagen.setImageDrawable(peliculaSerie.getImagen().getDrawable());
            ratingBar.setRating(peliculaSerie.getCantEstrellas());
            ratingBar.setNumStars(5);
        }

    }


}
