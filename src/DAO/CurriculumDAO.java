/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Curriculum;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class CurriculumDAO {
    
    public List<Curriculum> getCurriculumByGradeLevel(int gradeLevelId) {
        List<Curriculum> list = new ArrayList<>();
        String sql = """
                SELECT c.curriculum_id, c.grade_level_id, c.subject_id, s.subject_name
                FROM curriculum c
                JOIN subjects s ON c.subject_id = s.subject_id
                WHERE c.grade_level_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gradeLevelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Curriculum c = new Curriculum();
                    c.setCurriculumId(rs.getInt("curriculum_id"));
                    c.setGradeLevelId(rs.getInt("grade_level_id"));
                    c.setSubjectId(rs.getInt("subject_id"));
                    c.setSubjectName(rs.getString("subject_name"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSubjectToCurriculum(int gradeLevelId, int subjectId) {
        String checkSql = "SELECT * FROM curriculum WHERE grade_level_id = ? AND subject_id = ?";
        String insertSql = "INSERT INTO curriculum (grade_level_id, subject_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, gradeLevelId);
            checkPs.setInt(2, subjectId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) return false; // already exists

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setInt(1, gradeLevelId);
                insertPs.setInt(2, subjectId);
                return insertPs.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSubjectFromCurriculum(int curriculumId) {
        String sql = "DELETE FROM curriculum WHERE curriculum_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, curriculumId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
