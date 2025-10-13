/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AcademicYear;
import Util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author USER
 */
public class AcademicYearDAO {
    
    public List<AcademicYear> getAllAcademicYears() {
        List<AcademicYear> years = new ArrayList<>();
        String sql = "SELECT * FROM academic_year ORDER BY year_label";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AcademicYear year = new AcademicYear();
                year.setYearId(rs.getInt("year_id"));
                year.setYearLabel(rs.getString("year_label"));
                year.setStatus(rs.getString("status"));
                years.add(year);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }

    public void setActiveYear(int yearId) throws SQLException {
        String deactivateAll = "UPDATE academic_year SET status = 'CLOSED'";
    String activateSelected = "UPDATE academic_year SET status = 'OPEN' WHERE year_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmtDeactivate = conn.prepareStatement(deactivateAll);
             PreparedStatement stmtActivate = conn.prepareStatement(activateSelected)) {

            conn.setAutoCommit(false);

            stmtDeactivate.executeUpdate();
            stmtActivate.setInt(1, yearId);
            stmtActivate.executeUpdate();

            conn.commit();
        }
    }

    public AcademicYear getOpenAcademicYear() {
        String sql = "SELECT * FROM academic_year WHERE status = 'OPEN' LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                AcademicYear ay = new AcademicYear();
                ay.setYearId(rs.getInt("year_id"));
                ay.setYearLabel(rs.getString("year_label"));
                ay.setStatus(rs.getString("status"));
                return ay;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStatus(int yearId, String status) {
        String sql = "UPDATE academic_year SET status = ? WHERE year_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, yearId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCurrentYearId() throws SQLException {
        String sql = "SELECT year_id FROM academic_year WHERE status = 'OPEN' LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("year_id");
            }
        }
        return -1;
    }

    public AcademicYear getActiveYear() throws SQLException {
        String sql = "SELECT * FROM academic_year WHERE status = 'OPEN' LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                AcademicYear year = new AcademicYear();
                year.setYearId(rs.getInt("year_id"));
                year.setYearLabel(rs.getString("year_label"));
                year.setStatus(rs.getString("status"));
                return year;
            }
        }
        return null;
    }
}
