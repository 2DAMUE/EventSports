package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.w3c.dom.Text;

public class SpecifyCategory extends AppCompatActivity {
    private BottomSheetBehavior mBottom;
    private TextView mTextViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottom = BottomSheetBehavior.from(bottomSheet);
    }
}