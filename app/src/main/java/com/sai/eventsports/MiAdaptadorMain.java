package com.sai.eventsports;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MiAdaptadorMain extends RecyclerView.Adapter<MiAdaptadorMain.ViewHolder>{
    private List<ImagenDeporte> mData;
    private LayoutInflater mInflater;
    private Context context;

    public MiAdaptadorMain(List<ImagenDeporte> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public MiAdaptadorMain.ViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.vista_imagenes_main, null);
        return new MiAdaptadorMain.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MiAdaptadorMain.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ImagenDeporte> items){
        mData = items;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDeporte;
        TextView nombreDeporte;

        ViewHolder(View itemView){
            super(itemView);
            //this.imgDeporte = itemView.findViewById(R.id.imageViewDeporte);
            this.nombreDeporte = itemView.findViewById(R.id.textoDeporte);
        }

        void bindData(final ImagenDeporte item){
        }
    }
}
/*
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MiAdaptadorMain extends RecyclerView.Adapter<MiAdaptadorMain.MiContenedorDeVistas> {

    private ArrayList<ImagenDeporte> lista_imagenes = new ArrayList<>();

    public MiAdaptadorMain(ArrayList<ImagenDeporte> lista_imagenes) {
        this.lista_imagenes = lista_imagenes;
    }

    @NonNull
    @Override
    public MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_imagenes_main, parent, false);
        ImageView iv_deporte = vista.findViewById(R.id.imageViewDeporte);
        TextView tv_deporte = vista.findViewById(R.id.textoDeporte);

        MiContenedorDeVistas contenerdor = new MiContenedorDeVistas(vista);
        Log.d("contenedor", "creando contenedor de vistas");
        return contenerdor;
    }

    @Override
    public void onBindViewHolder(@NonNull MiContenedorDeVistas holder, int position) {
        ImagenDeporte c = lista_imagenes.get(position);
        holder.iv_deporte.setText((c.getUrl()));
        holder.tv_deporte.setText(c.getNombre());
        Log.d("contenedor", "vinculando position" + position);
    }

    @Override
    public int getItemCount() {
        return lista_imagenes.size();
    }

    public static class MiContenedorDeVistas extends RecyclerView.ViewHolder {
        public ImageView iv_deporte;
        public TextView tv_deporte;


        public MiContenedorDeVistas(View vista) {
            super(vista);
            this.iv_deporte = vista.findViewById(R.id.imageViewDeporte);
            this.tv_deporte = vista.findViewById(R.id.textoDeporte);
        }

    }


}*/