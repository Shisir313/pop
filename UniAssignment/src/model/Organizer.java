package model;

import java.util.Objects;

public class Organizer {
    private int organizerID;
    private String organizerName;
    private String contact;

    public Organizer() {}

    public Organizer(int organizerID, String organizerName, String contact) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
        this.contact = contact;
    }

    public int getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(int organizerID) {
        this.organizerID = organizerID;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "organizerID=" + organizerID +
                ", organizerName='" + organizerName + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizer that = (Organizer) o;
        return organizerID == that.organizerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizerID);
    }
}