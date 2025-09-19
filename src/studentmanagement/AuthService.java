/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author USER
 */
public class AuthService {
    
    // Login based role
    public AuthResult login(String username, String password) {
        AuthResult result = new AuthResult();
        
        String sql = "SELECT password_hash, role, linked_id FROM users WHERE username=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
           
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (storedHash.equals(hashPassword(password))){
                    result.setSuccess(true);
                    result.setRole(rs.getString("role"));
                    result.setLinkedId(rs.getInt("linked_id"));
                }
                return result;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        result.setSuccess(false);
        return result;
    }
    
    public boolean createUser(String username, String password, String role, int linkedId) {
        String sql = "INSERT INTO users (username, password_hash, role, linked_id) VALUES (?, ?, ?, ?)";
        String hashedPassword = hashPassword(password);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role.toUpperCase());
            stmt.setInt(4, linkedId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
