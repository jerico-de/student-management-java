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
    private String currentStatus;
    
    private String status;
    private String gradeLevelName;
    
    public Student() {}
    
    public Student(int studentId, int userId, String firstName, String lastName, String middleName, String gender, LocalDate birthdate, 
            String address, String currentStatus) {
        this.studentId = studentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.currentStatus = currentStatus;
    }
    
    public Student(String firstName, String lastName, String middleName, String gender, LocalDate birthdate, 
            String address, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.currentStatus = currentStatus;
        this.userId = userId;
    }
    
    public Student(int studentId, int userId, String firstName, String lastName, String middleName, String gender, LocalDate birthdate, String address) {
        this.studentId = studentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
    }
    
    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Student(int studentId, int userId, String firstName, String lastName) {
        this.studentId = studentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
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
    
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    
    public String getStatus() {  return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getGradeLevelName() {  return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }
            
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", userId=" + userId +
                ", name='" + lastName + ", " + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
 }
