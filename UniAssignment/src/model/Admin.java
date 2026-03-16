package model;

import java.util.Objects;

public class Admin {
    private int adminID;
    private String adminName;
    private String email;

    public Admin() {}

    public Admin(int adminID, String adminName, String email) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.email = email;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminID=" + adminID +
                ", adminName='" + adminName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return adminID == admin.adminID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminID);
    }
}