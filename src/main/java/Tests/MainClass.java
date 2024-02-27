package Tests;

import Entities.Categorie;
import Entities.Cours;
import Entities.Formation;
import Services.CategorieServices;
import Services.CoursServices;
import Services.FormationServices;
import Utils.Myconnection;
import java.sql.Connection;

import java.time.LocalDate;

public class MainClass {
    public static void main(String[] args) {
        Myconnection mc =  Myconnection.getInstance();

        Formation formation = new Formation(1, "Formation en Java", "20H", "Avancé",30);
        //Formation F4=new Formation(3,"Formation en angalis",20,"moyen","15/08/2024","23/03/2025");
        Categorie categorie=new Categorie(1,"Science");
        Cours cour=new Cours("proba");


        // Affichage des détails de la formation
        FormationServices fs =new FormationServices();
        CategorieServices cs=new CategorieServices();
        CoursServices CCS=new CoursServices();

        //CCS.ajouterEntity(cour);
        //cs.ajouterEntity(categorie);
        //fs.ajouterEntity(formation);
        //fs.ajouterEntity(F2);
        //System.out.println(fs.getAllData());
        //fs.deleteEntity(F1);
        //System.out.println(fs.getAllData());
        //fs.deleteEntity(formation);
        System.out.println(cs.getAllData());
        System.out.println(CCS.getAllData());
        System.out.println(fs.getAllData());
        //fs.updateEntity(F1);






    }
}
