package service;

import database.DBConnection;
import model.Organizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerService {

    public boolean createOrganizer(Organizer org) {
        String sql = "INSERT INTO Organizer (OrganizerName, Contact) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, org.getOrganizerName());
            pstmt.setString(2, org.getContact());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        org.setOrganizerID(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error creating organizer: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return false;
    }

    public Organizer getOrganizerById(int organizerID) {
        String sql = "SELECT OrganizerID, OrganizerName, Contact FROM Organizer WHERE OrganizerID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, organizerID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Organizer o = new Organizer();
                o.setOrganizerID(rs.getInt("OrganizerID"));
                o.setOrganizerName(rs.getString("OrganizerName"));
                o.setContact(rs.getString("Contact"));
                return o;
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching organizer: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return null;
    }
}