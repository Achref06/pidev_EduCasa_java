package entities;

import java.util.Date;

public class User {

    private int id;
    private String nom;
    private String prenom;

    private String mdp;

    private String email;

    private String role;

    private String specialite;

    private String niveau;

    public User(String nom, String prenom, String mdp, String email, String role, String specialite, String niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
        this.specialite = specialite;
        this.niveau = niveau;
    }

    public User(int id, String nom, String prenom, String mdp, String email, String role, String specialite, String niveau) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
        this.specialite = specialite;
        this.niveau = niveau;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", Mot de passe=" + mdp +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", specialite='" + specialite + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }
}
