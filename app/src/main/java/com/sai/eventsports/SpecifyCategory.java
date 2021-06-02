package com.sai.eventsports;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.recycler.MiAdaptadorSC;

import java.util.ArrayList;
import java.util.List;

public class SpecifyCategory extends AppCompatActivity implements CollectData.Comunicacion {
    Button clases;
    Button eventos;
    boolean clas = false;
    private CollectData.Comunicacion comunicacion = this;
    private RecyclerView recyclerView;
    private MiAdaptadorSC listAdapter;
    Toolbar nombre;
    boolean event = false;
    private static final String TAG=SpecifyCategory.class.getSimpleName();
    LinearLayout linearLayout;
    BottomSheetBehavior bottomSheetBehavoir;
    ImageView fondo;
    String titulo="";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);
        Intent intent = getIntent();
        titulo = intent.getStringExtra("NombreEvento");
        Log.d("Bien",titulo+"");
        nombre=findViewById(R.id.toolbar_specify_category);
        nombre.setTitle(titulo);
        recyclerView=findViewById(R.id.recyclerEvents);
        fondo=findViewById(R.id.fondo);
        clases = findViewById(R.id.clases);
        eventos = findViewById(R.id.eventos);
        linearLayout= findViewById(R.id.bottomSheet);

        SpannableString clasess = new SpannableString("Clases");
        clasess.setSpan(new UnderlineSpan(), 0, clasess.length(), 0);
        SpannableString eventoss = new SpannableString("Eventos");
        eventoss.setSpan(new UnderlineSpan(), 0, eventoss.length(), 0);
        Drawable drawable = fondo.getDrawable();

        event=false;

        bottomSheetBehavoir=BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavoir.setDraggable(true);
        Log.d("estado",bottomSheetBehavoir.getState()+"");
        recyclerView.setVisibility(View.INVISIBLE);

        bottomSheetBehavoir.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("estado",bottomSheetBehavoir.getState()+"");
                switch(newState){
                        case BottomSheetBehavior.STATE_EXPANDED:
                            drawable.setColorFilter(Color.BLACK,PorterDuff.Mode.LIGHTEN);
                            fondo.setImageDrawable(drawable);
                            recyclerView.setVisibility(View.VISIBLE);
                            bottomSheetBehavoir.setDraggable(true);
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                drawable.clearColorFilter();
                                clas=false;
                                recyclerView.setVisibility(View.INVISIBLE);
                                clases.setText("Clases");
                                clases.setTypeface(null, 0);
                                clases.setAlpha(1f);
                                event=false;
                                eventos.setText("Eventos");
                                eventos.setAlpha(1f);
                                eventos.setTypeface(null, 0);
                                fondo.setImageAlpha(500);
                                bottomSheetBehavoir.setDraggable(false);
                                break;
                }
                CollectData.recogerEventos(comunicacion);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        clases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clas){
                    clas=true;
                    clases.setText(clasess);
                    eventos.setAlpha(0.1f);
                    clases.setTypeface(null, 3);
                }else{
                    clas=false;
                    clases.setText("Clases");
                    eventos.setAlpha(1f);
                    clases.setTypeface(null, 0);
                }
                if(bottomSheetBehavoir.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavoir.setState(4);
                }

            }
        });
        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!event){
                    event=true;
                    eventos.setText(eventoss);
                    clases.setAlpha(0.1f);
                    eventos.setTypeface(null, 3);
                }else{
                    event=false;
                    eventos.setText("Eventos");
                    clases.setAlpha(1f);
                    eventos.setTypeface(null, 0);
                }
                if(bottomSheetBehavoir.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavoir.setState(4);
                }

            }
        });

    }

    @Override
    public void mandarUsuarios(List<User> users){}

    @Override
    public void mandarEventos(List<Evento> eventos) {
        listAdapter=new MiAdaptadorSC(null);
        List<Evento> ents = new ArrayList<>();
        List<Evento> clases = new ArrayList<>();
        for (Evento e:eventos) {
            if(e.getDeporte().equals(titulo)) {
                Log.d("Evento", e.getTipo());
                if (e.getTipo().equals("Evento")) {
                    ents.add(e);
                } else {
                    clases.add(e);
                }
            }
        }
        //If depende del boton clicado
            //eventos
        if(event) {
            listAdapter = new MiAdaptadorSC(ents);
        }else{
            listAdapter = new MiAdaptadorSC(clases);
        }
        Log.d("ada",listAdapter.toString());
        Log.d("ada",event+"");

        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listAdapter);
    }
}