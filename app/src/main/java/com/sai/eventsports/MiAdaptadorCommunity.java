package com.sai.eventsports;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

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
                //.load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/net4-515ff.appspot.com/o/profilepics%2F" + u.getUserId()+ ".jpg?alt=media&token=dcb65d07-cace-45b4-8fb7-e38880be36ce"))
                .load(R.drawable.ic_profile)
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
