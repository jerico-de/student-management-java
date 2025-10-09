/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class Curriculum {
    private int curriculumId;
    private int gradeLevelId;
    private int subjectId;

    public Curriculum() {}

    public Curriculum(int curriculumId, int gradeLevelId, int subjectId) {
        this.curriculumId = curriculumId;
        this.gradeLevelId = gradeLevelId;
        this.subjectId = subjectId;
    }

    public int getCurriculumId() { return curriculumId; }
    public void setCurriculumId(int curriculumId) { this.curriculumId = curriculumId; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    @Override
    public String toString() {
        return "Curriculum{" +
                "curriculumId=" + curriculumId +
                ", gradeLevelId=" + gradeLevelId +
                ", subjectId=" + subjectId +
                '}';
    }
}
