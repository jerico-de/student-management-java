/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.GradeLevel;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class GradeLevelDAO {
    
    public List<GradeLevel> getAllGradeLevels() throws SQLException {
        List<GradeLevel> list = new ArrayList<>();
        String sql = "SELECT grade_level_id, grade_name FROM grade_levels ORDER BY CAST(SUBSTRING(grade_name, 7) AS UNSIGNED)";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GradeLevel gradeLevel = new GradeLevel();
                gradeLevel.setGradeLevelId(rs.getInt("grade_level_id"));
                gradeLevel.setGradeLevelName(rs.getString("grade_name"));
                list.add(gradeLevel);
            }
        }
        return list;
    }

    public GradeLevel getGradeLevelById(int id) throws SQLException {
        String sql = "SELECT * FROM grade_levels WHERE grade_level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GradeLevel(rs.getInt("grade_level_id"), rs.getString("grade_name"));
                }
            }
        }
        return null;
    }

    public int getGradeLevelIdByName(String gradeName) throws SQLException {
        String sql = "SELECT grade_level_id FROM grade_levels WHERE grade_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, gradeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("grade_level_id");
                }
            }
        }
        throw new SQLException("Grade level not found: " + gradeName);
    }

    public void addGradeLevel(String gradeName) throws SQLException {
        String sql = "INSERT INTO grade_levels (grade_name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gradeName);
            ps.executeUpdate();
        }
    }

    public void updateGradeLevel(int id, String newName) throws SQLException {
        String sql = "UPDATE grade_levels SET grade_name = ? WHERE grade_level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void deleteGradeLevel(int id) throws SQLException {
        String sql = "DELETE FROM grade_levels WHERE grade_level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
