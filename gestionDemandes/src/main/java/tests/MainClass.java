package tests;

import entities.Form;
import services.FormServices;
import services.InfosServices;

import java.time.LocalDate;




public class MainClass {
    public static void main(String[] args) {
         //    MyConnection mc = new MyConnection();
        Form fo = new Form();
        LocalDate date = LocalDate.of(2024, 2, 07);
        String statut = "non traité";
        double qualite = 4.5; // Noté sur 5
        double interaction = 4.0; // Noté sur 5
        double preparation = 4.2; // Noté sur 5
        double evaluations = 3.8; // Noté sur 5

        // Calculer le rating
    //    double rating = calculerRating(qualite, interaction, preparation, evaluations);
     //   System.out.println("Rating Global du Professeur : " + rating);
      //  InfosServices infosServices = new InfosServices();
     //   int id = infosServices.getLastInsertedId();
     //   System.out.println(id);
     //   int idFormulaire = Form.getIdFormulaire(date, statut);
    //    System.out.println("ID formulaire pour date et statut : " + idFormulaire);
     //   FormServices fss = new FormServices();
        //  fs.addEntity(f);
        //  fs.addEntity(f1);
        //  fs.updateEntity(f1);
        //  fs.deleteEntity(f1);

       // fss.deleteEntity(fo);

        //  ds.addEntity(d);
        //  ds.addEntity(d1);
        //  ds.updateEntity(d1);
        //  ds.deleteEntity(d1);

       // System.out.println(fs.getAllData());
       // System.out.println(ds.getAllData());
    }
}
