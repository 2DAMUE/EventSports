package com.sai.eventsports.splash_login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sai.eventsports.R;


public class SplashScreen extends AppCompatActivity {

    protected  ImageView logo;

    protected ImageView fondoSplScr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Inicializacion de "logo"
        logo = (ImageView) findViewById(R.id.logoApp);
        //Incializacion de la animacion "rotate" de "logo"
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        logo.startAnimation(myAnimation);

        //Inicializacion de "fondoSplScr"
        fondoSplScr = findViewById(R.id.back_dog_Food);
        String url ="https://images.unsplash.com/photo-1502014822147-1aedfb0676e0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80";
        //Recolector de imagen en "fondoSplScr" mediante url
        Glide.with(this)
                .load("android.resource://" + getPackageName() + "/"+ R.drawable.fondo_splash)
                //.load("android.resource://" + getPackageName() + "/"+ R.drawable.fondo_degradado)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.white)))
                .into(fondoSplScr);

        openApp(true);




    }


    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen
                        .this, ActivityLogIn.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}