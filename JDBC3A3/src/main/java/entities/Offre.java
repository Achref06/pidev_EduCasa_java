package entities;

import java.util.Objects;

public class Offre {
    private int id ;
    private String nom , statut, description;
    private float prix;

    public Offre(int id, String nom, String statut, String description, float prix) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.description = description;
        this.prix = prix;
    }

    public Offre(String nom, String statut, String description, float prix) {
        this.nom = nom;
        this.statut = statut;
        this.description = description;
        this.prix = prix;
    }

    public Offre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", statut='" + statut + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }


}
