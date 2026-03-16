package model;

import java.time.LocalDate;
import java.util.Objects;

public class Event {
    private int eventID;
    private String eventName;
    private LocalDate eventDate;
    private int organizerID;

    public Event() {}

    public Event(int eventID, String eventName, LocalDate eventDate, int organizerID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.organizerID = organizerID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(int organizerID) {
        this.organizerID = organizerID;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", organizerID=" + organizerID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventID == event.eventID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID);
    }
}