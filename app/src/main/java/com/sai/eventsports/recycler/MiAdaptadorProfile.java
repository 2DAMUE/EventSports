package com.sai.eventsports.recycler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.ActivityEvents;
import com.sai.eventsports.R;
import com.sai.eventsports.SpecifyCategory;
import com.sai.eventsports.entidades.Apuntate;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.ImagenDeporte;

import java.util.List;

public class MiAdaptadorProfile extends RecyclerView.Adapter<MiAdaptadorProfile.ViewHolder>{
    private List<Evento> mData;
    View view;

    public MiAdaptadorProfile(List<Evento> itemList){
        this.mData = itemList;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public MiAdaptadorProfile.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_imagenes_main, parent, false);
        return new MiAdaptadorProfile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MiAdaptadorProfile.ViewHolder holder, final int position){
        Evento a = mData.get(position);
        Glide.with(view)
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosEvent%2F" + a.getUserid() + "-" + a.getNombre() + ".jpg?alt=media&token=2df21feb-3d9a-4e6a-8e53-770ce46c6740"))
                //.centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                //.circleCrop()
                .into(holder.imgDeporte);
        holder.nombreDeporte.setText(a.getNombre());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ActivityEvents.class);
                intent.putExtra("Deporte","deporte");
                intent.putExtra("Lugar","pf");
                intent.putExtra("NombreEvento",a.getNombre());
                intent.putExtra("UserIdEvento",a.getUserid());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setItems(List<Evento> items){
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
