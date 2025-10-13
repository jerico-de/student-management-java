/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Curriculum;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class CurriculumDAO {
    
    public List<Curriculum> getCurriculumByGradeLevel(int gradeLevelId) {
        List<Curriculum> list = new ArrayList<>();
        String sql = "SELECT * FROM curriculum WHERE grade_level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gradeLevelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Curriculum c = new Curriculum();
                    c.setCurriculumId(rs.getInt("curriculum_id"));
                    c.setGradeLevelId(rs.getInt("grade_level_id"));
                    c.setSubjectId(rs.getInt("subject_id"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
