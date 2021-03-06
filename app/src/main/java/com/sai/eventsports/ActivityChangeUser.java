package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.principales.ActivityProfile;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.List;
import java.util.Objects;

public class ActivityChangeUser extends AppCompatActivity implements CollectData.Comunicacion {

    private ImageView icBack;
    private Button btn_saveChange;
    private CollectData.Comunicacion comunicacion = this;
    private User user;
    private TextInputLayout antiguoUser, nuevoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        icBack = findViewById(R.id.ic_back);
        btn_saveChange = findViewById(R.id.btn_save_change_settings);
        antiguoUser = findViewById(R.id.edtxt_oldUser);
        nuevoUser = findViewById(R.id.edtxt_newUser);

        CollectData.recogerUsers(comunicacion);

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
                String olduser = Objects.requireNonNull(antiguoUser.getEditText()).getText().toString().trim();
                String newuser = Objects.requireNonNull(nuevoUser.getEditText()).getText().toString().trim();
                if (olduser.equals(user.getUser())){
                    User userModificado = new User(user.getUserID(),newuser,user.getEmail(),user.getTlf());
                    CollectData.saveUser(userModificado);
                    startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                }else{
                    antiguoUser.setError("Usuario no valido");
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
