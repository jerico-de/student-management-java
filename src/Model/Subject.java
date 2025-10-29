/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class Subject {
    private int subjectId;
    private int gradeLevelId;
    private String subjectName;

    public Subject() {}

    public Subject(int subjectId, int gradeLevelId, String subjectName) {
        this.subjectId = subjectId;
        this.gradeLevelId = gradeLevelId;
        this.subjectName = subjectName;
    }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    @Override
    public String toString() {
        return subjectId == -1 ? "Select" :subjectName;
    }
}
