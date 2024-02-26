package services;

import entities.User;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class UserServices implements IServices<User> {

    @Override
    public void addEntity(User user) {
        String requete = "INSERT INTO user (nom,prenom,mdp,email,role,specialite,niveau) VALUES ( ?,?,?,?,?,?,? )";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getMdp());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getRole());
            pst.setString(6, user.getSpecialite());
            pst.setString(7, user.getNiveau());
            pst.executeUpdate();
            System.out.println("User added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(User user) {

    }

    @Override
    public void deleteEntity(User user) {

        String requete = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, user.getId());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with ID " + user.getId() + " deleted successfully");
            } else {
                System.out.println("No User found with ID " + user.getId());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> getAllData() {

        List<User> users = new ArrayList<>();
        String requete = "SELECT * FROM user";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("ID"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mdp"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("specialite"),
                        rs.getString("niveau")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public User getUserByEmail(String email) {
        User user = null;
        String requete = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mdp"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("specialite"),
                        rs.getString("niveau")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    }
