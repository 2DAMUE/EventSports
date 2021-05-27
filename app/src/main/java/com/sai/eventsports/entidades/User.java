package com.sai.eventsports.entidades;

public class User {
    private String userID;
    private String user;
    private String email;
    private String tlf;

    public User() {
        super();
    }

    public User(String userID, String user, String email, String tlf) {
        this.userID = userID;
        this.user = user;
        this.email = email;
        this.tlf = tlf;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
}
