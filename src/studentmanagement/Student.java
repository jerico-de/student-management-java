/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

/**
 *
 * @author USER
 */
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    private String email;
    private String address;
    private String phone;
    private int gradeLevel;
    private int sectionId;

    // Constructor
    public Student(String firstName, String lastName, int age, String sex, String email, String address, String phone, int gradeLevel, int sectionId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gradeLevel = gradeLevel;
        this.sectionId = sectionId;
    }
        
    public Student(int studentId, String firstName, String lastName, int age, String sex, String email, String address, String phone, int gradeLevel, int sectionId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gradeLevel = gradeLevel;
        this.sectionId = sectionId;
    }

    // Getters & Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}

