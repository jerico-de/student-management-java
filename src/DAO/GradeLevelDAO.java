/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.GradeLevel;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class GradeLevelDAO {
    
    public List<GradeLevel> getAllGradeLevels() {
        List<GradeLevel> list = new ArrayList<>();
        String sql = "SELECT * FROM grade_levels ORDER BY grade_name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GradeLevel gl = new GradeLevel();
                gl.setGradeLevelId(rs.getInt("grade_level_id"));
                gl.setGradeName(rs.getString("grade_name"));
                list.add(gl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
