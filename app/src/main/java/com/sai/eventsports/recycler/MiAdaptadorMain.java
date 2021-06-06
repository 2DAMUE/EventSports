package com.sai.eventsports.recycler;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sai.eventsports.entidades.ImagenDeporte;
import com.sai.eventsports.R;
import com.sai.eventsports.SpecifyCategory;

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
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),SpecifyCategory.class);
                intent.putExtra("NombreEvento",i.getNombre());
                Bitmap bitmap = ((BitmapDrawable)holder.imgDeporte.getDrawable()).getBitmap();
                intent.putExtra("Fondo",bitmap);
                intent.putExtra("DeporteDia","no");
                v.getContext().startActivity(intent);
            }
        });
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