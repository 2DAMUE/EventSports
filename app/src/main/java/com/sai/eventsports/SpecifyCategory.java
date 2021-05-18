package com.sai.eventsports;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);

        btnBottomSheet = findViewById(R.id.clases);
        action = findViewById(R.id.action);
        linearLayout= findViewById(R.id.bottomSheet);

        bottomSheetBehavoir=BottomSheetBehavior.from(linearLayout);

        bottomSheetBehavoir.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch(newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                        case BottomSheetBehavior.STATE_EXPANDED:
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                btnBottomSheet.setText("Closed");
                                break;
                                case BottomSheetBehavior.STATE_DRAGGING:
                                    break;
                                    case BottomSheetBehavior.STATE_SETTLING:
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
                }else{
                    bottomSheetBehavoir.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });
    }

}