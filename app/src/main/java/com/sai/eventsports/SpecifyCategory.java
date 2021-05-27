package com.sai.eventsports;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SpecifyCategory extends AppCompatActivity{
    Button clases;
    Button eventos;
    boolean clas = false;
    boolean event = false;
    private static final String TAG=SpecifyCategory.class.getSimpleName();
    LinearLayout linearLayout;
    BottomSheetBehavior bottomSheetBehavoir;
    ImageView fondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);
        fondo=findViewById(R.id.fondo);
        clases = findViewById(R.id.clases);
        eventos = findViewById(R.id.eventos);
        linearLayout= findViewById(R.id.bottomSheet);

        SpannableString clasess = new SpannableString("Clases");
        clasess.setSpan(new UnderlineSpan(), 0, clasess.length(), 0);
        SpannableString eventoss = new SpannableString("Eventos");
        eventoss.setSpan(new UnderlineSpan(), 0, eventoss.length(), 0);

        bottomSheetBehavoir=BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavoir.setDraggable(true);
        Log.d("estado",bottomSheetBehavoir.getState()+"");

        bottomSheetBehavoir.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("estado",bottomSheetBehavoir.getState()+"");
                switch(newState){
                        case BottomSheetBehavior.STATE_EXPANDED:
                            fondo.setImageAlpha(70);
                            bottomSheetBehavoir.setDraggable(true);
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                clas=false;
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

}