package service;

import database.DBConnection;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    public boolean createAdmin(Admin admin) {
        String sql = "INSERT INTO Admin (AdminName, Email) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, admin.getAdminName());
            pstmt.setString(2, admin.getEmail());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        admin.setAdminID(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error creating admin: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return false;
    }

    public Admin getAdminById(int adminID) {
        String sql = "SELECT AdminID, AdminName, Email FROM Admin WHERE AdminID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, adminID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.setAdminID(rs.getInt("AdminID"));
                a.setAdminName(rs.getString("AdminName"));
                a.setEmail(rs.getString("Email"));
                return a;
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching admin: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return null;
    }
}