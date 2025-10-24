/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class FacultyAssignment {
    private int facultySubjectId;
    private int facultyId;
    private int subjectId;
    private int sectionId;
    
    private String facultyName;
    private String subjectName;
    private String gradeLevelName;
    private String sectionName;
    private boolean adviser;

    public FacultyAssignment() {}

    public FacultyAssignment(int facultySubjectId, int facultyId, int subjectId, int sectionId) {
        this.facultySubjectId = facultySubjectId;
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.sectionId = sectionId;
    }

    public int getFacultySubjectId() { return facultySubjectId; }
    public void setFacultySubjectId(int facultySubjectId) { this.facultySubjectId = facultySubjectId; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }
    
    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getGradeLevelName() { return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }
    
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    
    public boolean isAdviser() { return adviser; }
    public void setAdviser(boolean adviser) { this.adviser = adviser; }

    @Override
    public String toString() {
        return "FacultySubject{" +
                "facultySubjectId=" + facultySubjectId +
                ", facultyId=" + facultyId +
                ", subjectId=" + subjectId +
                ", sectionId=" + sectionId +
                '}';
    }
}
