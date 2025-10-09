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
public class Faculty {
    private int facultyId;
    private int userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private LocalDate birthdate;
    private String address;
    private Integer advisoryGradeLevelId;
    private Integer advisorySectionId;

    public Faculty() {}

    public Faculty(int facultyId, int userId, String firstName, String lastName, String middleName, String gender, LocalDate birthdate, String address, 
            Integer advisoryGradeLevelId, Integer advisorySectionId) {
        this.facultyId = facultyId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.advisoryGradeLevelId = advisoryGradeLevelId;
        this.advisorySectionId = advisorySectionId;
    }
    
    public Faculty(int facultyId, int userId, String firstName, String lastName, String gender) {
        this.facultyId = facultyId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
            

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

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

    public Integer getAdvisoryGradeLevelId() { return advisoryGradeLevelId; }
    public void setAdvisoryGradeLevelId(Integer advisoryGradeLevelId) { this.advisoryGradeLevelId = advisoryGradeLevelId; }

    public Integer getAdvisorySectionId() { return advisorySectionId; }
    public void setAdvisorySectionId(Integer advisorySectionId) { this.advisorySectionId = advisorySectionId; }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", userId=" + userId +
                ", name='" + lastName + ", " + firstName + '\'' +
                ", advisoryGradeLevelId=" + advisoryGradeLevelId +
                ", advisorySectionId=" + advisorySectionId +
                '}';
    }
}
