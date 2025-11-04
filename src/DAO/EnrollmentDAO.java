/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Enrollment;
import Model.EnrollmentResult;
import Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 */
public class EnrollmentDAO {
    
    public boolean enrollStudent(int studentId, int gradeLevelId, int sectionId, int yearId) throws SQLException {
        if (isAlreadyEnrolled(studentId, yearId)) {
            return false;
        }
        
        String checkSQL = "SELECT enrollment_id, status FROM enrollment WHERE student_id = ? AND year_id = ?";
        String updateSQL = "UPDATE enrollment SET grade_level_id = ?, section_id = ?, status = 'Enrolled', enrolled_date = NOW() WHERE enrollment_id = ?";
        String insertSQL = "INSERT INTO enrollment (student_id, grade_level_id, section_id, year_id, status, enrolled_date) VALUES (?, ?, ?, ?, 'Enrolled', NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPS = conn.prepareStatement(checkSQL);
             PreparedStatement updatePS = conn.prepareStatement(updateSQL);
             PreparedStatement insertPS = conn.prepareStatement(insertSQL)) {

            checkPS.setInt(1, studentId);
            checkPS.setInt(2, yearId);
            ResultSet rs = checkPS.executeQuery();

            if (rs.next()) {
                int enrollmentId = rs.getInt("enrollment_id");
                String status = rs.getString("status");

                // If record exists but is "Not Enrolled", update it
                if ("Not Enrolled".equalsIgnoreCase(status)) {
                    updatePS.setInt(1, gradeLevelId);
                    updatePS.setInt(2, sectionId);
                    updatePS.setInt(3, enrollmentId);
                    return updatePS.executeUpdate() > 0;
                } else {
                    return false; // already enrolled
                }
            } else {
                // No record → insert new
                insertPS.setInt(1, studentId);
                insertPS.setInt(2, gradeLevelId);
                insertPS.setInt(3, sectionId);
                insertPS.setInt(4, yearId);
                return insertPS.executeUpdate() > 0;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public EnrollmentResult enrollMultipleStudents(List<Integer> studentIds, int gradeLevelId, int sectionId, int yearId) throws SQLException {
        String checkSQL = "SELECT enrollment_id, status FROM enrollment WHERE student_id = ? AND year_id = ?";
        String updateSQL = "UPDATE enrollment SET grade_level_id = ?, section_id = ?, status = 'Enrolled', enrolled_date = NOW() WHERE enrollment_id = ?";
        String insertSQL = "INSERT INTO enrollment (student_id, grade_level_id, section_id, year_id, status, enrolled_date) VALUES (?, ?, ?, ?, 'Enrolled', NOW())";
        String nameSQL = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM students WHERE student_id = ?";

        int successCount = 0;
        List<String> skippedStudents = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPS = conn.prepareStatement(checkSQL);
             PreparedStatement updatePS = conn.prepareStatement(updateSQL);
             PreparedStatement insertPS = conn.prepareStatement(insertSQL);
             PreparedStatement namePS = conn.prepareStatement(nameSQL)) {

            for (int studentId : studentIds) {
                checkPS.setInt(1, studentId);
                checkPS.setInt(2, yearId);
                ResultSet rsCheck = checkPS.executeQuery();

                if (rsCheck.next()) {
                    int enrollmentId = rsCheck.getInt("enrollment_id");
                    String status = rsCheck.getString("status");

                    if ("Not Enrolled".equalsIgnoreCase(status)) {
                        // Update existing record to enrolled
                        updatePS.setInt(1, gradeLevelId);
                        updatePS.setInt(2, sectionId);
                        updatePS.setInt(3, enrollmentId);
                        if (updatePS.executeUpdate() > 0) successCount++;
                    } else {
                        // Already enrolled → skip and note
                        namePS.setInt(1, studentId);
                        ResultSet rsName = namePS.executeQuery();
                        if (rsName.next()) skippedStudents.add(rsName.getString("full_name"));
                        rsName.close();
                    }
                } else {
                    // No record → insert
                    insertPS.setInt(1, studentId);
                    insertPS.setInt(2, gradeLevelId);
                    insertPS.setInt(3, sectionId);
                    insertPS.setInt(4, yearId);
                    insertPS.addBatch();
                }
                rsCheck.close();
            }

            // Execute batch insert for new students
            int[] batchResults = insertPS.executeBatch();
            for (int res : batchResults) {
                if (res > 0) successCount++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new EnrollmentResult(successCount, skippedStudents);
    }

    public Map<String, String> unenrollMultipleStudentsDetailed(List<Integer> studentIds, int yearId) {
        Map<String, String> results = new LinkedHashMap<>();

        String checkQuery = "SELECT s.first_name, s.last_name, e.status " +
                            "FROM enrollment e " +
                            "JOIN students s ON e.student_id = s.student_id " +
                            "WHERE e.student_id = ? AND e.year_id = ?";
        String updateQuery = "UPDATE enrollment SET status = 'Not Enrolled' " +
                             "WHERE student_id = ? AND year_id = ? AND status = 'Enrolled'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkQuery);
             PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {

            for (int studentId : studentIds) {
                checkPs.setInt(1, studentId);
                checkPs.setInt(2, yearId);
                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                    String status = rs.getString("status");

                    if ("Enrolled".equalsIgnoreCase(status)) {
                        updatePs.setInt(1, studentId);
                        updatePs.setInt(2, yearId);
                        int updated = updatePs.executeUpdate();

                        if (updated > 0)
                            results.put(fullName, "Unenrolled successfully");
                        else
                            results.put(fullName, "Failed to unenroll");
                    } else {
                        results.put(fullName, "Already " + status);
                    }
                } else {
                    results.put("Student ID " + studentId, "Not enrolled this year");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
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
    
    public boolean isAlreadyEnrolled(int studentId, int yearId) {
        String query = "SELECT COUNT(*) FROM enrollment WHERE student_id = ? AND year_id = ? AND status = 'Enrolled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.setInt(2, yearId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
