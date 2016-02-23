package com.yoannlt.velostar.renneslevlostar.model;

import android.util.TimeUtils;

/**
 * Created by yoannlt on 21/02/2016.
 */
public class Station {
    private String etat;
    private String nom;
    private int nombreemplacementsactuels;
    private int nombreemplacementsdisponibles;
    private int nombrevelosdisponibles;
    private int idStation;
    private String latitude;
    private String longitude;

    // Empty constructor
    public Station() {}

    // Full constructor
    public Station(String nom, String etat, TimeUtils lastUpdate, int nbEmplacements, int nbEmplacementDispo, int nbVeloDispo, int idStation /*String latitude, String longitude*/) {
        this.nom = nom;
        this.etat = etat;
        this.nombreemplacementsactuels = nbEmplacements;
        this.nombreemplacementsdisponibles = nbEmplacementDispo;
        this.nombrevelosdisponibles = nbVeloDispo;
        this.idStation = idStation;
        //this.latitude = latitude;
        //this.longitude = longitude;
    }

    // Getters & Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNombreemplacementsactuels() {
        return nombreemplacementsactuels;
    }

    public void setNombreemplacementsactuels(int nombreemplacementsactuels) {
        this.nombreemplacementsactuels = nombreemplacementsactuels;
    }

    public int getNombreemplacementsdisponibles() {
        return nombreemplacementsdisponibles;
    }

    public void setNombreemplacementsdisponibles(int nombreemplacementsdisponibles) {
        this.nombreemplacementsdisponibles = nombreemplacementsdisponibles;
    }

    public int getNombrevelosdisponibles() {
        return nombrevelosdisponibles;
    }

    public void setNombrevelosdisponibles(int nombrevelosdisponibles) {
        this.nombrevelosdisponibles = nombrevelosdisponibles;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Station{" +
                "nom='" + nom + '\'' +
                ", etat='" + etat + '\'' +
                ", nombreemplacementsactuels=" + nombreemplacementsactuels +
                ", nombreemplacementsdisponibles=" + nombreemplacementsdisponibles +
                ", nombrevelosdisponibles=" + nombrevelosdisponibles +
                ", idStation=" + idStation +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
