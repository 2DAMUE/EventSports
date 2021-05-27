package com.sai.eventsports.entidades;

public class Evento {
    private String userid;
    private String nombre;
    private double latitud;
    private double longitud;
    private String direccion;
    private String descripcion;
    private String tipo;

    public Evento() {
        super();
    }

    public Evento(String userid, String nombre, double latitud, double longitud, String direccion, String descripcion, String tipo) {
        this.userid = userid;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
