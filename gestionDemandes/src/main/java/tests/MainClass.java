package tests;

import entities.Form;
import services.FormServices;

import java.time.LocalDate;


public class MainClass {
    public static void main(String[] args) {
         //    MyConnection mc = new MyConnection();
        Form fo = new Form(53,LocalDate.parse("2024-02-22"),"non traité");
        FormServices fss = new FormServices();
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
