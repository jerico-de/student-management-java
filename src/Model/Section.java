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
    private int sectionId;
    private int gradeLevelId;
    private String sectionName;

    public Section() {}

    public Section(int sectionId, int gradeLevelId, String sectionName) {
        this.sectionId = sectionId;
        this.gradeLevelId = gradeLevelId;
        this.sectionName = sectionName;
    }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", gradeLevelId=" + gradeLevelId +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }
}
