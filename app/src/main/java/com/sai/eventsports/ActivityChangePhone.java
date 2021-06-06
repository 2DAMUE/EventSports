package com.sai.eventsports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.principales.ActivityProfile;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.List;
import java.util.Objects;

public class ActivityChangePhone extends AppCompatActivity implements CollectData.Comunicacion {

    private ImageView icBack;
    private Button btn_saveChange;
    private CollectData.Comunicacion comunicacion = this;
    private User user;
    private TextInputLayout antiguoTlf, nuevoTlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        icBack = findViewById(R.id.ic_back);
        btn_saveChange = findViewById(R.id.btn_save_change_settings);
        antiguoTlf = findViewById(R.id.edtxt_oldPhone);
        nuevoTlf = findViewById(R.id.edtxt_newPhone);

        CollectData.recogerUsers(comunicacion);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangePhone.this, ActivitySettings.class);
                startActivity(intent);
            }

        });

        btn_saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldtlf = Objects.requireNonNull(antiguoTlf.getEditText()).getText().toString().trim();
                String newtlf = Objects.requireNonNull(nuevoTlf.getEditText()).getText().toString().trim();
                if (oldtlf.equals(user.getTlf())){
                    User userModificado = new User(user.getUserID(),user.getUser(),user.getEmail(),newtlf);
                    CollectData.saveUser(userModificado);
                    startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                }else{
                    antiguoTlf.setError("Teléfono no valido");
                }
            }
        });
    }

    @Override
    public void mandarUsuarios(List<User> users) {
        user = new User();
        for (User u:users) {
            if (u.getUserID().equals(ActivityLogIn.USERUID)){
                user = u;
            }
        }
    }

    @Override
    public void mandarEventos(List<Evento> eventos) {}
}
