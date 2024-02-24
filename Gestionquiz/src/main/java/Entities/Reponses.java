package Entities;

public class Reponses {
    private int id;
    private int idq;
    private String rep;
    private boolean statut;

    public Reponses(String rep, boolean statut) {
        this.rep = rep;
        this.statut = statut;
    }

    public Reponses(int id, int idq, String rep, boolean statut) {
        this.id = id;
        this.idq = idq;
        this.rep = rep;
        this.statut = statut;
    }

    public Reponses(int idq, String rep, boolean statut) {
        this.idq = idq;
        this.rep = rep;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Reponses{" +
                "id=" + id +
                ", idq=" + idq +
                ", rep='" + rep + '\'' +
                ", statut=" + statut +
                '}';
    }
}
