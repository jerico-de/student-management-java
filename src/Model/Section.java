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
    private String name;

    public Section() {}

    public Section(int id, int gradeLevelId, String name) {
        this.id = id;
        this.gradeLevelId = gradeLevelId;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGradeLevelId() { return gradeLevelId; }
    public void setGradeLevelId(int gradeLevelId) { this.gradeLevelId = gradeLevelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}
