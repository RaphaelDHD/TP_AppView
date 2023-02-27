package com.example.tp_3;

import java.io.Serializable;

public class Contact implements Serializable {
    private String urlImage;
    private String nom;
    private String prenom;
    private String numPhone;
    private String dateNaissance;


    public String getNom() {
        return nom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public String getImg() {
        return urlImage;
    }

    public Contact(String urlImage , String nom, String prenom, String numPhone, String dateNaissance) {
        this.urlImage = urlImage;
        this.nom = nom;
        this.prenom = prenom;
        this.numPhone = numPhone;
        this.dateNaissance = dateNaissance;
    }
}
