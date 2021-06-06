package com.sai.eventsports.principales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sai.eventsports.ActivitySettings;
import com.sai.eventsports.CollectData;
import com.sai.eventsports.entidades.Apuntate;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.ImagenDeporte;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.recycler.MiAdaptadorMain;
import com.sai.eventsports.R;
import com.sai.eventsports.recycler.MiAdaptadorProfile;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.ArrayList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity implements CollectData.ComunicacionApuntate {

    private BottomNavigationView bnv;
    private static final int REQUEST_PERMISSON_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private Uri imageUri;
    private ImageView galleryImage;
    private RecyclerView recyclerViewPublications;
    private RecyclerView recyclerViewRegisters;
    private List<Evento> elements = new ArrayList<>();;
    private CollectData.ComunicacionApuntate comunicacionApuntate = this;
    private TextView userName, email, tlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerViewPublications = findViewById(R.id.mi_RecyclerPublications);
        recyclerViewRegisters = findViewById(R.id.mi_RecyclerRegisters);
        userName = findViewById(R.id.textViewUserNameProfile);
        email = findViewById(R.id.textViewEmailProfile);
        tlf = findViewById(R.id.textViewUserTelefonofile);

        bnv = findViewById(R.id.nav_profile);
        bnv.setSelectedItemId(R.id.navigation_profile);
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
                        startActivity(new Intent(getApplicationContext(), ActivityCommunity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_profile:
                        return true;
                }
                return false;
            }
        });
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(myToolbar);

        galleryImage = (ImageView) findViewById(R.id.imageViewProfilePhoto);

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
                    if (ActivityCompat.checkSelfPermission(ActivityProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        ActivityCompat.requestPermissions(ActivityProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSON_CODE);
                    }
                }

            }
        });
        Glide.with(getApplicationContext())
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosPerfil%2F" + ActivityLogIn.USERUID + ".jpg?alt=media&token=5d406923-e2fd-4e85-8c3e-21a0d2630e83"))
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(galleryImage);

        CollectData.traerUsuarioPerfil(comunicacionApuntate,ActivityLogIn.USERUID);
        CollectData.traerApuntate(comunicacionApuntate,ActivityLogIn.USERUID);
        CollectData.recogerEventosApuntate(comunicacionApuntate);
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
            //CollectData.saveImg(imageUri);
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

    /**
     * Sirve para ir a setting en el toolbar
     * @param //item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_button2) {
            Intent intent = new Intent(getApplicationContext(), ActivitySettings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void mandarApuntate(List<Apuntate> deportes) {
        for (Apuntate a:deportes) {
            CollectData.traerEventoApuntate(comunicacionApuntate,a.getId(),a.getTitulo());
        }
    }

    @Override
    public void mandarEventos(List<Evento> eventos) {
        List<Evento> eventos1 = new ArrayList<>();
        for (Evento e:eventos) {
            if(e.getUserid().equals(ActivityLogIn.USERUID)){
                eventos1.add(e);
            }
        }
        MiAdaptadorProfile listAdapter = new MiAdaptadorProfile(eventos1);
        recyclerViewPublications.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityProfile.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPublications.setLayoutManager(layoutManager);
        recyclerViewPublications.setAdapter(listAdapter);
    }

    @Override
    public void mandarEvento(List<Evento> evento) {
        elements.addAll(evento);
        MiAdaptadorProfile listAdapter = new MiAdaptadorProfile(elements);
        recyclerViewRegisters.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityProfile.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRegisters.setLayoutManager(layoutManager);
        recyclerViewRegisters.setAdapter(listAdapter);
    }

    @Override
    public void mandarUsuario(List<User> user) {
        for (User u:user) {
            userName.setText(u.getUser());
            email.setText(u.getEmail());
            tlf.setText(u.getTlf());
        }
    }
}