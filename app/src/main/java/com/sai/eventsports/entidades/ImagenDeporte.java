package com.sai.eventsports.entidades;

public class ImagenDeporte {

    private int url;
    private String nombre;

    public ImagenDeporte(String nombre, int url){
        this.url = url;
        this.nombre = nombre;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


