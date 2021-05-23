package com.sai.eventsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityCommunity extends AppCompatActivity {

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        bnv = findViewById(R.id.nav_community);
        bnv.setSelectedItemId(R.id.navigation_community);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}