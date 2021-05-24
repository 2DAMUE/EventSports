package com.sai.eventsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityNewEvent extends AppCompatActivity {

    private Spinner miSpn;
    private BottomNavigationView bnv;
    private int posicion;
    private Button btnCrear;
    private TextInputLayout titulo;
    private TextInputLayout descripcion;
    private TextInputLayout direccion,num,localidad;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        miSpn = findViewById(R.id.spinner_tipo);
        btnCrear = findViewById(R.id.btn_crear);
        titulo = findViewById(R.id.textInputTitulo);
        descripcion = findViewById(R.id.textInputDescripcion);
        direccion = findViewById(R.id.textInputDireccion);
        num = findViewById(R.id.textInputNum);
        localidad = findViewById(R.id.textInputLocalidad);
        bnv = findViewById(R.id.nav_new_event);
        bnv.setSelectedItemId(R.id.navigation_new_event);
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
                        return true;
                    case R.id.navigation_community:
                        startActivity(new Intent(getApplicationContext(), ActivityCommunity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        String[] tipoEvent = {"Evento","Clase"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,tipoEvent);
        miSpn.setAdapter(adaptador);

        miSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] coordenadas = Util.takeCoordenadas(context,Objects.requireNonNull(direccion.getEditText()).getText().toString().trim(), Objects.requireNonNull(num.getEditText()).getText().toString().trim(), Objects.requireNonNull(localidad.getEditText()).getText().toString().trim());
                double latitud = coordenadas[0];
                double longitud = coordenadas[1];
                Evento e = new Evento(ActivityLogIn.USERUID, Objects.requireNonNull(titulo.getEditText()).getText().toString().trim(),latitud,longitud, Objects.requireNonNull(descripcion.getEditText()).getText().toString().trim(),tipoEvent[posicion]);
                CollectData.saveEvento(e);
            }
        });
    }
}