package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityChangeUser extends AppCompatActivity {


    ImageView icBack;
    Button btn_saveChange;
    ImageView backgroundChangePasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        icBack = findViewById(R.id.ic_back);
        btn_saveChange = findViewById(R.id.btn_save_change_settings);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangeUser.this, ActivitySettings.class);
                startActivity(intent);
            }

        });

        btn_saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangeUser.this, ActivitySettings.class);
                startActivity(intent);
            }

        });
    }
    }
