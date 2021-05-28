package com.sai.eventsports;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;

import java.util.ArrayList;
import java.util.List;

public class CollectData {

    public static void saveUser(User u) {
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference subirUser = myDatabase.getReference("Users");
        String userId = u.getUserID();
        subirUser.child(userId).setValue(u);
    }

    public static void saveEvento(Evento e) {
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference subirEvento = myDatabase.getReference("Eventos");
        String userId = e.getUserid();
        String nom = e.getNombre();
        subirEvento.child(userId + "-" + nom).setValue(e);
    }

    public static void recogerUsers (Comunicacion comunicacion){
        List<User> users = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Users");
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> datos = snapshot.getChildren();
                for (DataSnapshot d: datos) {
                    User u = d.getValue(User.class);
                    users.add(u);
                }
                comunicacion.mandarUsuarios(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void recogerEventos (Comunicacion comunicacion){
        List<Evento> eventos = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Eventos");
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> datos = snapshot.getChildren();
                for (DataSnapshot d: datos) {
                    Evento e = d.getValue(Evento.class);
                    eventos.add(e);
                }
                comunicacion.mandarEventos(eventos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



    public interface Comunicacion{
        void mandarUsuarios(List<User> users);
        void mandarEventos(List<Evento> eventos);
    }
}
