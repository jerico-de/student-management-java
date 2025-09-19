/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class StudentDAO {
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, age, prior_school, grade_level, email, address, phone, section_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getPriorSchool());
            stmt.setInt(5, student.getGradeLevel());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getAddress());
            stmt.setString(8, student.getPhone());
            stmt.setInt(9, student.getSectionId());

            stmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("prior_school"),
                        rs.getInt("grade_level"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("section_id")
                );
                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET first_name=?, last_name=?, age=?, prior_school=?, grade_level=?, email=?, address=?, phone=?, section_id=? WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getPriorSchool());
            stmt.setInt(5, student.getGradeLevel());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getAddress());
            stmt.setString(8, student.getPhone());
            stmt.setInt(9, student.getSectionId());
            stmt.setInt(10, student.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> findStudent(String input) {
        List<Student> students = new ArrayList<>();
        boolean isNumeric = input.matches("\\d+");
        String sql = isNumeric ? "SELECT * FROM students WHERE student_id=?"
                               : "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (isNumeric) {
                stmt.setInt(1, Integer.parseInt(input));
            } else {
                String keyword = "%" + input + "%";
                stmt.setString(1, keyword);
                stmt.setString(2, keyword);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("prior_school"),
                        rs.getInt("grade_level"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("section_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ? OR prior_school LIKE ? OR email LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            stmt.setString(4, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("prior_school"),
                        rs.getInt("grade_level"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("section_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}
