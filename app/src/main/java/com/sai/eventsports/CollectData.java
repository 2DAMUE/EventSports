package com.sai.eventsports;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public static void recogerUsers (Comunicación comunicacion){
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

    public interface Comunicación{
        void mandarUsuarios(List<User> users);
    }
}
