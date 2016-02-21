package com.yoannlt.velostar.renneslevlostar.model;

import android.util.TimeUtils;

/**
 * Created by yoannlt on 21/02/2016.
 */
public class Station {
    private String nom;
    private String etat;
    private TimeUtils lastUpdate;
    private int nbEmplacements;
    private int nbEmplacementDispo;
    private int nbVeloDispo;
    private int idStation;
    private String latitude;
    private String longitude;

    // Empty constructor
    public Station() {}

    // Full constructor
    public Station(String nom, String etat, TimeUtils lastUpdate, int nbEmplacements, int nbEmplacementDispo, int nbVeloDispo, int idStation, String latitude, String longitude) {
        this.nom = nom;
        this.etat = etat;
        this.lastUpdate = lastUpdate;
        this.nbEmplacements = nbEmplacements;
        this.nbEmplacementDispo = nbEmplacementDispo;
        this.nbVeloDispo = nbVeloDispo;
        this.idStation = idStation;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public TimeUtils getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(TimeUtils lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getNbEmplacements() {
        return nbEmplacements;
    }

    public void setNbEmplacements(int nbEmplacements) {
        this.nbEmplacements = nbEmplacements;
    }

    public int getNbEmplacementDispo() {
        return nbEmplacementDispo;
    }

    public void setNbEmplacementDispo(int nbEmplacementDispo) {
        this.nbEmplacementDispo = nbEmplacementDispo;
    }

    public int getNbVeloDispo() {
        return nbVeloDispo;
    }

    public void setNbVeloDispo(int nbVeloDispo) {
        this.nbVeloDispo = nbVeloDispo;
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
}
