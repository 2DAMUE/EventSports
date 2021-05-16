package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ActivityNewEvent extends AppCompatActivity {
    private Spinner miSpn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        miSpn = findViewById(R.id.spinner_tipo);

        String[] tipoEvent = {"Evento","Clase"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,tipoEvent);
        miSpn.setAdapter(adaptador);

        /*miSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }
}