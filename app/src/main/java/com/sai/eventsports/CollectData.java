package com.sai.eventsports;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
