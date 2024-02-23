package tn.esprit.entities;

public class traitement {
    private static int idT;
    private static int numR;

    private static String reponse;


    public traitement() {};
    public traitement( String reponse, int numR) {
        this.idT =idT;
        this.reponse =reponse;
    }
    public traitement( int numR,String reponse) {
       //this.idT=idT;
        this.numR=numR;
        this.reponse =reponse;


    }

    public traitement(String text, String text1, String text2) {
    }

    public traitement(String text, String text1) {
    }

    public traitement(String text) {
    }


    // public traitement(int idt, String reponse){};


    @Override
    public String toString() {
        return "traitement{" +
                ", idt='" + idT +
                ", reponse='" + reponse +
                ", id reclamation='" +numR +
                '}';
    }

    public static int getIdT() {
        return idT;
    }

    public static void setIdT(int idt) {
        traitement.idT = idT;
    }

    public static String getReponse() {
        return reponse;
    }

    public static void setReponse(String reponse) {
        traitement.reponse = reponse;
    }

    public static int getNumR() {
        return numR;
    }

    public static void setNumR(int numR) {
        traitement.numR = numR;
    }
}


