/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author USER
 */
public class EnrollmentResult {
    private int enrolledCount;
    private List<String> skippedStudents;

    public EnrollmentResult(int enrolledCount, List<String> skippedStudents) {
        this.enrolledCount = enrolledCount;
        this.skippedStudents = skippedStudents;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public List<String> getSkippedStudents() {
        return skippedStudents;
    }
}
