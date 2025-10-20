/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
/**
 *
 * @author USER
 */
public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int gradeLevelId;
    private int sectionId;
    private Integer curriculumId;
    private int yearId;
    private String status;
    private LocalDateTime enrolledDate;
    
    private String gradeLevelName;
    private String sectionName;
    private String yearLabel;
    private String academicYear;
    
    private String gradeName;

    public Enrollment() {}

    public Enrollment(int enrollmentId, int studentId, int gradeLevelId, int sectionId,
                      Integer curriculumId, int yearId, String status, LocalDateTime enrolledDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.gradeLevelId = gradeLevelId;
        this.sectionId = sectionId;
        this.curriculumId = curriculumId;
        this.yearId = yearId;
        this.status = status;
        this.enrolledDate = enrolledDate;
    }
    
    public Enrollment(int studentId, String academicYear, String sectionName, String gradeName) {
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.sectionName = sectionName;
        this.gradeName = gradeName;
    }
    
    public Enrollment(int studentId, String sectionName, String gradeName) {
        this.studentId = studentId;
        this.sectionName = sectionName;
        this.gradeName = gradeName;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }

    public Integer getCurriculumId() { return curriculumId; }
    public void setCurriculumId(Integer curriculumId) { this.curriculumId = curriculumId; }

    public int getYearId() { return yearId; }
    public void setYearId(int yearId) { this.yearId = yearId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDateTime enrolledDate) { this.enrolledDate = enrolledDate; }
    
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    
    public String getAcademicYear() {return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public String getGradeName() { return gradeName; }
    public void setGradeName(String gradeName) { this.gradeName = gradeName; }
    
    public String getGradeLevelName() { return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }
            
    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", studentId=" + studentId +
                ", gradeLevelId=" + gradeLevelId +
                ", sectionId=" + sectionId +
                ", yearId=" + yearId +
                ", status='" + status + '\'' +
                ", enrolledDate=" + enrolledDate +
                '}';
    }
}
