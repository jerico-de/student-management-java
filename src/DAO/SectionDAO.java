/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Section;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class SectionDAO {
    
    public List<Section> getSectionsByGradeLevel(int gradeLevelId) {
        List<Section> list = new ArrayList<>();
        String sql = "SELECT * FROM sections WHERE grade_level_id = ? ORDER BY section_name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gradeLevelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Section s = new Section();
                    s.setSectionId(rs.getInt("section_id"));
                    s.setGradeLevelId(rs.getInt("grade_level_id"));
                    s.setSectionName(rs.getString("section_name"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
