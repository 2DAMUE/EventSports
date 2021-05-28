package com.sai.eventsports.principales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sai.eventsports.entidades.ImagenDeporte;
import com.sai.eventsports.recycler.MiAdaptadorMain;
import com.sai.eventsports.R;

import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private List<ImagenDeporte> elements;
    protected ImageView imagenDeporte;
    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.nav_home);
        bnv.setSelectedItemId(R.id.navigation_home);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_maps:
                        startActivity(new Intent(getApplicationContext(), ActivityMaps.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_new_event:
                        startActivity(new Intent(getApplicationContext(), ActivityNewEvent.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_community:
                        startActivity(new Intent(getApplicationContext(), ActivityCommunity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ImagenDeporte("Futbol",R.drawable.bota));
        elements.add(new ImagenDeporte("Baloncesto", R.drawable.baloncesto));
        elements.add(new ImagenDeporte("Tenis", R.drawable.tenis));
        elements.add(new ImagenDeporte("Voleibol", R.drawable.voleibol));
        elements.add(new ImagenDeporte("Natación", R.drawable.natacion));
        elements.add(new ImagenDeporte("Badminton", R.drawable.branded));
        elements.add(new ImagenDeporte("Atletismo", R.drawable.atle));
        elements.add(new ImagenDeporte("Balonmano", R.drawable.bola));
        elements.add(new ImagenDeporte("Waterpolo", R.drawable.waterpolo));
        elements.add(new ImagenDeporte("Padel", R.drawable.padel));
        elements.add(new ImagenDeporte("Ajedrez", R.drawable.ajedrez));

        MiAdaptadorMain listAdapter = new MiAdaptadorMain(elements);
        RecyclerView recyclerView = findViewById(R.id.mi_RecyclerMain);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
    }
}