/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class Section {
    private int id;
    private int gradeLevelId;
    private String sectionName;
    
    private String gradeLevelName;

    public Section() {}

    public Section(int id, int gradeLevelId, String sectionName) {
        this.id = id;
        this.gradeLevelId = gradeLevelId;
        this.sectionName = sectionName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getSectonName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    
    public String getGradeLevelName() { return gradeLevelName; }
    public void setGradeLevelName(String gradeLevelName) { this.gradeLevelName = gradeLevelName; }

    @Override
    public String toString() {
        return sectionName;
    }
}
