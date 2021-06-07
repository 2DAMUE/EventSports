package com.sai.eventsports.principales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.sai.eventsports.CollectData;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.R;
import com.sai.eventsports.Util;
import com.sai.eventsports.entidades.ImagenDeporte;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.Objects;

public class ActivityNewEvent extends AppCompatActivity {

    private RadioButton rbClase, rbEvento;
    private BottomNavigationView bnv;
    private int posicion, posicionDeporte;
    private Button btnCrear;
    private TextInputLayout titulo;
    private TextInputLayout descripcion;
    private Spinner spinnerDeporte;
    private TextInputLayout direccion, num, localidad;
    private Context context = this;
    private static final int REQUEST_PERMISSON_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private Uri imageUri;
    private ImageView galleryImage;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        rbClase = findViewById(R.id.radioButtonClase);
        rbEvento = findViewById(R.id.radioButtonEvento);
        btnCrear = findViewById(R.id.btn_crear);
        titulo = findViewById(R.id.textInputTitulo);
        descripcion = findViewById(R.id.textInputDescripcion);
        spinnerDeporte = findViewById(R.id.spinner_Deporte);
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

        rbEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbEvento.isChecked()) {
                    tipo = "Evento";
                }
            }
        });
        rbClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbClase.isChecked()) {
                    tipo = "Clase";
                }
            }
        });
        String[] tipoDeporte = {"Futbol", "Baloncesto", "Tenis", "Voleibol", "Natación", "Badminton", "Atletismo", "Balonmano", "Waterpolo", "Padel", "Ajedrez"};
        ArrayAdapter<String> adaptado = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, tipoDeporte);
        spinnerDeporte.setAdapter(adaptado);

        spinnerDeporte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionDeporte = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dir = Objects.requireNonNull(direccion.getEditText()).getText().toString().trim();
                String numerito = Objects.requireNonNull(num.getEditText()).getText().toString().trim();
                String loca = Objects.requireNonNull(localidad.getEditText()).getText().toString().trim();
                String tituloo = Objects.requireNonNull(titulo.getEditText()).getText().toString().trim();
                String desc = Objects.requireNonNull(descripcion.getEditText()).getText().toString().trim();
                if (imageUri == null) {
                    Toast.makeText(getApplicationContext(), "Escoja una imagen", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(tituloo)) {
                    titulo.setError("Escriba un Titulo");
                } else if (TextUtils.isEmpty(dir)) {
                    direccion.setError("Escriba una Dirección");
                } else if (TextUtils.isEmpty(loca)) {
                    localidad.setError("Escriba una Localidad");
                } else if (TextUtils.isEmpty(desc)) {
                    descripcion.setError("Escriba una Descripción");
                } else if (!rbEvento.isChecked() && !rbClase.isChecked()){
                    Toast.makeText(getApplicationContext(), "Escoja un Tipo", Toast.LENGTH_SHORT).show();
                } else{
                    double[] coordenadas = Util.takeCoordenadas(context, dir, numerito, loca);
                    double latitud = coordenadas[0];
                    double longitud = coordenadas[1];
                    String direc = dir + "," + numerito + "," + loca;
                    Evento e = new Evento(ActivityLogIn.USERUID, tituloo, latitud, longitud, direc, desc, tipo, tipoDeporte[posicionDeporte]);
                    CollectData.saveImgEvent(imageUri, tituloo);
                    CollectData.saveEvento(e);
                    startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                }
            }
        });

        galleryImage = (ImageView) findViewById(R.id.escogerImagen);

        Glide.with(this)
                .load("android.resource://" + getPackageName() + "/" + R.drawable.user_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(galleryImage);

        galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(ActivityNewEvent.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        ActivityCompat.requestPermissions(ActivityNewEvent.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSON_CODE);
                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_GALLERY) {
            imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .circleCrop()
                    .into(galleryImage);
        } else {
            Log.i("RAG", "Result: " + resultCode);
            Toast.makeText(this, "You did not choose any photo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSON_CODE) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "You need to enable permissions", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }
}