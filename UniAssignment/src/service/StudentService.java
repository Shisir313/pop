package service;

import database.DBConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service for student-related DB operations.
 */
public class StudentService {

    // Create a new student record
    public boolean createStudent(Student student) {
        String sql = "INSERT INTO Student (StudentName, Email) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getEmail());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        student.setStudentID(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error creating student: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return false;
    }

    // Get student by ID
    public Student getStudentById(int studentID) {
        String sql = "SELECT StudentID, StudentName, Email FROM Student WHERE StudentID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setStudentID(rs.getInt("StudentID"));
                s.setStudentName(rs.getString("StudentName"));
                s.setEmail(rs.getString("Email"));
                return s;
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching student: " + ex.getMessage());
        } finally {
            DBConnection.closeQuietly(rs);
            DBConnection.closeQuietly(pstmt);
            DBConnection.closeQuietly(conn);
        }
        return null;
    }
}