package com.example.gestion_formation.models;

public class Former {
    private int code_former;
    private String nom;
    private String prenom;
    private String domaine;
    private String mail;
    private int phone;
    public Former(int code_former, String nom, String prenom, String domaine, String mail, int phone) {
        this.code_former = code_former;
        this.nom = nom;
        this.prenom = prenom;
        this.domaine = domaine;
        this.mail = mail;
        this.phone = phone;
    }

    public int getCode_former() {
        return code_former;
    }

    public void setCode_former(int code_former) {
        this.code_former = code_former;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
