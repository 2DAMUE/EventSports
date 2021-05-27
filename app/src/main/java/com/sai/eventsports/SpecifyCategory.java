package com.sai.eventsports;

import android.os.Bundle;
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
    Button btnBottomSheet;
    private static final String TAG=SpecifyCategory.class.getSimpleName();
    LinearLayout linearLayout;
    Button action;
    BottomSheetBehavior bottomSheetBehavoir;
    ImageView fondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);
        fondo=findViewById(R.id.fondo);
        btnBottomSheet = findViewById(R.id.clases);
        action = findViewById(R.id.action);
        linearLayout= findViewById(R.id.bottomSheet);

        bottomSheetBehavoir=BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavoir.setDraggable(true);
        Log.d("estado",bottomSheetBehavoir.getState()+"");

        bottomSheetBehavoir.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("estado",bottomSheetBehavoir.getState()+"");
                switch(newState){
                        case BottomSheetBehavior.STATE_EXPANDED:
                            Toast.makeText(SpecifyCategory.this, "Abierto",Toast.LENGTH_LONG).show();
                            fondo.setImageAlpha(70);
                            bottomSheetBehavoir.setDraggable(true);
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                fondo.setImageAlpha(500);
                                bottomSheetBehavoir.setDraggable(false);
                                Toast.makeText(SpecifyCategory.this, "Cerrado",Toast.LENGTH_LONG).show();
                                break;
                }

                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SpecifyCategory.this, "Action is clicked",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavoir.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavoir.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Log.d("bien",bottomSheetBehavoir.getPeekHeight()+"");
                }else{
                    bottomSheetBehavoir.setState(4);
                    Log.d("estado",bottomSheetBehavoir.getState()+"");
                    Log.d("bien",bottomSheetBehavoir.getPeekHeight()+"");
                }

            }
        });

    }

}