package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    List<ImagenDeporte> elements;
    protected ImageView imagenDeporte;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = ActivityLogIn.recogerInstancia();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ImagenDeporte("futbol"));
        elements.add(new ImagenDeporte("baloncesto"));
        elements.add(new ImagenDeporte("tenis"));

        MiAdaptadorMain listAdapter = new MiAdaptadorMain(elements,this);
        RecyclerView recyclerView = findViewById(R.id.mi_RecyclerMain);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityMain.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
    }
}
/*Button btn = findViewById(R.id.signout);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mGoogleSignInClient.signOut();
        firebaseAuth.signOut();
        ActivityLogIn.USERUID = null;
        Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
});
 */