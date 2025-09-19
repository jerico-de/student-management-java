/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

/**
 *
 * @author USER
 */
public class AuthResult {
    private boolean success;
    private String role; // ADMIN, FACULTY, STUDENT
    private int linkedId;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getLinkedId() { return linkedId; }
    public void setLinkedId(int linkedId) { this.linkedId = linkedId; }
}

