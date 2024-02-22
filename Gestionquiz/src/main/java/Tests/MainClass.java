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



        QuestionsServices qs=new QuestionsServices();
        Questions q= new Questions(4,"Qu'est ce qu'une methode abstraite",null);
        qs.addEntity(q);

        ReponsesService rs=new ReponsesService();
        Reponses r=new Reponses(1,"methode non modifiable",false);
        rs.addEntity(r);

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


*/

    }
}
