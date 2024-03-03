package Services;

import Entities.Questions;
import Entities.Quiz;
import Entities.Reponses;
import Interfaces.IServices;
import Utils.MyConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
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
        if (quizExists(questions.getIdquiz())) {
            String requete = "INSERT INTO question (idQuiz, quest, listeRep) VALUES (?, ?, ?)";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, questions.getIdquiz());
                pst.setString(2, questions.getQuest());

                // Ajout de la liste de réponses après insertion de la question
                String listeRepAsJson = new Gson().toJson(questions.getListeRep(), new TypeToken<List<Reponses>>() {}.getType());

                pst.setString(3, listeRepAsJson);

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    // Récupération de l'ID généré pour la question
                    try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            questions.setId(generatedKeys.getInt(1));

                            // Ajout des réponses à la question après avoir récupéré l'ID
                            addReponsesToQuestion(questions);
                        } else {
                            throw new SQLException("Échec de récupération de l'ID de la question");
                        }
                    }

                    System.out.println("Question ajoutée avec succès!");
                } else {
                    System.out.println("Échec de l'ajout de la question");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Quiz inexistant");
        }
    }

    private void addReponsesToQuestion(Questions questions) {
        List<Reponses> listeRep = questions.getListeRep();
        if (listeRep != null && !listeRep.isEmpty()) {
            ReponsesService reponsesService = new ReponsesService(MyConnection.getInstance().getCnx());
            for (Reponses reponses : listeRep) {
                reponses.setIdq(questions.getId()); // Assigner l'ID de la question à chaque réponse
                reponsesService.addEntity(reponses);
            }
        }
    }

    @Override
    public void updateEntity(Questions questions) {
        if(quizExists(questions.getId())) {
            String requete = "UPDATE question SET listeRep = ? WHERE id = ?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

                // Convertir la liste de réponses en JSON
                String listeRepAsJson = new Gson().toJson(questions.getListeRep());

                pst.setString(1, listeRepAsJson);
                pst.setInt(2, questions.getId());

                pst.executeUpdate();
                System.out.println("Liste de réponses mise à jour pour la question avec ID " + questions.getId());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteEntity(Questions questions) {
        deleteReponsesByQuestionId(questions.getId());
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
        Quiz q=new Quiz();
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
                System.out.println("listeRepAsJson: "+ listeRepAsJson);
                List<Reponses> listeReponses= new Gson().fromJson(listeRepAsJson,new TypeToken<List<Reponses>>(){}.getType());
                questions1.setListeRep(listeReponses);
                questions.add(questions1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return questions;
    }

       /* public List<Questions> getQuestionsByQuizId(int quizId) {
            List<Questions> questionsList = new ArrayList<>();
            String requete = "SELECT * FROM question WHERE idquiz = ?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, quizId);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Questions q = new Questions();
                    q.setId(rs.getInt("id"));
                    q.setIdquiz(rs.getInt("idquiz"));
                    q.setQuest(rs.getString("quest"));
                    // ... autres attributs
                    questionsList.add(q);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return questionsList;
        }
*/



    public List<Questions> getQuestionsByQuizId(int quizId) {

        List<Questions> questionsList = new ArrayList<>();
        String requete = "SELECT question.*, reponse.* FROM question LEFT JOIN reponse ON question.id = reponse.idq WHERE question.idquiz = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();

           while (rs.next()){
                Questions questions1=new Questions();
                questions1.setId(rs.getInt("id"));
                questions1.setIdquiz(rs.getInt("idquiz"));
                questions1.setQuest(rs.getString("quest"));
                String listeRepAsJson=rs.getString("listeRep");
                System.out.println("listeRepAsJson: "+ listeRepAsJson);
                List<Reponses> listeReponses= new Gson().fromJson(listeRepAsJson,new TypeToken<List<Reponses>>(){}.getType());
                questions1.setListeRep(listeReponses);
                questionsList.add(questions1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questionsList;
    }

}
