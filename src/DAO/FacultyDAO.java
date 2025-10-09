/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Faculty;
import Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class FacultyDAO {
    
    public boolean addFaculty(Faculty faculty) {
        String sql = "INSERT INTO faculty (user_id, first_name, last_name, gender) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, faculty.getUserId());
            stmt.setString(2, faculty.getFirstName());
            stmt.setString(3, faculty.getLastName());
            stmt.setString(4, faculty.getGender());
            
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding faculty: " + e.getMessage());
            return false;
        }
    }

    public Faculty getFacultyById(int facultyId) {
        String sql = "SELECT * FROM faculty WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, facultyId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching faculty: " + e.getMessage());
        }
        return null;
    }
    
    public List<Faculty> searchFaculty(String keyword) {
        List<Faculty> facultyList = new ArrayList<>();
        String sql = "SELECT * FROM faculty WHERE faculty_id LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                facultyList.add(new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching faculty: " + e.getMessage());
        }
        return facultyList;
    }

    public List<Faculty> getAllFaculty() {
        List<Faculty> facultyList = new ArrayList<>();
        String sql = "SELECT * FROM faculty";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                facultyList.add(new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching faculty list: " + e.getMessage());
        }
        return facultyList;
    }

    public boolean updateFaculty(Faculty faculty) {
        String sql = "UPDATE faculty SET first_name = ?, last_name = ?, gender = ?  WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, faculty.getFirstName());
            stmt.setString(2, faculty.getLastName());
            stmt.setString(3, faculty.getGender());
            stmt.setInt(4, faculty.getFacultyId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating faculty: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFaculty(int facultyId) {
        String sql = "DELETE FROM faculty WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, facultyId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting faculty: " + e.getMessage());
            return false;
        }
    }
}
