package com.sai.eventsports;


import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MiAdaptadorMain extends RecyclerView.Adapter<MiAdaptadorMain.ViewHolder>{
    private List<ImagenDeporte> mData;
    View view;

    public MiAdaptadorMain(List<ImagenDeporte> itemList){
        this.mData = itemList;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public MiAdaptadorMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_imagenes_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MiAdaptadorMain.ViewHolder holder, final int position){
        ImagenDeporte i = mData.get(position);
        Glide.with(view)
                .load(i.getUrl())
                //.placeholder(R.drawable.forma_fotos)
                .centerCrop()
                //.transition(DrawableTransitionOptions.withCrossFade(300))
                //.circleCrop()
                .into(holder.imgDeporte);
        holder.nombreDeporte.setText(i.getNombre());
    }

    public void setItems(List<ImagenDeporte> items){
        mData = items;
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDeporte;
        TextView nombreDeporte;
        LinearLayout cardview;

        ViewHolder(View itemView){
            super(itemView);
            this.imgDeporte = itemView.findViewById(R.id.imageViewDeporte);
            this.nombreDeporte = itemView.findViewById(R.id.textoDeporte);
            this.cardview = itemView.findViewById(R.id.cardview);
        }
    }
}