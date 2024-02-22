package Entities;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private int id;
    private int idquiz;
    private String quest;
    private List<Reponses> listeRep;

    public Questions(int idquiz, String quest) {
        this.idquiz = idquiz;
        this.quest = quest;
    }

    public Questions() {
        // Initialisation de la liste de réponses à une nouvelle liste vide par exemple
        this.listeRep = new ArrayList<>();
    }

    // Constructeur avec paramètres pour une initialisation complète
    public Questions(int idquiz, String quest, List<Reponses> listeRep) {
        this.idquiz = idquiz;
        this.quest = quest;
        this.listeRep = listeRep;
    }

    public Questions(int id,int idquiz, String quest, List<Reponses> listeRep) {
        this.id = id;
        this.idquiz = idquiz;
        this.quest = quest;
        this.listeRep = listeRep;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdquiz() {
        return idquiz;
    }

    public void setIdquiz(int idquiz) {
        this.idquiz = idquiz;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public List<Reponses> getListeRep() {
        return listeRep;
    }

    public void setListeRep(List<Reponses> listeRep) {
        this.listeRep = listeRep;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", idquiz=" + idquiz +
                ", quest='" + quest + '\'' +
                ", listeRep=" + listeRep +
                '}';
    }
}
