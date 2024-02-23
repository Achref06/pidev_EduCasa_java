package tn.esprit.entities;

import java.sql.Date;
import java.time.LocalDate;


public class reclamation {
    private static int numR;
    private static String description;
    private static String type ;
    private static LocalDate date ;


    public reclamation(String text) {}

    //affichage 
    public reclamation(int numR ,String description, String type, LocalDate date) {
        this.numR = numR;
        this.description = description;
        this.type = type;
        this.date = date;

    }
    //le numR est auto-incrementé
    public reclamation(String description, String type,LocalDate date ) {
        this.description = description;
        this.type = type;
        this.date = date;

    }

    public reclamation(String text, String text1, String text2) {
    }


    @Override
    public String toString() {
        return "reclamation{" +
                ", numR='" + numR +
                ", description='" + description +
                ", type='" + type +
                ", date='" + date +
                '}';
    }
    public static int getNumR() {
        return numR;
    }

    public void setNumR(int numR) {
        this.numR = numR;
    }
    public static String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
