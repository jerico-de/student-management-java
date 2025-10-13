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
    
    public boolean enrollStudent(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollment (student_id, grade_level_id, section_id, curriculum_id, year_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getGradeLevelId());
            stmt.setInt(3, enrollment.getSectionId());
            if (enrollment.getCurriculumId() != null) {
                stmt.setInt(4, enrollment.getCurriculumId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setInt(5, enrollment.getYearId());
            stmt.setString(6, "Enrolled");
            stmt.executeUpdate();
        }
        return false;
    }
    
    public void unenrollStudent(int studentId, int academicYearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'not enrolled' WHERE student_id = ? AND academic_year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, academicYearId);
            stmt.executeUpdate();
        }
    }

    public List<Enrollment> getEnrollmentsByYear(int yearId) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean bulkUpdateStatusByYear(int yearId, String status) {
        String sql = "UPDATE enrollment SET status = ? WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, yearId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void bulkUnenrollByAcademicYear(int academicYearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'not enrolled' WHERE academic_year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, academicYearId);
            stmt.executeUpdate();
        }
    }
    
    public List<Integer> getEnrolledStudents(int academicYearId) throws SQLException {
        List<Integer> studentIds = new ArrayList<>();
        String sql = "SELECT student_id FROM enrollment WHERE academic_year_id = ? AND status = 'enrolled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, academicYearId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentIds.add(rs.getInt("student_id"));
            }
        }
        return studentIds;
    }
    
    public void updateEnrollmentStatus(int enrollmentId, String newStatus) throws SQLException {
        String sql = "UPDATE enrollment SET status = ? WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, enrollmentId);
            stmt.executeUpdate();
        }
    }
    
    public void bulkSetNotEnrolled(int yearId) throws SQLException {
        String sql = "UPDATE enrollment SET status = 'Not Enrolled' WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, yearId);
            stmt.executeUpdate();
        }
    }
}
