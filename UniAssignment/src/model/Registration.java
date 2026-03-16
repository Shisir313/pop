package model;

import java.time.LocalDate;
import java.util.Objects;

public class Registration {
    private int registrationID;
    private int studentID;
    private int eventID;
    private LocalDate registrationDate;

    public Registration() {}

    public Registration(int registrationID, int studentID, int eventID, LocalDate registrationDate) {
        this.registrationID = registrationID;
        this.studentID = studentID;
        this.eventID = eventID;
        this.registrationDate = registrationDate;
    }

    public int getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(int registrationID) {
        this.registrationID = registrationID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationID=" + registrationID +
                ", studentID=" + studentID +
                ", eventID=" + eventID +
                ", registrationDate=" + registrationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return registrationID == that.registrationID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationID);
    }
}