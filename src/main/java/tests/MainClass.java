package tests;

import entities.User;
import services.UserServices;
import utils.MyConnection;

import java.util.Date;

public class MainClass {
    public static void main(String[] args) {
         MyConnection mc = new MyConnection();
        Date d=new Date(2002,05,06);

    User u = new User("Saadaoui","Achref","21-01-2002","augyjdfvtyevfd","bouliss","jadnhbei","finhezjb");
        UserServices us = new UserServices();
        us.addEntity(u);


    }
}
