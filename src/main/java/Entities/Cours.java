package Entities;

public class Cours {

    private int idCour;
    private String nomCour;

    public Cours() {
    }

    public Cours(int idCour, String nomCour) {
        this.idCour = idCour;
        this.nomCour = nomCour;
    }

    public Cours(String nomCour) {
        this.nomCour = nomCour;
    }

    public int getIdCour() {
        return idCour;
    }

    public void setIdCour(int idCour) {
        this.idCour = idCour;
    }

    public String getNomCour() {
        return nomCour;
    }

    public void setNomCour(String nomCour) {
        this.nomCour = nomCour;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "idCour=" + idCour +
                ", nomCour='" + nomCour + '\'' +
                '}';
    }
}
