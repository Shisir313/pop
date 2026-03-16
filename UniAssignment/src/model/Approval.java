package model;

import java.util.Objects;

public class Approval {
    private int approvalID;
    private int registrationID;
    private int adminID;
    private String status; // e.g., PENDING, APPROVED, REJECTED

    public Approval() {}

    public Approval(int approvalID, int registrationID, int adminID, String status) {
        this.approvalID = approvalID;
        this.registrationID = registrationID;
        this.adminID = adminID;
        this.status = status;
    }

    public int getApprovalID() {
        return approvalID;
    }

    public void setApprovalID(int approvalID) {
        this.approvalID = approvalID;
    }

    public int getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(int registrationID) {
        this.registrationID = registrationID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "approvalID=" + approvalID +
                ", registrationID=" + registrationID +
                ", adminID=" + adminID +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Approval approval = (Approval) o;
        return approvalID == approval.approvalID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(approvalID);
    }
}