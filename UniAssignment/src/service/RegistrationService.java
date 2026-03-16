package service;

import database.DBConnection;
import model.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for registrations.
 */
public class RegistrationService {

    // Register a student for an event
    public boolean registerStudentForEvent(Registration reg) {
        String sql = "INSERT INTO Registration (StudentID, EventID, RegistrationDate) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reg.getStudentID());
            pstmt.setInt(2, reg.getEventID());
            pstmt.setDate(3, java.sql.Date.valueOf(reg.getRegistrationDate()));
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            System.err.println("Error registering student: " + ex.getMessage());
            return false;
        } finally {
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
    }

    // Get registrations by a student
    public List<Registration> getRegistrationsByStudent(int studentID) {
        String sql = "SELECT RegistrationID, StudentID, EventID, RegistrationDate FROM Registration WHERE StudentID = ?";
        List<Registration> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
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
            System.err.println("Error fetching registrations: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return list;
    }

    // Get all registrations
    public List<Registration> getAllRegistrations() {
        String sql = "SELECT RegistrationID, StudentID, EventID, RegistrationDate FROM Registration";
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
            System.err.println("Error fetching all registrations: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return list;
    }
}
