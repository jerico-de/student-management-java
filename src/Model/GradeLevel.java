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
    private String gradeLevelName;

    public GradeLevel() {}

    public GradeLevel(int gradeLevelId, String gradeLevelName) {
        this.gradeLevelId = gradeLevelId;
        this.gradeLevelName = gradeLevelName;
    }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getGradeLevelName() { return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }

   @Override
    public String toString() {
        return gradeLevelId == -1 ? "Select" :gradeLevelName;
    }
}
