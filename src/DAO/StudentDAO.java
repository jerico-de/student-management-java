/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Student;
import Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class StudentDAO {
    
    public boolean addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (user_id, first_name, last_name, middle_name, gender, birthdate, address) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, student.getUserId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getMiddleName());
            stmt.setString(5, student.getGender());

            if (student.getBirthdate() != null) {
                stmt.setDate(6, java.sql.Date.valueOf(student.getBirthdate()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setString(7, student.getAddress());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // get generated student_id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setStudentId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    public Student getStudentById(int studentId) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("student_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_name"),
                        rs.getString("gender"),
                        rs.getDate("birthdate").toLocalDate(),
                        rs.getString("address")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }
    
    public List<Student> searchStudents(String keyword, int currentYearId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT s.*, 
                   COALESCE(e.status, 'Not Enrolled') AS current_status
            FROM students s
            LEFT JOIN enrollment e 
                ON s.student_id = e.student_id AND e.year_id = ?
            LEFT JOIN users u 
                ON s.user_id = u.user_id
            WHERE s.first_name LIKE ? 
               OR s.last_name LIKE ? 
               OR u.username LIKE ? 
               OR s.student_id LIKE ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            stmt.setInt(1, currentYearId);
            stmt.setString(2, likeKeyword);
            stmt.setString(3, likeKeyword);
            stmt.setString(4, likeKeyword);
            stmt.setString(5, likeKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setStudentId(rs.getInt("student_id"));
                    st.setUserId(rs.getInt("user_id"));
                    st.setFirstName(rs.getString("first_name"));
                    st.setLastName(rs.getString("last_name"));
                    st.setMiddleName(rs.getString("middle_name"));
                    st.setGender(rs.getString("gender"));
                    if (rs.getDate("birthdate") != null) {
                        st.setBirthdate(rs.getDate("birthdate").toLocalDate());
                    }
                    st.setAddress(rs.getString("address"));
                    st.setCurrentStatus(rs.getString("current_status"));
                    students.add(st);
                }
            }
        }
        return students;
    }
            
    public List<Student> getAllStudentsWithStatus(int currentYearId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT s.*, 
                   COALESCE(e.status, 'Not Enrolled') AS current_status
            FROM students s
            LEFT JOIN enrollment e 
              ON s.student_id = e.student_id AND e.year_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, currentYearId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student st = new Student();
                st.setStudentId(rs.getInt("student_id"));
                st.setUserId(rs.getInt("user_id"));
                st.setFirstName(rs.getString("first_name"));
                st.setLastName(rs.getString("last_name"));
                st.setMiddleName(rs.getString("middle_name"));
                st.setGender(rs.getString("gender"));
                if (rs.getDate("birthdate") != null) {
                    st.setBirthdate(rs.getDate("birthdate").toLocalDate());
                }
                st.setAddress(rs.getString("address"));
                st.setCurrentStatus(rs.getString("current_status"));
                students.add(st);
            }
        }
        return students;
    }
    
    public List<Student> getAllStudentsWithStatus() throws SQLException {
        AcademicYearDAO academicYearDAO = new AcademicYearDAO();
        int currentYearId = academicYearDAO.getCurrentYearId();
        return getAllStudentsWithStatus(currentYearId);
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, middle_name = ?, gender = ?, birthdate = ?, address = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getMiddleName());
            stmt.setString(4, student.getGender());
            stmt.setDate(5, Date.valueOf(student.getBirthdate()));
            stmt.setString(6, student.getAddress());
            stmt.setInt(7, student.getStudentId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        String getUserIdQuery = "SELECT user_id FROM students WHERE student_id = ?";
        String deleteStudentQuery = "DELETE FROM students WHERE student_id = ?";
        String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId = -1;
            try (PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteStudentQuery)) {
                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            if (userId != -1) {
                try (PreparedStatement ps = conn.prepareStatement(deleteUserQuery)) {
                    ps.setInt(1, userId);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
