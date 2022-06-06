package com.example.gestion_formation.models;

import javafx.beans.property.*;

import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;

public class Participant {

    IntegerProperty Matricule_participant;
    StringProperty Nom;
    StringProperty Prenom;
    ObjectProperty<LocalDate> Date_naissance;
    StringProperty Profil;

    public Participant(int Matricule_participant, String Nom, String Prenom, LocalDate Date_naissance, String Profil) {
        this.Matricule_participant =new SimpleIntegerProperty(Matricule_participant);
        this.Nom =  new SimpleStringProperty(Nom);
        this.Prenom =  new SimpleStringProperty (Prenom);
        this.Date_naissance =new SimpleObjectProperty<>( Date_naissance);
        this.Profil =new SimpleStringProperty( Profil);
    }

    public int getMatricule_participant() {
        return Matricule_participant.get();
    }

    public IntegerProperty matricule_participantProperty() {
        return Matricule_participant;
    }

    public void setMatricule_participant(int matricule_participant) {
        this.Matricule_participant.set(matricule_participant);
    }

    public String getNom() {
        return Nom.get();
    }

    public StringProperty nomProperty() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom.set(nom);
    }

    public String getPrenom() {
        return Prenom.get();
    }

    public StringProperty prenomProperty() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom.set(prenom);
    }

    public LocalDate getDate_naissance() {
        return Date_naissance.get();
    }

    public ObjectProperty<LocalDate> date_naissanceProperty() {
        return Date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.Date_naissance.set(date_naissance);
    }

    public String getProfil() {
        return Profil.get();
    }

    public StringProperty profilProperty() {
        return Profil;
    }

    public void setProfil(String profil) {
        this.Profil.set(profil);
    }
}
