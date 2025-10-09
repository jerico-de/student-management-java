/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author USER
 */
public class AcademicYear {
    private int yearId;
    private String yearLabel;
    private String status;

    public AcademicYear() {}

    public AcademicYear(int yearId, String yearLabel, String status) {
        this.yearId = yearId;
        this.yearLabel = yearLabel;
        this.status = status;
    }

    public int getYearId() { return yearId; }
    public void setYearId(int yearId) { this.yearId = yearId; }

    public String getYearLabel() { return yearLabel; }
    public void setYearLabel(String yearLabel) { this.yearLabel = yearLabel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "AcademicYear{" +
                "yearId=" + yearId +
                ", yearLabel='" + yearLabel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
