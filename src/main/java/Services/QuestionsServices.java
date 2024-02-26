package Services;

import Entities.Questions;
import Entities.Reponses;
import Interfaces.IServices;
import Utils.MyConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class QuestionsServices implements IServices<Questions> {
    public QuestionsServices(Connection cnx) {
    }

    private boolean quizExists(int idQuiz){
    try{
        String sql="SELECT id FROM quiz WHERE id = ?";
        try(PreparedStatement statement =MyConnection.getInstance().getCnx().prepareStatement(sql)) {
            statement.setInt(1,idQuiz);
            try(ResultSet resultSet = statement.executeQuery()){return resultSet.next();}
        }
        catch (SQLException e) {
e.printStackTrace();
        return false;}
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }
    @Override
    public void addEntity(Questions questions) {
        if(quizExists(questions.getIdquiz())) {
            String requete = "INSERT INTO question (idQuiz, quest, listeRep) VALUES (?, ?, ?)";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete,  Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, questions.getIdquiz());
                pst.setString(2, questions.getQuest());
String listeRepAsJson = new Gson().toJson(questions.getListeRep());
pst.setString(3,listeRepAsJson);
                pst.executeUpdate();
                System.out.println("question ajoute!");
                try(ResultSet generatedKeys = pst.getGeneratedKeys()){
                    if(generatedKeys.next()){questions.setId(generatedKeys.getInt(1));
                    }
                    else{
                        throw new SQLException("Echec de recuperation de l'id de la question");
                    }
                }
                addReponsesToQuestion(questions);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("Quiz inexistant");
        }

    }
    private void addReponsesToQuestion(Questions questions){
        List<Reponses> listeRep=questions.getListeRep();
        if(listeRep!=null && !listeRep.isEmpty()){
            ReponsesService reponsesService=new ReponsesService(MyConnection.getInstance().getCnx());
            for (Reponses reponses : listeRep){
                reponses.setIdq(questions.getId());
                reponsesService.addEntity(reponses);
            }
        }
    }

    @Override
    public void updateEntity(Questions questions) {

    }

    @Override
    public void deleteEntity(Questions questions) {
        //deleteReponsesByQuestionId(questions.getId());
        String requete="DELETE FROM question WHERE id=?";
        try {
            PreparedStatement pst=MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1,questions.getId());
            int rowsAffected=pst.executeUpdate();
            if (rowsAffected>0){
                System.out.println("question with ID"+questions.getId()+"supprime");
            }
            else{
                System.out.println("qestion non supprime");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    private void deleteReponsesByQuestionId(int id){
        String requete="DELETE FROM reponse WHERE id=?";
        try {
            PreparedStatement pst=MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Questions> getAllData() {
        ObservableList<Questions> questions= FXCollections.observableArrayList();
        String requete="SELECT * FROM question";
        try {
            PreparedStatement pst=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet resultSet= pst.executeQuery();
            while (resultSet.next()){
                Questions questions1=new Questions();
                questions1.setId(resultSet.getInt("id"));
                questions1.setIdquiz(resultSet.getInt("idquiz"));
                questions1.setQuest(resultSet.getString("quest"));
                String listeRepAsJson=resultSet.getString("listeRep");
                List<Reponses> listeReponses= new Gson().fromJson(listeRepAsJson,new TypeToken<List<Reponses>>(){}.getType());
                questions1.setListeRep(listeReponses);
                questions.add(questions1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return questions;
    }
}
