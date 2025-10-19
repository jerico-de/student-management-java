/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Section;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class SectionDAO {
    
    private final GradeLevelDAO gradeLevelDAO = new GradeLevelDAO();

    public List<Section> getAllSectionsWithGradeNames() throws SQLException {
        List<Section> list = new ArrayList<>();
        String sql = "SELECT s.section_id, s.section_name, g.grade_name, s.grade_level_id " +
                     "FROM sections s JOIN grade_levels g ON s.grade_level_id = g.grade_level_id " +
                     "ORDER BY s.section_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Section s = new Section();
                s.setId(rs.getInt("section_id"));
                s.setName(rs.getString("section_name"));
                s.setName(rs.getString("grade_name"));
                s.setGradeLevelId(rs.getInt("grade_level_id"));
                list.add(s);
            }
        }
        return list;
    }

    public Section getSectionById(int sectionId) throws SQLException {
        String sql = "SELECT * FROM sections WHERE section_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sectionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Section(
                        rs.getInt("section_id"),
                        rs.getInt("grade_level_id"),
                        rs.getString("section_name")
                    );
                }
            }
        }
        return null;
    }

    public void addSection(String sectionName, int gradeLevelId) throws SQLException {
        String sql = "INSERT INTO sections (section_name, grade_level_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sectionName);
            stmt.setInt(2, gradeLevelId);
            stmt.executeUpdate();
        }
    }

    public void updateSection(int sectionId, String sectionName, int gradeLevelId) throws SQLException {
        String sql = "UPDATE sections SET section_name = ?, grade_level_id = ? WHERE section_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sectionName);
            ps.setInt(2, gradeLevelId);
            ps.setInt(3, sectionId);
            ps.executeUpdate();
        }
    }

    public void deleteSection(int sectionId) throws SQLException {
        String sql = "DELETE FROM sections WHERE section_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ps.executeUpdate();
        }
    }
    
    public boolean sectionExists(String sectionName, int gradeLevelId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM sections WHERE section_name = ? AND grade_level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sectionName);
            stmt.setInt(2, gradeLevelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public List<Object[]> getAllSections() throws SQLException {
        List<Object[]> sections = new ArrayList<>();
        String sql = "SELECT s.section_id, g.grade_name, s.section_name " +
                    "FROM sections s " +
                    "INNER JOIN grade_levels g ON s.grade_level_id = g.grade_level_id " +
                    "ORDER BY CAST(SUBSTRING(g.grade_name, 7) AS UNSIGNED), s.section_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                sections.add(new Object[]{
                        rs.getInt("section_id"),
                        rs.getString("grade_name"),
                        rs.getString("section_name")
                });
            }
        }
        return sections;
    }
}
