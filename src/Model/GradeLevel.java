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
    private int id;
    private String gradeLevelName;

    public GradeLevel() {}

    public GradeLevel(int id, String gradeLevelName) {
        this.id = id;
        this.gradeLevelName = gradeLevelName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getGradeLevelName() { return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }

   @Override
    public String toString() {
        return gradeLevelName;
    }
}
