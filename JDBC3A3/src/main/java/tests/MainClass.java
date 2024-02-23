package tests;

import entities.Offre;
import entities.Personne;
import services.OffreServices;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {

     //MyConnection mc = new MyConnection();

        Offre offre = new Offre(1,"pack innn","disponible","3emme",27.3f);
        Offre offre2 = new Offre(5,"pack maths","non disponible","4emme",37.3f);

        OffreServices offreServices = new OffreServices();
        offreServices.addEntity2(offre2);
        //offreServices.updateEntity(offre);
        //offreServices.deleteEntity(offre2);
        System.out.println(offreServices.getAllData());

    }
}
