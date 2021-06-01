package com.sai.eventsports.recycler;

import android.content.Intent;
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
import com.google.android.material.card.MaterialCardView;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.principales.ActivityProfile;

import java.util.List;

public class MiAdaptadorSC extends RecyclerView.Adapter<MiAdaptadorSC.ViewHolder>{
    private List<Evento> evento;
    View view;

    public MiAdaptadorSC(List<Evento> evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "MiAdaptadorSC{" +
                "evento=" + evento +
                ", view=" + view +
                '}';
    }

    @NonNull
    @Override
    public MiAdaptadorSC.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_imagenes_main, parent, false);
        return new MiAdaptadorSC.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiAdaptadorSC.ViewHolder holder, int position) {
        Evento e = evento.get(position);
        Log.d("Bien",e.toString());
        Glide.with(view)
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosEvent%2F" + e.getUserid() + "-" + e.getNombre() + ".jpg?alt=media&token=2df21feb-3d9a-4e6a-8e53-770ce46c6740"))
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(holder.imgDeporte);
        holder.nombreEvento.setText(e.getNombre());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CAmbiar!!!!
                Intent intent = new Intent(view.getContext(), ActivityProfile.class);
                intent.putExtra("NombreEvento",e.getNombre());
                Log.d("bien",e.getNombre());
                v.getContext().startActivity(intent);
            }
        });
    }
    public void setItems(List<Evento> items){
        evento = items;
    }
    @Override
    public int getItemCount() {
        return evento.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDeporte;
        TextView nombreEvento;
        LinearLayout cardview;

        ViewHolder(View itemView){
            super(itemView);
            this.imgDeporte = itemView.findViewById(R.id.imageViewDeporte);
            this.nombreEvento = itemView.findViewById(R.id.textoDeporte);
            this.cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
