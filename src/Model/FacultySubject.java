/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class FacultySubject {
    private int facultySubjectId;
    private int facultyId;
    private int subjectId;
    private int sectionId;

    public FacultySubject() {}

    public FacultySubject(int facultySubjectId, int facultyId, int subjectId, int sectionId) {
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
