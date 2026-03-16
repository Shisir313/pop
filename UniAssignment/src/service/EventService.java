package service;

import database.DBConnection;
import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for event-related operations.
 */
public class EventService {

    // Create a new event in the database
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO Event (EventName, EventDate, OrganizerID) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, event.getEventName());
            pstmt.setDate(2, java.sql.Date.valueOf(event.getEventDate()));
            pstmt.setInt(3, event.getOrganizerID());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            System.err.println("Error creating event: " + ex.getMessage());
            return false;
        } finally {
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
    }

    // Retrieve all events
    public List<Event> getAllEvents() {
        String sql = "SELECT EventID, EventName, EventDate, OrganizerID FROM Event";
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                java.sql.Date d = rs.getDate("EventDate");
                if (d != null) e.setEventDate(d.toLocalDate());
                e.setOrganizerID(rs.getInt("OrganizerID"));
                events.add(e);
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching events: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return events;
    }

    // Get a single event by ID
    public Event getEventById(int eventID) {
        String sql = "SELECT EventID, EventName, EventDate, OrganizerID FROM Event WHERE EventID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eventID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                java.sql.Date d = rs.getDate("EventDate");
                if (d != null) e.setEventDate(d.toLocalDate());
                e.setOrganizerID(rs.getInt("OrganizerID"));
                return e;
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching event by id: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return null;
    }
}
