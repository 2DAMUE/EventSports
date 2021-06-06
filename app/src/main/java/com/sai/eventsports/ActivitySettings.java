package com.sai.eventsports;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.sai.eventsports.splash_login_register.ActivityChangePhone;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

public class ActivitySettings extends AppCompatActivity {

    private LinearLayout cerrarSesion;
    private LinearLayout politicaDePrivacidad;
    private LinearLayout contacto;

    private LinearLayout changeUser;
    private LinearLayout changePhone;
    private LinearLayout changePersonalInf;

    private Switch switchContactos;
    int REQUEST_CODE = 200;

    //Google
    private GoogleSignInClient mGoogleSignInClient;
    //Facebook
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchContactos = findViewById(R.id.switch_contactos);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        politicaDePrivacidad = findViewById(R.id.politica_privacidad);
        contacto = findViewById(R.id.contacto);
        changeUser = findViewById(R.id.cambiar_usuario);
        changePhone = findViewById(R.id.cambiar_telefono);

        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySettings.this, ActivityChangeUser.class);
                startActivity(intent);
            }

        });

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySettings.this, ActivityChangePhone.class);
                startActivity(intent);
            }

        });

        mAuth = ActivityLogIn.recogerInstancia();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //****************** BUTTONS ACTIONS ****************

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                ActivityLogIn.USERUID = null;
                Intent intent = new Intent(ActivitySettings.this, ActivityLogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        politicaDePrivacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySettings.this, ActivityPDF.class);
                startActivity(intent);
            }
        });

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogContact();
            }
        });

    }

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    public void onclick(View view){

        if (view.getId() == R.id.switch_contactos){
            if(switchContactos.isChecked()){
            }else{

            }

        }
    }*/

    /**
     * In this method, the password recovery screen has been implemented
     * as an alert dialog.
     */
    private void showAlertDialogContact() {
        // Here we setup the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.activity_contact,
                null));

        // Here we create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    private void verificarPermisos() {
        int permisos = ContextCompat.checkSelfPermission( this,Manifest.permission.READ_CONTACTS);
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CODE);
        if(permisos == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Permiso ACEPTADO", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Permiso DENEGADO", Toast.LENGTH_SHORT).show();
        }
    }*/
}