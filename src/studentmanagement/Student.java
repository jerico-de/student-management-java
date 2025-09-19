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
     private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String priorSchool;
    private int gradeLevel;
    private String email;
    private String address;
    private String phone;
    private int sectionId;

    public Student(int id, String firstName, String lastName, int age, String priorSchool,
                   int gradeLevel, String email, String address, String phone, int sectionId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.priorSchool = priorSchool;
        this.gradeLevel = gradeLevel;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.sectionId = sectionId;
    }

    public Student(String firstName, String lastName, int age, String priorSchool,
                   int gradeLevel, String email, String address, String phone, int sectionId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.priorSchool = priorSchool;
        this.gradeLevel = gradeLevel;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.sectionId = sectionId;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPriorSchool() { return priorSchool; }
    public int getGradeLevel() { return gradeLevel; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public int getSectionId() { return sectionId; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setPriorSchool(String priorSchool) { this.priorSchool = priorSchool; }
    public void setGradeLevel(int gradeLevel) { this.gradeLevel = gradeLevel; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }
}
