package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Utils.MyConnection;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author spangsberg
 */
public class Statistiques implements Initializable {

    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(buildPieChart());
    }

    @FXML
    private void handleShowBarChart() {

        borderPane.setCenter(buildBarChart());
    }

    @FXML
    private void handleShowPieChart() {
        borderPane.setCenter(buildPieChart());
    }

    public static BarChart<String, Number> buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Niveau de Formation");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de Formations");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Nombre de Formations par Niveau");
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery("SELECT nbQuest, COUNT(*) AS formationCount FROM quiz GROUP BY nbQuest");
            while (rs.next()) {
                String niveauFormation = rs.getString("nbQuest");
                long formationCount = rs.getLong("formationCount");
                dataSeries.getData().add(new XYChart.Data<>(niveauFormation,formationCount));

                // Here you can store the data in a map or list
                // For simplicity, let's just print the data for now
                System.out.println("nb de questions: " + niveauFormation + ", Formation Count: " + formationCount);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }



        // 3. Process the results of the query

        barChart.getData().add(dataSeries);
        return barChart;
    }

    public static PieChart buildPieChart() {
        // Create an observable list to hold pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Execute SQL query to retrieve data from the database
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery("SELECT nbQuest, COUNT(*) AS formationCount FROM quiz GROUP BY nbQuest");
            while (rs.next()) {
                String niveauFormation = rs.getString("nbQuest");
                long formationCount = rs.getLong("formationCount");
                // Add data to the pie chart observable list
                pieChartData.add(new PieChart.Data(niveauFormation, formationCount));
                // Print the data for verification
                System.out.println("Niveau Formation: " + niveauFormation + ", Formation Count: " + formationCount);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the pie chart with the populated data
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Nombre de questions par quiz");
        pieChart.setLegendVisible(false); // Optional: Hide legend

        return pieChart;
    }

    /**
     *
     */
    @FXML
    private void handleClose() {
        System.exit(0);
    }


    /**
     *
     */
    @FXML
    private void handleUpdatePieData() {
        Node node = borderPane.getCenter();

        if (node instanceof PieChart)
        {
            PieChart pc = (PieChart) node;
            double value = pc.getData().get(2).getPieValue();
            pc.getData().get(2).setPieValue(value * 1.10);
            createToolTips(pc);
        }
    }


    /**
     * Creates tooltips for all data entries
     * @param pc
     */
    private void createToolTips(PieChart pc) {

        for (PieChart.Data data: pc.getData()) {
            String msg = Double.toString(data.getPieValue());

            Tooltip tp = new Tooltip(msg);
            tp.setShowDelay(Duration.seconds(0));

            Tooltip.install(data.getNode(), tp);

            //update tooltip data when changed
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
            {
                tp.setText(newValue.toString());
            });
        }
    }
}