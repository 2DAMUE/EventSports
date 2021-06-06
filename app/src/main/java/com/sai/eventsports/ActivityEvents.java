package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.entidades.Apuntate;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.principales.ActivityMaps;
import com.sai.eventsports.principales.ActivityProfile;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.List;

public class ActivityEvents extends AppCompatActivity implements CollectData.ComunicacionVista {

    private TextView toolbar;
    private TextView deporte, direccion, descripcion, username, email, tarjetaText;
    private EditText tarjeta;
    private ImageView imgEvento, imgUser, vuelta;
    private String titulo, UserId, nom, lugar;
    private CollectData.ComunicacionVista comunicacionVista = this;
    private RadioButton rb1, rb2;
    private Button btn_apuntate, btn_chat;
    private LinearLayout verMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();
        titulo = intent.getStringExtra("NombreEvento");
        UserId = intent.getStringExtra("UserIdEvento");
        nom = intent.getStringExtra("Deporte");
        lugar = intent.getStringExtra("Lugar");

        toolbar = findViewById(R.id.toolbar_events);
        deporte = findViewById(R.id.textDeporte);
        direccion = findViewById(R.id.textLocation);
        descripcion = findViewById(R.id.textDescripcion);
        username = findViewById(R.id.username);
        email = findViewById(R.id.fullname);
        tarjeta = findViewById(R.id.textTarjeta);
        tarjetaText = findViewById(R.id.texttarjeta);
        imgEvento = findViewById(R.id.imagevnto);
        imgUser = findViewById(R.id.img_profile_evento);
        vuelta = findViewById(R.id.vuelta);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        btn_apuntate = findViewById(R.id.button3);
        btn_chat = findViewById(R.id.button2);
        verMapa = findViewById(R.id.verMapa);

        CollectData.traerEvento(comunicacionVista, UserId, titulo);
        CollectData.traerUsuario(comunicacionVista, UserId);

        toolbar.setText(titulo);
        Glide.with(getApplicationContext())
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosEvent%2F" + UserId + "-" + titulo + ".jpg?alt=media&token=2df21feb-3d9a-4e6a-8e53-770ce46c6740"))
                //.centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(imgEvento);
        Glide.with(getApplicationContext())
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/stellar-operand-305716.appspot.com/o/FotosPerfil%2F" + UserId + ".jpg?alt=media&token=5d406923-e2fd-4e85-8c3e-21a0d2630e83"))
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(imgUser);

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb2.isChecked()) {
                    tarjeta.setVisibility(View.INVISIBLE);
                    tarjetaText.setVisibility(View.INVISIBLE);
                }
            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()) {
                    tarjeta.setVisibility(View.VISIBLE);
                    tarjetaText.setVisibility(View.VISIBLE);
                }
            }
        });
        vuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lugar.equals("sc")) {
                    Intent intent = new Intent(ActivityEvents.this, SpecifyCategory.class);
                    intent.putExtra("NombreEvento", nom);
                    Bitmap bitmap = ((BitmapDrawable) SpecifyCategory.fondo.getDrawable()).getBitmap();
                    intent.putExtra("Fondo",bitmap);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ActivityEvents.this, ActivityProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        btn_apuntate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apuntate apuntate = new Apuntate(ActivityLogIn.USERUID, titulo, UserId);
                CollectData.subirApuntate(apuntate);
                Toast.makeText(ActivityEvents.this, "Apuntado!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEvents.this, ActivityChat.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("id", UserId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMaps.class);
                intent.putExtra("id",UserId);
                intent.putExtra("titulo",titulo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void mandarEvento(List<Evento> eventos) {
        for (Evento e : eventos) {
            deporte.setText(e.getDeporte());
            direccion.setText(e.getDireccion());
            descripcion.setText(e.getDescripcion());
        }
    }

    @Override
    public void mandarUsuario(List<User> user) {
        for (User u : user) {
            username.setText(u.getUser());
            email.setText(u.getEmail());
        }
    }
}