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
    private String name;
    private int age;
    private String course;
    private int yearLevel;
    private String email;

    public Student(int id, String name, int age, String course, int yearLevel, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.yearLevel = yearLevel;
        this.email = email;
    }

    public Student(String name, int age, String course, int yearLevel, String email) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.yearLevel = yearLevel;
        this.email = email;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public int getYearLevel() { return yearLevel; }
    public String getEmail() {return email; };
    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int name) { this.age = age; }
    public void setCourse(String course) { this.course = course; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }
    public void setEmail(String email) { this.email = email; }
}
