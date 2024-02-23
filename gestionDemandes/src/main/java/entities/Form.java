package entities;

import java.time.LocalDate;

public class Form {
    private int id;
    private LocalDate date;
    private String statut;

    public Form(java.util.Date date, String selectedStatut) {
    }

    public Form(int id, LocalDate date, String statut) {
        this.id = id;
        this.date = date;
        this.statut = statut;
    }

    public Form(LocalDate date, String statut) {
        this.date = date;
        this.statut = statut;
    }

    public Form() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                '}';
    }
}
