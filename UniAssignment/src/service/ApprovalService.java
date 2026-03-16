package service;

import database.DBConnection;
import model.Approval;
import model.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to approve or reject registrations.
 */
public class ApprovalService {

    // Approve a registration by creating/updating an approval row
    public boolean approveRegistration(int registrationID, int adminID) {
        return upsertApproval(registrationID, adminID, "APPROVED");
    }

    // Reject a registration
    public boolean rejectRegistration(int registrationID, int adminID) {
        return upsertApproval(registrationID, adminID, "REJECTED");
    }

    // Helper to insert or update approval
    private boolean upsertApproval(int registrationID, int adminID, String status) {
        String selectSql = "SELECT ApprovalID FROM Approval WHERE RegistrationID = ?";
        String insertSql = "INSERT INTO Approval (RegistrationID, AdminID, Status) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Approval SET AdminID = ?, Status = ? WHERE RegistrationID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, registrationID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // update
                DBConnection.closeQuietly(rs);
                DBConnection.closeQuietly(pstmt);
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, adminID);
                pstmt.setString(2, status);
                pstmt.setInt(3, registrationID);
                int rows = pstmt.executeUpdate();
                return rows > 0;
            } else {
                DBConnection.closeQuietly(rs);
                DBConnection.closeQuietly(pstmt);
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, registrationID);
                pstmt.setInt(2, adminID);
                pstmt.setString(3, status);
                int rows = pstmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error upserting approval: " + ex.getMessage());
            return false;
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
    }

    // Get pending registrations (i.e., registrations without approval or with status PENDING)
    public List<Registration> getPendingRegistrations() {
        String sql = "SELECT r.RegistrationID, r.StudentID, r.EventID, r.RegistrationDate " +
                     "FROM Registration r LEFT JOIN Approval a ON r.RegistrationID = a.RegistrationID " +
                     "WHERE a.Status IS NULL OR a.Status = 'PENDING'";
        List<Registration> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRegistrationID(rs.getInt("RegistrationID"));
                r.setStudentID(rs.getInt("StudentID"));
                r.setEventID(rs.getInt("EventID"));
                java.sql.Date d = rs.getDate("RegistrationDate");
                if (d != null) r.setRegistrationDate(d.toLocalDate());
                list.add(r);
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching pending registrations: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return list;
    }
}