package com.sai.eventsports.principales;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sai.eventsports.CollectData;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;

import java.util.List;

public class ActivityMaps extends FragmentActivity implements OnMapReadyCallback, CollectData.Comunicacion {

    private GoogleMap mMap;
    private BottomNavigationView bnv;
    private LocationManager lm;
    private static final int REQUEST_CODE = 101;
    private FloatingActionButton find_Location;
    private CollectData.Comunicacion comunicacion = this;
    private String id, tituloEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_bueno);

        find_Location = findViewById(R.id.fab);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tituloEvent = intent.getStringExtra("titulo");

        bnv = findViewById(R.id.nav_maps);
        bnv.setSelectedItemId(R.id.navigation_maps);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_maps:
                        return true;
                    case R.id.navigation_new_event:
                        startActivity(new Intent(getApplicationContext(), ActivityNewEvent.class));
                        overridePendingTransition(0, 0);
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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkPermissionClient();
        find_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerLocalizacion();
            }
        });
        CollectData.recogerEventos(comunicacion);
    }

    private void checkPermissionClient() {
        //Aqui prguntaremos si quiere dar permiso de ubicacion a la aplicacion en caso de que no tenga ira al metodo onRequestPermissionsResult(),
        //si ya tiene los permiso ira directamente a obtenerLocalizacion()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //comprobamos que le dan permiso a la apliocacion para que coja la ubicacio
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerLocalizacion();
            }
        }
    }

    private void obtenerLocalizacion() {
        //Aqui revisamos si estan los permisos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Este toast es para que muestre el mensaje de que busca una ubicacion
        Toast toast = Toast.makeText(getApplicationContext(), "Buscando Ubicacion.....", Toast.LENGTH_SHORT);
        toast.show();
        //Aqui ponemos un oyesnte a la ubicacion y en caso de que sea nulo le decimos que ponga la ultima encontrada
        //y si no que compruebe si la latitud y longitud no son nulas
        //en caso de que sean busca otra vez y si las encuentra que lo marque en el mapa
        LocationListener oyente_localizaciones = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("Paso", " Por aqui");
                findLocation(location);
                lm.removeUpdates(this);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(ActivityMaps.this, "Activa el GPS", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }
        };
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, oyente_localizaciones);
    }

    private void findLocation(Location location) {
        if (location != null) {
            double latitud = location.getLatitude();
            double longitud = location.getLongitude();
            Log.d("Coordenadas", latitud + " " + longitud);
            // Add a marker in Sydney and move the camera
            LatLng ubi = new LatLng(latitud, longitud);
            mMap.addMarker(new MarkerOptions().position(ubi).title("Mi Ubicaci??n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 15));
        }
    }

    private void drawLocationEvents(double latitud, double longitud, String tipo, String titulo, String userId) {
        //Aqui se pasan las coordenadas
        LatLng ubi = new LatLng(latitud, longitud);
        //Aqui dirigimos la camara a la ubicacion
        mMap.animateCamera(CameraUpdateFactory.newLatLng(ubi));
        //Esto es el marcador con el titulo de ubicacion
        if (tipo.equals("Evento")) {
            mMap.addMarker(new MarkerOptions().position(ubi)
                    .title(titulo)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        } else if (tipo.equals("Clase")) {
            mMap.addMarker(new MarkerOptions().position(ubi)
                    .title(titulo)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 7));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void mandarUsuarios(List<User> users) {
    }

    @Override
    public void mandarEventos(List<Evento> eventos) {
        Evento evento = new Evento();
        for (Evento e : eventos) {
            if (e.getUserid().equals(id) && e.getNombre().equals(tituloEvent)) {
                evento = e;
            } else {
                drawLocationEvents(e.getLatitud(), e.getLongitud(), e.getTipo(), e.getNombre(), e.getUserid());
            }
        }
        drawLocationEventsZoom(evento);
    }

    private void drawLocationEventsZoom(Evento e) {
        if (e.getUserid() != null) {
            //Aqui se pasan las coordenadas
            LatLng ubi = new LatLng(e.getLatitud(), e.getLongitud());
            //Aqui dirigimos la camara a la ubicacion
            mMap.animateCamera(CameraUpdateFactory.newLatLng(ubi));
            //Esto es el marcador con el titulo de ubicacion
            if (e.getTipo().equals("Evento")) {
                mMap.addMarker(new MarkerOptions().position(ubi)
                        .title(e.getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            } else if (e.getTipo().equals("Clase")) {
                mMap.addMarker(new MarkerOptions().position(ubi)
                        .title(e.getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 15));
        }
    }
}