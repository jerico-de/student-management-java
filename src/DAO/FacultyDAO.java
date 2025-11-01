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
    
    public boolean addFaculty(Faculty faculty) throws SQLException {
        String sql = "INSERT INTO faculty (user_id, first_name, last_name, middle_name, gender, birthdate, address, advisory_grade_level_id, advisory_section_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, faculty.getUserId());
            stmt.setString(2, faculty.getFirstName());
            stmt.setString(3, faculty.getLastName());
            stmt.setString(4, faculty.getMiddleName());
            stmt.setString(5, faculty.getGender());
            stmt.setObject(6, faculty.getBirthdate());
            stmt.setString(7, faculty.getAddress());
            stmt.setObject(8, faculty.getAdvisoryGradeLevelId());
            stmt.setObject(9, faculty.getAdvisorySectionId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateFaculty(Faculty faculty) throws SQLException {
        String sql = "UPDATE faculty SET first_name=?, last_name=?, middle_name=?, gender=?, birthdate=?, address=?, advisory_grade_level_id=?, advisory_section_id=? WHERE faculty_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, faculty.getFirstName());
            stmt.setString(2, faculty.getLastName());
            stmt.setString(3, faculty.getMiddleName());
            stmt.setString(4, faculty.getGender());
            stmt.setObject(5, faculty.getBirthdate());
            stmt.setString(6, faculty.getAddress());
            stmt.setObject(7, faculty.getAdvisoryGradeLevelId());
            stmt.setObject(8, faculty.getAdvisorySectionId());
            stmt.setInt(9, faculty.getFacultyId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteFaculty(int facultyId) throws SQLException {
        String sql = "DELETE FROM faculty WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, facultyId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Faculty> getAllFaculty() throws SQLException {
        List<Faculty> facultyList = new ArrayList<>();
        String sql = "SELECT * FROM faculty";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Faculty f = new Faculty();
                f.setFacultyId(rs.getInt("faculty_id"));
                f.setUserId(rs.getInt("user_id"));
                f.setFirstName(rs.getString("first_name"));
                f.setLastName(rs.getString("last_name"));
                f.setMiddleName(rs.getString("middle_name"));
                f.setGender(rs.getString("gender"));
                f.setBirthdate(rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null);
                f.setAddress(rs.getString("address"));
                f.setAdvisoryGradeLevelId((Integer) rs.getObject("advisory_grade_level_id"));
                f.setAdvisorySectionId((Integer) rs.getObject("advisory_section_id"));
                facultyList.add(f);
            }
        }
        return facultyList;
    }

    public Faculty getFacultyById(int id) throws SQLException {
        String sql = "SELECT * FROM faculty WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Faculty f = new Faculty();
                    f.setFacultyId(rs.getInt("faculty_id"));
                    f.setUserId(rs.getInt("user_id"));
                    f.setFirstName(rs.getString("first_name"));
                    f.setLastName(rs.getString("last_name"));
                    f.setMiddleName(rs.getString("middle_name"));
                    f.setGender(rs.getString("gender"));
                    f.setBirthdate(rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null);
                    f.setAddress(rs.getString("address"));
                    f.setAdvisoryGradeLevelId((Integer) rs.getObject("advisory_grade_level_id"));
                    f.setAdvisorySectionId((Integer) rs.getObject("advisory_section_id"));
                    return f;
                }
            }
        }
        return null;
    }
    
    public List<Faculty> searchFaculty(String keyword) {
        List<Faculty> list = new ArrayList<>();
        String sql = """
                    SELECT * FROM faculty 
                    WHERE first_name LIKE ? 
                       OR last_name LIKE ? 
                       OR middle_name LIKE ?
                    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculty f = new Faculty();
                f.setFacultyId(rs.getInt("faculty_id"));
                f.setUserId(rs.getInt("user_id"));
                f.setFirstName(rs.getString("first_name"));
                f.setLastName(rs.getString("last_name"));
                f.setMiddleName(rs.getString("middle_name"));
                f.setGender(rs.getString("gender"));
                f.setBirthdate(rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null);
                f.setAddress(rs.getString("address"));
                f.setAdvisoryGradeLevelId((Integer) rs.getObject("advisory_grade_level_id"));
                f.setAdvisorySectionId((Integer) rs.getObject("advisory_section_id"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void setAsAdviser(int facultyId, int sectionId) throws SQLException {
        String sql = "UPDATE faculty SET advisory_section_id=?, advisory_grade_level_id=(SELECT grade_level_id FROM sections WHERE section_id=?) WHERE faculty_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ps.setInt(2, sectionId);
            ps.setInt(3, facultyId);
            ps.executeUpdate();
        }
    }
    
    public Faculty getFacultyByUserId(int id) throws SQLException {
        String sql = "SELECT * FROM faculty WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Faculty f = new Faculty();
                    f.setFacultyId(rs.getInt("faculty_id"));
                    f.setUserId(rs.getInt("user_id"));
                    f.setFirstName(rs.getString("first_name"));
                    f.setLastName(rs.getString("last_name"));
                    f.setMiddleName(rs.getString("middle_name"));
                    f.setGender(rs.getString("gender"));
                    f.setBirthdate(rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null);
                    f.setAddress(rs.getString("address"));
                    f.setAdvisoryGradeLevelId((Integer) rs.getObject("advisory_grade_level_id"));
                    f.setAdvisorySectionId((Integer) rs.getObject("advisory_section_id"));
                    return f;
                }
            }
        }
        return null;
    }
    
    public int getTotalFaculty() throws SQLException {
        String sql = """
                     SELECT COUNT(DISTINCT f.faculty_id) AS total
                                 FROM faculty f
                                 JOIN users u ON f.user_id = u.user_id
                                 WHERE u.status = 'ACTIVE'
                     """;
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareCall(sql);
            ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
