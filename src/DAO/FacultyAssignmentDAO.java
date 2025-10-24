/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Faculty;
import Model.FacultyAssignment;
import Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class FacultyAssignmentDAO {
    
    public boolean assignFacultyToSubject(int facultyId, int subjectId, int sectionId, boolean isAdviser) throws SQLException {
        String check = "SELECT * FROM faculty_subjects WHERE faculty_id=? AND subject_id=? AND section_id=?";
        String insert = "INSERT INTO faculty_subjects (faculty_id, subject_id, section_id) VALUES (?, ?, ?)";
        String setAdviser = "UPDATE faculty SET advisory_section_id=? WHERE faculty_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(check)) {

            psCheck.setInt(1, facultyId);
            psCheck.setInt(2, subjectId);
            psCheck.setInt(3, sectionId);

            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) return false;

            try (PreparedStatement psInsert = conn.prepareStatement(insert)) {
                psInsert.setInt(1, facultyId);
                psInsert.setInt(2, subjectId);
                psInsert.setInt(3, sectionId);
                psInsert.executeUpdate();
            }

            if (isAdviser) {
                try (PreparedStatement psAdviser = conn.prepareStatement(setAdviser)) {
                    psAdviser.setInt(1, sectionId);
                    psAdviser.setInt(2, facultyId);
                    psAdviser.executeUpdate();
                }
            }

            return true;
        }
    }

    public List<FacultyAssignment> getAllAssignments() throws SQLException {
        String sql = """
                    SELECT fs.faculty_subject_id, 
                           CONCAT(f.last_name, ', ', f.first_name) AS faculty_name,
                           s.subject_name,
                           gl.grade_name,
                           sec.section_name,
                           CASE 
                               WHEN f.advisory_section_id = fs.section_id THEN 1 
                               ELSE 0 
                           END AS is_adviser
                    FROM faculty_subjects fs
                    JOIN faculty f ON fs.faculty_id = f.faculty_id
                    JOIN subjects s ON fs.subject_id = s.subject_id
                    JOIN sections sec ON fs.section_id = sec.section_id
                    JOIN grade_levels gl ON sec.grade_level_id = gl.grade_level_id
                    ORDER BY f.last_name, s.subject_name
                    """;

        List<FacultyAssignment> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FacultyAssignment fa = new FacultyAssignment();
                fa.setFacultyName(rs.getString("faculty_name"));
                fa.setSubjectName(rs.getString("subject_name"));
                fa.setGradeLevelName (rs.getString("grade_name"));
                fa.setSectionName(rs.getString("section_name"));
                fa.setAdviser(rs.getBoolean("is_adviser"));
                list.add(fa);
            }
        }
        return list;
    }

    public void removeAssignment(String facultyName, String subjectName, String sectionName) throws SQLException {
        String sql = """
                DELETE fs FROM faculty_subjects fs
                JOIN faculty f ON fs.faculty_id = f.faculty_id
                JOIN subjects s ON fs.subject_id = s.subject_id
                JOIN sections sec ON fs.section_id = sec.section_id
                WHERE CONCAT(f.last_name, ', ', f.first_name) = ? 
                  AND s.subject_name = ? 
                  AND sec.section_name = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facultyName);
            ps.setString(2, subjectName);
            ps.setString(3, sectionName);
            ps.executeUpdate();
        }
    }
    
    public List<FacultyAssignment> searchAssignments(String keyword, String subjectFilter, String gradeFilter, String sectionFilter, String adviserFilter) throws SQLException {
        StringBuilder sql = new StringBuilder("""
                        SELECT fs.faculty_subject_id, 
                               CONCAT(f.last_name, ', ', f.first_name) AS faculty_name,
                               s.subject_name,
                               gl.grade_name,
                               sec.section_name,
                               CASE 
                                   WHEN f.advisory_section_id = fs.section_id THEN 1 
                                   ELSE 0 
                               END AS is_adviser
                        FROM faculty_subjects fs
                        JOIN faculty f ON fs.faculty_id = f.faculty_id
                        JOIN subjects s ON fs.subject_id = s.subject_id
                        JOIN sections sec ON fs.section_id = sec.section_id
                        JOIN grade_levels gl ON sec.grade_level_id = gl.grade_level_id
                        WHERE 1=1
                    """);

        
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (f.first_name LIKE ? OR f.last_name LIKE ? OR s.subject_name LIKE ? OR gl.grade_name LIKE ? OR sec.section_name LIKE ?)");
        }
        if (subjectFilter != null && !subjectFilter.equals("All")) {
            sql.append(" AND s.subject_name = ?");
        }
        if (gradeFilter != null && !gradeFilter.equals("All")) {
            sql.append("AND gl.grade_name = ?");
        }
        if (sectionFilter != null && !sectionFilter.equals("All")) {
            sql.append(" AND sec.section_name = ?");
        }
        if (adviserFilter != null && !adviserFilter.equals("All")) {
            if (adviserFilter.equals("Yes")) sql.append(" AND f.advisory_section_id = fs.section_id");
            else if (adviserFilter.equals("No")) sql.append(" AND (f.advisory_section_id IS NULL OR f.advisory_section_id <> fs.section_id)");
        }

        sql.append(" ORDER BY f.last_name, s.subject_name");

        List<FacultyAssignment> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String like = "%" + keyword + "%";
                ps.setString(paramIndex++, like);
                ps.setString(paramIndex++, like);
                ps.setString(paramIndex++, like);
                ps.setString(paramIndex++, like);
                ps.setString(paramIndex++, like);
            }
            if (subjectFilter != null && !subjectFilter.equals("All")) {
                ps.setString(paramIndex++, subjectFilter);
            }
            if (gradeFilter != null && !gradeFilter.equals("All")) {
                ps.setString(paramIndex++, gradeFilter);
            }
            if (sectionFilter != null && !sectionFilter.equals("All")) {
                ps.setString(paramIndex++, sectionFilter);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FacultyAssignment fa = new FacultyAssignment();
                fa.setFacultyName(rs.getString("faculty_name"));
                fa.setSubjectName(rs.getString("subject_name"));
                fa.setGradeLevelName(rs.getString("grade_name"));
                fa.setSectionName(rs.getString("section_name"));
                fa.setAdviser(rs.getBoolean("is_adviser"));
                list.add(fa);
            }
        }
        return list;
    }
}
