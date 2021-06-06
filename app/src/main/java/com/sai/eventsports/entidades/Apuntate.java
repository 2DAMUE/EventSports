package com.sai.eventsports.entidades;

public class Apuntate {
    private String userid;
    private String titulo;
    private String id;

    public Apuntate() {
        super();
    }

    public Apuntate(String userid, String titulo, String id) {
        this.userid = userid;
        this.titulo = titulo;
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
