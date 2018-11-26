package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.view.Fragments.FiltroFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.digital.borradorproyectointegrador.R.color.colorAccent;

public class AdaptadorFiltros extends RecyclerView.Adapter {

    private List<Genero> generoList;
    private FiltrosInterface filtrosInterface;


    public AdaptadorFiltros(List<Genero> generoList, FiltrosInterface filtrosInterface) {
        this.generoList = generoList;
        this.filtrosInterface = filtrosInterface;
    }

    public void setGeneroList(List<Genero> generoList) {
        this.generoList = generoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_view_filtros,parent,false);
        GeneroViewHolder generoViewHolder = new GeneroViewHolder(view);

        return generoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Genero genero = generoList.get(position);
        GeneroViewHolder generoViewHolder = (GeneroViewHolder) holder;
        generoViewHolder.cargar(genero);
    }

    @Override
    public int getItemCount() {
        return generoList.size();
    }

    public interface FiltrosInterface{
        void mostrarFiltros(Integer integer, String nombre);
    }


    public class GeneroViewHolder extends RecyclerView.ViewHolder{

        private TextView generos;
        private CardView cardViewCategoria;

        public GeneroViewHolder(final View itemView) {
            super(itemView);
            generos = itemView.findViewById(R.id.genero);
            cardViewCategoria = itemView.findViewById(R.id.cardViewCategoria);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Genero genero = generoList.get(getAdapterPosition());
                    boolean isSelected = genero.getSelected();
                    cardViewCategoria.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), colorAccent));
                    filtrosInterface.mostrarFiltros(genero.getId(),genero.getName());
                }
            });

        }

        public void cargar(Genero genero){
            generos.setText(genero.getName());
        }

    }

}
