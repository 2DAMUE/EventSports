package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<ImagenDeporte> elements;
    protected ImageView imagenDeporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ImagenDeporte("futbol"));
        elements.add(new ImagenDeporte("baloncesto"));
        elements.add(new ImagenDeporte("tenis"));

        MiAdaptadorMain listAdapter = new MiAdaptadorMain(elements,this);
        RecyclerView recyclerView = findViewById(R.id.mi_RecyclerMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}
