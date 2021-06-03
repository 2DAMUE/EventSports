package com.sai.eventsports.entidades;

public class Mensaje {
    private String mensaje;
    private String username;
    private String uid_user;
    private String hora;

    public Mensaje() {
        super();
    }

    public Mensaje(String mensaje, String username, String uid_user, String hora) {
        this.mensaje = mensaje;
        this.username = username;
        this.uid_user = uid_user;
        this.hora = hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
