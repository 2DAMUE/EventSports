package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

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

        //firebaseAuth = ActivityLogIn.recogerInstancia();
        // Configure Google Sign In
        /*GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);*/
        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ImagenDeporte("Futbol",R.drawable.bota));
        elements.add(new ImagenDeporte("Baloncesto", R.drawable.baloncesto));
        elements.add(new ImagenDeporte("Tenis", R.drawable.tenis));
        elements.add(new ImagenDeporte("Voleibol", R.drawable.voleibol));
        elements.add(new ImagenDeporte("Natación", R.drawable.natacion));
        elements.add(new ImagenDeporte("Badminton", R.drawable.branded));
        elements.add(new ImagenDeporte("Atletismo", R.drawable.atle));
        elements.add(new ImagenDeporte("Balonmano", R.drawable.bola));
        elements.add(new ImagenDeporte("Waterpolo", R.drawable.waterpolo));
        elements.add(new ImagenDeporte("Padel", R.drawable.padel));
        elements.add(new ImagenDeporte("Ajedrez", R.drawable.ajedrez));

        MiAdaptadorMain listAdapter = new MiAdaptadorMain(elements);
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