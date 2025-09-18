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
        String sql = "INSERT INTO students (name, age, course, year_level, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getCourse());
            stmt.setInt(4, student.getYearLevel());
            stmt.setString(5, student.getEmail());
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
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getInt("year_level"),
                        rs.getString("email")
                );
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, age=?, course=?, year_level=?, email=? WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getCourse());
            stmt.setInt(4, student.getYearLevel());
            stmt.setString(5,student.getEmail());
            stmt.setInt(6, student.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
            
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
    }
    
    
    public List<Student> findStudent (String input) {
        List<Student> students = new ArrayList<>();
        String sql;
        boolean isNumeric = input.matches("\\d+"); // checks if the input is ID number
        
        if (isNumeric) {
            sql = "SELECT * FROM students WHERE student_id=?";
        } else {
            sql = "SELECT * FROM students WHERE name LIKE ?";
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (isNumeric) {
                stmt.setInt(1, Integer.parseInt(input));
            } else {
                stmt.setString(1, "%" + input + "%");
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                students.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course"),
                    rs.getInt("year_level"),
                    rs.getString("email")
                ));       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        } return false;
    }
    
    public List<Student> searchStudents(String keyword) {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT * FROM students WHERE name LIKE ? OR course LIKE ? OR email LIKE ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        String like = "%" + keyword + "%";
        stmt.setString(1, like);
        stmt.setString(2, like);
        stmt.setString(3, like);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            students.add(new Student(
                rs.getInt("student_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("course"),
                rs.getInt("year_level"),
                rs.getString("email")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return students;
}
}
