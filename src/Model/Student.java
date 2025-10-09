/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class Student {
    private int studentId;
    private int userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private LocalDate birthdate;
    private String address;
    private String enrollmentStatus;
    
    public Student() {}
    
    public Student(int studentId, int userId, String firstName, String lastName, String middleName, String gender, LocalDate birthdate, 
            String address, String enrollmentStatus) {
        this.studentId = studentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.enrollmentStatus = enrollmentStatus;
    }
    
    public Student(int studentId, int userId, String firstName, String lastName, String gender, LocalDate birthdate, String address) {
        this.studentId = studentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
    }
    
    public Student(int studentId, String firstName, String lastName, String enrollmentStatus) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollmentStatus = enrollmentStatus;
    }
    
    // Getters and Setters
    public int getStudentId() {return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", userId=" + userId +
                ", name='" + lastName + ", " + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", status='" + enrollmentStatus + '\'' +
                '}';
    }
 }
