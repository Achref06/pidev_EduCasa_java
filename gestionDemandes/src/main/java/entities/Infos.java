package entities;

import java.sql.Date;
import java.time.LocalDate;

public class Infos {
    private Form form;
    private int idP;
    private int idF;
    private String nom;
    private String prenom;
    private LocalDate dateN;
    private String email;
    private String experience;
    private String motivation;
    private String matiere;
    private String diplome;

    public Infos() {
    }

    public Infos(int idP, int idF, String nom, String prenom, LocalDate dateN, String email, String experience, String motivation, String matiere, String diplome) {
        this.idP = idP;
        this.idF = idF;
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.email = email;
        this.experience = experience;
        this.motivation = motivation;
        this.matiere = matiere;
        this.diplome = diplome;
    }
    public Infos(int idF, String nom, String prenom, LocalDate dateN, String email, String experience, String motivation, String matiere, String diplome) {
        this.idF = idF;
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.email = email;
        this.experience = experience;
        this.motivation = motivation;
        this.matiere = matiere;
        this.diplome = diplome;
    }
    public Infos(String nom, String prenom, LocalDate dateN, String email, String experience, String motivation, String matiere, String diplome) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.email = email;
        this.experience = experience;
        this.motivation = motivation;
        this.matiere = matiere;
        this.diplome = diplome;
    }

    public Infos(int idP) {
        this.idP=idP;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdF() {
        return idF;
    }

    public void setIdF(int idF) {
        this.idF = idF;
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

    public LocalDate getDateN() {
        return dateN;
    }

    public void setDateN(LocalDate dateN) {
        this.dateN = dateN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    @Override
    public String toString() {
        return "Infos{" +
                "form=" + form +
                ", idP=" + idP +
                ", idF=" + idF +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateN=" + dateN +
                ", email='" + email + '\'' +
                ", experience='" + experience + '\'' +
                ", motivation='" + motivation + '\'' +
                ", matiere='" + matiere + '\'' +
                ", diplome='" + diplome + '\'' +
                '}';
    }
}
