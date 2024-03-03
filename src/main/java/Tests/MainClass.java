package Tests;

import Entities.Questions;
import Entities.Quiz;
import Entities.Reponses;
import Services.QuestionsServices;
import Services.QuizServices;
import Services.ReponsesService;
import Utils.MyConnection;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
    /*  CE CODE EST FONCTIONNEL

   CRUD QUIZ

        MyConnection mc = new MyConnection();
        QuizServices qs=new QuizServices();
        Quiz q= new Quiz(1,"java",20,15);
        Quiz q2= new Quiz(2,"web",20,12);
        Quiz q3= new Quiz(3,"math",20,18);

        QuizServices qs=new QuizServices();
        qs.addEntity(q);
        qs.updateEntity(q);
        qs.deleteEntity(q);
        System.out.println(qs.getAllData());





     */
     /*
     AJOUT D'UNE QUESTION ET SES REPONSES
     CE CODE EST FONCTIONNEL
        Questions q=new Questions(4,"Quelle est la capital de la france");

        List<Reponses> listeRep =new ArrayList<>();
        listeRep.add(new Reponses("Paris",true));
        listeRep.add(new Reponses("Berlin",false));
        listeRep.add(new Reponses("Londres",false));
        listeRep.add(new Reponses("Madrid",false));
        q.setListeRep(listeRep);
        QuestionsServices questionsServices=new QuestionsServices(MyConnection.getInstance().getCnx());
        questionsServices.addEntity(q);


     SUPPRESSION QUESTION ET SES REPONSES
QuestionsServices questionsServices=new QuestionsServices(MyConnection.getInstance().getCnx());
        Questions q=new Questions(1,4);
        questionsServices.deleteEntity(q);


        AFFICHAGE QUESTION ET REPONSES
        QuestionsServices questionsServices=new QuestionsServices(MyConnection.getInstance().getCnx());
        System.out.println(questionsServices.getAllData());

*/



        Questions q=new Questions(17,"Qcasdmsdsdsdsdnt");

        List<Reponses> listeRep =new ArrayList<>();
        listeRep.add(new Reponses("Paq",true));
        listeRep.add(new Reponses("Bavcvaaq",false));
        listeRep.add(new Reponses("L  oaaq",false));
        listeRep.add(new Reponses("Mwseaaq",false));
        q.setListeRep(listeRep);
        QuestionsServices questionsServices=new QuestionsServices(MyConnection.getInstance().getCnx());
        questionsServices.addEntity(q);




    }
}
