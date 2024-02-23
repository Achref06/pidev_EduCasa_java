package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormulaireDAO {
    // Method to get the ID from the database based on a condition
    public int getIdFromDatabase(String condition) {
        int id1 = -1; // Default value if ID not found or an error occurs

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String sql = "SELECT id FROM formulaire WHERE condition_column = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, condition);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        id1 = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception properly in your application
        }

        return id1;
    }
}
