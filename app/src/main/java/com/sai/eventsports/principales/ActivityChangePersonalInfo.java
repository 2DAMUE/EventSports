package com.sai.eventsports.principales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.R;

public class ActivityChangePersonalInfo extends AppCompatActivity {


    ImageView icBack;
    Button btn_saveChange;
    ImageView backgroundChangePasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);

        icBack = findViewById(R.id.ic_back);
        btn_saveChange = findViewById(R.id.btn_save_change_settings);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangePersonalInfo.this, ActivitySettings.class);
                startActivity(intent);
            }

        });

        btn_saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangePersonalInfo.this, ActivitySettings.class);
                startActivity(intent);
            }

        });
    }
    }
