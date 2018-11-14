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
    private Boolean hayLugar = true;
    private contadorFiltros contadorFiltros;
    private List<Integer> generosSeleccionados = new ArrayList<>();

    public AdaptadorFiltros(contadorFiltros contadorFiltros, List<Genero> generoList) {
        this.generoList = generoList;
        this.contadorFiltros = contadorFiltros;
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

    public interface contadorFiltros{
        public void sumarContadorFiltros();
        public void restarContadorFiltros();
        public List<Integer> listaGeneros();
        //public void irAlFragment();
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

                    if (isSelected){
                        cardViewCategoria.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBlanco));
                        contadorFiltros.restarContadorFiltros();
                        tieneLugar();
                       genero.setSelected(false);
                       generosSeleccionados.remove(genero.getId());
                    }else {
                        if (revisarLugar()){
                            cardViewCategoria.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), colorAccent));
                            genero.setSelected(true);
                            contadorFiltros.sumarContadorFiltros();
                            generosSeleccionados.add(genero.getId());
                        }
                    }

                }
            });

        }

        public void cargar(Genero genero){
            generos.setText(genero.getName());
        }

    }

    public boolean revisarLugar(){
            return hayLugar;
    }

    public void finLugar(){
        hayLugar = false;
    }

    public void tieneLugar(){
        hayLugar = true;
    }

    public List<Integer> darListaSeleccionados(){
        return generosSeleccionados;
    }

}
