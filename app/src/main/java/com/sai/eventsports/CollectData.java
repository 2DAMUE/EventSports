package com.sai.eventsports;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sai.eventsports.entidades.Apuntate;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.Mensaje;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.splash_login_register.ActivityLogIn;

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

    public static void saveImg(Uri imageUri) {
        StorageReference profileImageReference = FirebaseStorage.getInstance().getReference("FotosPerfil/" + ActivityLogIn.USERUID + ".jpg");

        if (imageUri != null) {
            profileImageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String profileImageUrl = taskSnapshot.getUploadSessionUri().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("PUTAIMG", e.getLocalizedMessage());
                }
            });
        }
    }

    public static void saveImgEvent(Uri imageUri, String nombre) {
        StorageReference profileImageReference = FirebaseStorage.getInstance().getReference("FotosEvent/" + ActivityLogIn.USERUID + "-" + nombre + ".jpg");

        if (imageUri != null) {
            profileImageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String eventImageUrl = taskSnapshot.getUploadSessionUri().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("PUTAIMG", e.getLocalizedMessage());
                }
            });
        }
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

    public static void traerEvento(ComunicacionVista comunicacionVista, String userId, String titulo) {
        List<Evento> evento = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Eventos").child(userId + "-" + titulo);
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Evento e = snapshot.getValue(Evento.class);
                evento.add(e);
                comunicacionVista.mandarEvento(evento);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void traerUsuario(ComunicacionVista comunicacionVista, String userId) {
        List<User> user = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Users").child(userId);
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                user.add(u);
                comunicacionVista.mandarUsuario(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public static void traerUsuarioChat(ComunicacionChat comunicacionChat, String userId) {
        List<User> user = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Users").child(userId);
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                user.add(u);
                comunicacionChat.mandarUsuario(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void subirMensaje(String s, Mensaje m, String userid, String titulo) {
        DatabaseReference referenceChat = FirebaseDatabase.getInstance().getReference();
        DatabaseReference referenceMessage = referenceChat.child("Chats").child(userid + "-" + titulo);
        referenceMessage.child(s).setValue(m);
    }

    public static void recogerChat(ComunicacionChat comunicacionChat, String titulo, String userid, String h) {
        DatabaseReference referenceChat = FirebaseDatabase.getInstance().getReference();
        DatabaseReference referenceMessage = referenceChat.child("Chats").child(userid + "-" + titulo);
        referenceMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> datos = snapshot.getChildren();
                List<Mensaje> listaChat = new ArrayList<>();
                for (DataSnapshot d : datos) {
                    Mensaje m = d.getValue(Mensaje.class);
                    listaChat.add(m);
                    comunicacionChat.madarChat(listaChat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void subirApuntate(Apuntate apuntate) {
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference subirApuntate = myDatabase.getReference("Apuntate").child(apuntate.getUserid());
        subirApuntate.child(apuntate.getId() + "-" + apuntate.getTitulo()).setValue(apuntate);
    }

    public static void traerApuntate(ComunicacionApuntate comunicacionApuntate, String useruid) {
        List<Apuntate> deportes = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerApuntate = myDatabase.getReference("Apuntate").child(useruid);
        traerApuntate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> datos = snapshot.getChildren();
                for (DataSnapshot d: datos) {
                    Apuntate a = d.getValue(Apuntate.class);
                    deportes.add(a);
                }
                comunicacionApuntate.mandarApuntate(deportes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void recogerEventosApuntate(ComunicacionApuntate comunicacionApuntate) {
        List<Evento> eventos = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerEvents = myDatabase.getReference("Eventos");
        traerEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> datos = snapshot.getChildren();
                for (DataSnapshot d: datos) {
                    Evento e = d.getValue(Evento.class);
                    eventos.add(e);
                }
                comunicacionApuntate.mandarEventos(eventos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void traerEventoApuntate(ComunicacionApuntate comunicacionApuntate, String id, String titulo) {
        List<Evento> evento = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Eventos").child(id + "-" + titulo);
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Evento e = snapshot.getValue(Evento.class);
                evento.add(e);
                comunicacionApuntate.mandarEvento(evento);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void traerUsuarioPerfil(ComunicacionApuntate comunicacionApuntate, String useruid) {
        List<User> user = new ArrayList<>();
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference traerUser = myDatabase.getReference("Users").child(useruid);
        traerUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                user.add(u);
                comunicacionApuntate.mandarUsuario(user);
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
    public interface ComunicacionVista{
        void mandarEvento(List<Evento> eventos);
        void mandarUsuario(List<User> user);
    }
    public interface ComunicacionChat{
        void mandarUsuario(List<User> user);
        void madarChat(List<Mensaje> listaChat);
    }
    public interface ComunicacionApuntate{
        void mandarApuntate(List<Apuntate> deportes);
        void mandarEventos(List<Evento> eventos);
        void mandarEvento(List<Evento> evento);
        void mandarUsuario(List<User> user);
    }
}
