package Entities;

import java.time.LocalDate;

public class Formation {

    private int idFormation;
    private String nomFormation;
    private String dureeFormation;
    private String niveauFormation;
    private int nbJours;

    public Formation() {
    }

    public Formation(int idFormation, String nomFormation, String dureeFormation, String niveauFormation, int nbJours) {
        this.idFormation = idFormation;
        this.nomFormation = nomFormation;
        this.dureeFormation = dureeFormation;
        this.niveauFormation = niveauFormation;
        this.nbJours = nbJours;
    }

    public Formation(String nomFormation, String dureeFormation, String niveauFormation, int nbJours) {
        this.nomFormation = nomFormation;
        this.dureeFormation = dureeFormation;
        this.niveauFormation = niveauFormation;
        this.nbJours = nbJours;
    }

    public Formation(String text, String text1, String text2, String value, String value1) {
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getDureeFormation() {
        return dureeFormation;
    }

    public void setDureeFormation(String dureeFormation) {
        this.dureeFormation = dureeFormation;
    }

    public String getNiveauFormation() {
        return niveauFormation;
    }

    public void setNiveauFormation(String niveauFormation) {
        this.niveauFormation = niveauFormation;
    }

    public int getNbJours() {
        return nbJours;
    }

    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "idFormation=" + idFormation +
                ", nomFormation='" + nomFormation + '\'' +
                ", dureeFormation='" + dureeFormation + '\'' +
                ", niveauFormation='" + niveauFormation + '\'' +
                ", nbJours=" + nbJours +
                '}';
    }
}
