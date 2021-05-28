package com.sai.eventsports.principales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sai.eventsports.CollectData;
import com.sai.eventsports.recycler.MiAdaptadorCommunity;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.User;

import java.util.List;

public class ActivityCommunity extends AppCompatActivity implements CollectData.Comunicación, SearchView.OnQueryTextListener {

    private BottomNavigationView bnv;
    private RecyclerView recyclerView;
    private CollectData.Comunicación comunicacion = this;
    private SearchView svSearch;
    private MiAdaptadorCommunity listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        svSearch = findViewById(R.id.svSearch);
        recyclerView = findViewById(R.id.recicler_community);
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
        initListener();
        CollectData.recogerUsers(comunicacion);
    }

    @Override
    public void mandarUsuarios(List<User> users) {
        listAdapter = new MiAdaptadorCommunity(users);
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
    }
    private void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filter(newText);
        return false;
    }
}