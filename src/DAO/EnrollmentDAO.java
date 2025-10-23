/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Enrollment;
import Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class EnrollmentDAO {
    
    public boolean enrollStudent(int studentId, int gradeLevelId, int sectionId, int yearId) throws SQLException {
        String sql = """
            INSERT INTO enrollment (student_id, grade_level_id, section_id, year_id, status)
            VALUES (?, ?, ?, ?, 'Enrolled')
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, gradeLevelId);
            ps.setInt(3, sectionId);
            ps.setInt(4, yearId);
            return ps.executeUpdate() > 0;
        }
    }

    public void unenrollStudent(int studentId, int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE student_id = ? AND year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, yearId);
            ps.executeUpdate();
        }
    }

    public List<Enrollment> getEnrollmentsByYear(int yearId) throws SQLException {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, yearId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Enrollment en = new Enrollment();
                    en.setEnrollmentId(rs.getInt("enrollment_id"));
                    en.setStudentId(rs.getInt("student_id"));
                    en.setGradeLevelId(rs.getInt("grade_level_id"));
                    en.setSectionId(rs.getInt("section_id"));
                    int curriculumId = rs.getInt("curriculum_id");
                    en.setCurriculumId(rs.wasNull() ? null : curriculumId);
                    en.setYearId(rs.getInt("year_id"));
                    en.setStatus(rs.getString("status"));

                    Timestamp ts = rs.getTimestamp("enrolled_date");
                    if (ts != null) {
                        en.setEnrolledDate(ts.toLocalDateTime());
                    }
                    list.add(en);
                }
            }
        }
        return list;
    }

    public boolean bulkUpdateStatusByYear(int yearId, String status) throws SQLException {
        String sql = "UPDATE enrollment SET status = ? WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, yearId);
            return ps.executeUpdate() > 0;
        }
    }

    public void bulkUnenrollByYear(int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, yearId);
            ps.executeUpdate();
        }
    }

    public List<Integer> getEnrolledStudents(int yearId) throws SQLException {
        List<Integer> studentIds = new ArrayList<>();
        String sql = "SELECT student_id FROM enrollment WHERE year_id = ? AND status = 'Enrolled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, yearId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    studentIds.add(rs.getInt("student_id"));
                }
            }
        }
        return studentIds;
    }

    public void updateEnrollmentStatus(int enrollmentId, String newStatus) throws SQLException {
        String sql = "UPDATE enrollment SET status = ? WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, enrollmentId);
            ps.executeUpdate();
        }
    }

    public void bulkSetNotEnrolled(int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, yearId);
            ps.executeUpdate();
        }
    }

    public Enrollment getEnrollmentByStudentId(int studentId) throws SQLException {
        String sql = """
                SELECT ay.year_label AS academic_year, s.section_name, g.grade_name
                FROM enrollment e
                JOIN sections s ON e.section_id = s.section_id
                JOIN academic_year ay ON e.year_id = ay.year_id
                JOIN grade_levels g ON e.grade_level_id = g.grade_level_id
                WHERE e.student_id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Enrollment(
                            studentId,
                            rs.getString("academic_year"),
                            rs.getString("section_name"),
                            rs.getString("grade_name")
                    );
                }
            }
        }
        return null;
    }
    
    public Enrollment getEnrollmentByStudentIdAndYear(int studentId, int yearId) throws SQLException {
    String sql = "SELECT s.section_name, g.grade_name " +
                 "FROM enrollment e " +
                 "JOIN sections s ON e.section_id = s.section_id " +
                 "JOIN grade_levels g ON e.grade_level_id = g.grade_level_id " +
                 "WHERE e.student_id = ? AND e.year_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, studentId);
        ps.setInt(2, yearId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Enrollment(
                        studentId,
                        rs.getString("grade_name"),
                        rs.getString("section_name")
                );
            }
        }
    }
    return null;
    }
    
    public String getStudentEnrollmentStatus(int studentId, int yearId) throws SQLException {
        String sql = "SELECT status FROM enrollment WHERE student_id = ? AND year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, yearId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        }
        return "Not Enrolled";
    }
    
    public String getStudentSection(int studentId, int yearId) throws SQLException {
        String sql = "SELECT s.section_name " +
                     "FROM enrollment e " +
                     "JOIN sections s ON e.section_id = s.section_id " +
                     "WHERE e.student_id = ? AND e.year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, yearId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("section_name");
                }
            }
        }
        return "Not Enrolled";
    }
    
    public boolean isStudentEnrolled(int studentId, int yearId) throws SQLException {
        String sql = "SELECT 1 FROM enrollment WHERE student_id = ? AND year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, yearId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    public String getStudentGradeLevel(int studentId, int yearId) throws SQLException {
        String sql = "SELECT g.grade_name " +
                     "FROM enrollment e " +
                     "JOIN grade_levels g ON e.grade_level_id = g.grade_level_id " +
                     "WHERE e.student_id = ? AND e.year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, yearId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("grade_name");
                }
            }
        }
        return "Not Enrolled";
    }
    
    public void markEnrollmentsInactiveForYear(int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, yearId);
            ps.executeUpdate();
        }
    }
    
    public boolean endAcademicYear(int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE year_id = ? AND status = 'Enrolled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, yearId);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean dropStudent(int studentId, int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Dropped' WHERE student_id = ? AND year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, yearId);
            return ps.executeUpdate() > 0;
        }
    }
}
