/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class Grade {
    private int gradeId;
    private int studentId;
    private int subjectId;
    private int facultyId;
    private int enrollmentId;
    private String quarter;
    private double grade;
    private String remarks;

    public Grade() {}

    public Grade(int gradeId, int studentId, int subjectId, int facultyId,
                 int enrollmentId, String quarter, double grade, String remarks) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.enrollmentId = enrollmentId;
        this.quarter = quarter;
        this.grade = grade;
        this.remarks = remarks;
    }

    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", facultyId=" + facultyId +
                ", enrollmentId=" + enrollmentId +
                ", quarter='" + quarter + '\'' +
                ", grade=" + grade +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
