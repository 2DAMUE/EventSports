package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.eventsports.entidades.Mensaje;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.principales.ActivityMain;
import com.sai.eventsports.recycler.MiAdaptadorChat;
import com.sai.eventsports.recycler.MiAdaptadorCommunity;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

import java.util.ArrayList;
import java.util.List;

public class ActivityChat extends AppCompatActivity implements CollectData.ComunicacionChat {

    private TextView txtMensaje;
    private RecyclerView recyclerViewChat;
    private Toolbar toolbar;
    private ImageView btnEnviar;
    private LinearLayoutManager gestor;
    private String titulo, Userid, username;
    private CollectData.ComunicacionChat comunicacionChat = this;
    private MiAdaptadorChat listAdapter;
    private List<Mensaje> listaMensajes;
    private ImageView imagepatras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtMensaje = findViewById(R.id.edit_txt_chat);
        toolbar = findViewById(R.id.toolbar_chat);
        recyclerViewChat = findViewById(R.id.recycler_chat);
        btnEnviar = findViewById(R.id.btn_chat);
        gestor = new LinearLayoutManager(this);
        imagepatras = findViewById(R.id.vuelta);

        Intent intent = getIntent();
        titulo = intent.getStringExtra("Titulo");
        Userid = intent.getStringExtra("id");

        toolbar.setTitle("Chat " + titulo);

        CollectData.traerUsuarioChat(comunicacionChat, ActivityLogIn.USERUID);
        CollectData.recogerChat(comunicacionChat, titulo, Userid, Util.cogerHora()[0]);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] hora = Util.cogerHora();
                Mensaje m = new Mensaje(txtMensaje.getText().toString().trim(), username, ActivityLogIn.USERUID, hora[0]);
                CollectData.subirMensaje(hora[1], m, Userid, titulo);
                txtMensaje.setText("");
            }
        });
        recyclerViewChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    recyclerViewChat.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (listAdapter == null) {
                                Mensaje m2 = new Mensaje("Ayudemonos entre nosotros!", "Asistente Virtual", "", Util.cogerHora()[0]);
                                CollectData.subirMensaje(Util.cogerHora()[1], m2, Userid, titulo);
                            } else {
                                recyclerViewChat.smoothScrollToPosition(listAdapter.getItemCount() - 1);
                            }
                        }
                    }, 100);
                }
            }
        });

        imagepatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityMain.class));
            }
        });
    }

    @Override
    public void mandarUsuario(List<User> user) {
        for (User u : user) {
            username = u.getUser();
        }
    }

    @Override
    public void madarChat(List<Mensaje> listaChat) {
        listAdapter = new MiAdaptadorChat(listaChat);
        recyclerViewChat.setLayoutManager(gestor);
        recyclerViewChat.setAdapter(listAdapter);
        setScrollBar();
    }

    private void setScrollBar() {
        recyclerViewChat.scrollToPosition(listAdapter.getItemCount() - 1);
    }
}