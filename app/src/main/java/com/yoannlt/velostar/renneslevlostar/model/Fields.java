package com.yoannlt.velostar.renneslevlostar.model;

/**
 * Created by yoannlt on 23/02/2016.
 */
public class Fields {
    private String etat;
    private String lastupdate;
    private int nombrevelosdisponibles;
    private int nombreemplacementsactuels;
    private String nom;
    private int nombreemplacementsdisponibles;
    private int idstation;
    private double[] coordonnees;

    public Fields() {
    }

    public Fields(String etat, String lastupdate, int nombrevelosdisponibles, int nombreemplacementsactuels, String nom, int nombreemplacementsdisponibles, int idstation, double[] coordonnees) {
        this.etat = etat;
        this.lastupdate = lastupdate;
        this.nombrevelosdisponibles = nombrevelosdisponibles;
        this.nombreemplacementsactuels = nombreemplacementsactuels;
        this.nom = nom;
        this.nombreemplacementsdisponibles = nombreemplacementsdisponibles;
        this.idstation = idstation;
        this.coordonnees = coordonnees;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public int getNombrevelosdisponibles() {
        return nombrevelosdisponibles;
    }

    public void setNombrevelosdisponibles(int nombrevelosdisponibles) {
        this.nombrevelosdisponibles = nombrevelosdisponibles;
    }

    public int getNombreemplacementsactuels() {
        return nombreemplacementsactuels;
    }

    public void setNombreemplacementsactuels(int nombreemplacementsactuels) {
        this.nombreemplacementsactuels = nombreemplacementsactuels;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombreemplacementsdisponibles() {
        return nombreemplacementsdisponibles;
    }

    public void setNombreemplacementsdisponibles(int nombreemplacementsdisponibles) {
        this.nombreemplacementsdisponibles = nombreemplacementsdisponibles;
    }

    public int getIdstation() {
        return idstation;
    }

    public void setIdstation(int idstation) {
        this.idstation = idstation;
    }

    public double[] getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(double[] coordonnees) {
        this.coordonnees = coordonnees;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "etat='" + etat + '\'' +
                ", lastupdate='" + lastupdate + '\'' +
                ", nombrevelosdisponibles=" + nombrevelosdisponibles +
                ", nombreemplacementsactuels=" + nombreemplacementsactuels +
                ", nom='" + nom + '\'' +
                ", nombreemplacementsdisponibles=" + nombreemplacementsdisponibles +
                ", idstation=" + idstation +
                '}';
    }
}
