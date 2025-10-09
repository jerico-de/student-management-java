/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class GradeLevel {
    private int gradeLevelId;
    private String gradeName;

    public GradeLevel() {}

    public GradeLevel(int gradeLevelId, String gradeName) {
        this.gradeLevelId = gradeLevelId;
        this.gradeName = gradeName;
    }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getGradeName() { return gradeName; }
    public void setGradeName(String gradeName) { this.gradeName = gradeName; }

    @Override
    public String toString() {
        return "GradeLevel{" +
                "gradeLevelId=" + gradeLevelId +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
