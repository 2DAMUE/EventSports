package com.sai.eventsports.recycler;

import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiAdaptadorCommunity extends RecyclerView.Adapter<MiAdaptadorCommunity.ViewHolder>{
    private List<User> mData;
    private List<User> originalItems;
    View view;

    public MiAdaptadorCommunity(List<User> itemList){
        this.mData = itemList;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(mData);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public MiAdaptadorCommunity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comunity_items, parent, false);
        return new MiAdaptadorCommunity.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MiAdaptadorCommunity.ViewHolder holder, final int position){
        User u = mData.get(position);
        Glide.with(view)
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosPerfil%2F" + u.getUserID() + ".jpg?alt=media&token=7437589e-16cb-4f09-8c58-3b4f1e6187be"))
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(holder.imgProfile);
        holder.userName.setText(u.getUser());
        holder.email.setText(u.getEmail());
        /*holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public void setItems(List<User> items){
        mData = items;
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProfile;
        TextView userName,email;
        RelativeLayout cardview;

        ViewHolder(View itemView){
            super(itemView);
            this.imgProfile = itemView.findViewById(R.id.img_profile);
            this.userName = itemView.findViewById(R.id.username);
            this.email = itemView.findViewById(R.id.fullname);
            this.cardview = itemView.findViewById(R.id.rl_community);
        }
    }
    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            mData.clear();
            mData.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mData.clear();
                List<User> collect = originalItems.stream()
                        .filter(i -> i.getUser().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                mData.addAll(collect);
            }
            else {
                mData.clear();
                for (User i : originalItems) {
                    if (i.getUser().toLowerCase().contains(strSearch)) {
                        mData.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
