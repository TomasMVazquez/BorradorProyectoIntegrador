package com.example.digital.borradorproyectointegrador.view.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;

import java.util.List;

public class AdaptadorGeneros extends RecyclerView.Adapter {

    private List<Genero> generoList;

    public AdaptadorGeneros(List<Genero> generoList) {
        this.generoList = generoList;
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

    public class GeneroViewHolder extends RecyclerView.ViewHolder{

        private TextView generos;

        public GeneroViewHolder(View itemView) {
            super(itemView);
            generos = itemView.findViewById(R.id.genero);
        }

        public void cargar(Genero genero){
            generos.setText(genero.getName());
        }

    }

}
