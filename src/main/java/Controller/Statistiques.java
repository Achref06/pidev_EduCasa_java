package Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Utils.MyConnection;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import Entities.Reponses;  // Importez votre classe Reponses ici
import Services.ReponsesService;  // Importez votre service ReponsesService ici

public class Statistiques {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart chart;

    // Remplacez ces valeurs simulées par la logique de votre application
    private int numberOfCorrectAnswers = 0;
    private int numberOfIncorrectAnswers = 0;

    @FXML
    void initialize() {
      /*  // Récupérer les données de la table reponses (exemple)
        ReponsesService reponsesService = new ReponsesService(MyConnection.getInstance().getCnx());
        List<Reponses> reponsesList = reponsesService.getAllReponses();  // Remplacez cela par votre propre méthode de récupération des réponses

        // Calculer le nombre de réponses correctes et incorrectes
        for (Reponses reponse : reponsesList) {
            if (reponse.isStatut()) {
                numberOfCorrectAnswers++;
            } else {
                numberOfIncorrectAnswers++;
            }
        }

        // Création des données du PieChart
        PieChart.Data correctData = new PieChart.Data("Correctes", numberOfCorrectAnswers);
        PieChart.Data incorrectData = new PieChart.Data("Incorrectes", numberOfIncorrectAnswers);

        // Ajout des données au PieChart
        chart.getData().addAll(correctData, incorrectData);

        // Configuration du titre
        chart.setTitle("Statistiques des réponses");
    */
    }
}
