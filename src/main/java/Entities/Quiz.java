package Entities;

public class Quiz {
    private int id;
    private String nom;
    private int nbQuest;
    private int note;

    public Quiz(int id, String nom, int nbQuest, int note) {
        this.id = id;
        this.nom = nom;
        this.nbQuest = nbQuest;
        this.note = note;
    }
    public Quiz( String nom, int nbQuest, int note) {
        this.nom = nom;
        this.nbQuest = nbQuest;
        this.note = note;
    }
    public Quiz(){}

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

    public int getNbQuest() {
        return nbQuest;
    }

    public void setNbQuest(int nbQuest) {
        this.nbQuest = nbQuest;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nbQuest=" + nbQuest +
                ", note=" + note +
                '}';
    }
}
