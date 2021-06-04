package com.sai.eventsports.recycler;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.Mensaje;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.List;

public class MiAdaptadorChat extends RecyclerView.Adapter<MiAdaptadorChat.ViewHolder> {
    private List<Mensaje> lista;
    private View vista;

    public MiAdaptadorChat(List<Mensaje> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public MiAdaptadorChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_chat2, parent, false);
        } else {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_chat, parent, false);
        }
        return new MiAdaptadorChat.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mensaje m = lista.get(position);
        holder.txt_mensaje.setText(m.getMensaje());
        holder.txt_username.setText(m.getUsername());
        holder.txt_hora.setText(m.getHora());
        Glide.with(vista)
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosPerfil%2F" + m.getUid_user() + ".jpg?alt=media&token=5d406923-e2fd-4e85-8c3e-21a0d2630e83"))
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_mensaje, txt_username, txt_hora;
        public ImageView img;

        public ViewHolder(View vista) {
            super(vista);
            this.txt_mensaje = vista.findViewById(R.id.text_mensaje_caht_View);
            this.txt_username = vista.findViewById(R.id.text_username_chat_View);
            this.txt_hora = vista.findViewById(R.id.text_hora_chat_View);
            this.img = vista.findViewById(R.id.img_chat_view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (lista.get(position).getUid_user().equals(ActivityLogIn.USERUID)) {
            return 1;
        } else {
            return -1;
        }
    }
}
