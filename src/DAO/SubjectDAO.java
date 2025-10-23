/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Subject;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class SubjectDAO {
    
    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY subject_id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectId(rs.getInt("subject_id"));
                s.setSubjectName(rs.getString("subject_name"));
                list.add(s);
            }
        }
        return list;
    }
    
    public List<Subject> getSubjectsByGradeLevel(int gradeLevelId) throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = """
            SELECT s.subject_id, s.subject_name
            FROM curriculum c
            JOIN subjects s ON c.subject_id = s.subject_id
            WHERE c.grade_level_id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gradeLevelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject s = new Subject();
                    s.setSubjectId(rs.getInt("subject_id"));
                    s.setSubjectName(rs.getString("subject_name"));
                    list.add(s);
                }
            }
        }
        return list;
    }
    
    public boolean addNewSubject(String subjectName) throws SQLException {
        String check = "SELECT * FROM subjects WHERE subject_name = ?";
        String insert = "INSERT INTO subjects (subject_name) VALUES (?)"; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement chk = conn.prepareStatement(check)) {
            chk.setString(1, subjectName);
            ResultSet rs = chk.executeQuery();
            if (rs.next()) return false;

            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setString(1, subjectName);
            ps.executeUpdate();
            return true;
        }
    }
    
    public boolean updateSubject(int subjectId, String newName) throws SQLException {
        String check = "SELECT * FROM subjects WHERE subject_name = ? AND subject_id <> ?";
        String update = "UPDATE subjects SET subject_name = ? WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement chk = conn.prepareStatement(check)) {
            chk.setString(1, newName);
            chk.setInt(2, subjectId);
            ResultSet rs = chk.executeQuery();
            if (rs.next()) return false; // name already exists

            PreparedStatement ps = conn.prepareStatement(update);
            ps.setString(1, newName);
            ps.setInt(2, subjectId);
            ps.executeUpdate();
            return true;
        }
    }
    
    public boolean deleteSubject(int subjectId) throws SQLException {
        String sql = "DELETE FROM subjects WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
}
