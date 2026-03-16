package console;

import model.Event;
import model.Registration;
import model.Student;
import model.Organizer;
import model.Admin;
import service.EventService;
import service.RegistrationService;
import service.ApprovalService;
import service.StudentService;
import service.OrganizerService;
import service.AdminService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Simple console UI to interact with services.
 */
public class ConsoleMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventService eventService = new EventService();
    private static final RegistrationService registrationService = new RegistrationService();
    private static final ApprovalService approvalService = new ApprovalService();
    private static final StudentService studentService = new StudentService();
    private static final OrganizerService organizerService = new OrganizerService();
    private static final AdminService adminService = new AdminService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Main Menu");
            System.out.println("1. Student");
            System.out.println("2. Organizer");
            System.out.println("3. Admin");
            System.out.println("4. Register New User");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": studentMenu(); break;
                case "2": organizerMenu(); break;
                case "3": adminMenu(); break;
                case "4": registerNewUserMenu(); break;
                case "5": System.out.println("Exiting..."); System.exit(0); break;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void registerNewUserMenu() {
        while (true) {
            System.out.println("\nRegister New User");
            System.out.println("1. Student");
            System.out.println("2. Organizer");
            System.out.println("3. Admin");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": registerStudent(); break;
                case "2": registerOrganizer(); break;
                case "3": registerAdmin(); break;
                case "4": return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void studentMenu() {
        System.out.print("Enter your StudentID: ");
        int studentID = Integer.parseInt(scanner.nextLine());
        while (true) {
            System.out.println("\nStudent Menu");
            System.out.println("1. View Events");
            System.out.println("2. Register for Event");
            System.out.println("3. View My Registrations");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": viewEvents(); break;
                case "2": registerForEvent(studentID); break;
                case "3": viewMyRegistrations(studentID); break;
                case "4": return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void organizerMenu() {
        System.out.print("Enter your OrganizerID: ");
        int organizerID = Integer.parseInt(scanner.nextLine());
        while (true) {
            System.out.println("\nOrganizer Menu");
            System.out.println("1. Create Event");
            System.out.println("2. View My Events");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": createEvent(organizerID); break;
                case "2": viewMyEvents(organizerID); break;
                case "3": return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void adminMenu() {
        System.out.print("Enter your AdminID: ");
        int adminID = Integer.parseInt(scanner.nextLine());
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. View Pending Registrations");
            System.out.println("2. Approve Registration");
            System.out.println("3. Reject Registration");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": viewPendingRegistrations(); break;
                case "2": approveRegistration(adminID); break;
                case "3": rejectRegistration(adminID); break;
                case "4": return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void viewEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        System.out.println("Events:");
        for (Event e : events) {
            System.out.println(e);
        }
    }

    private static void registerForEvent(int studentID) {
        viewEvents();
        System.out.print("Enter EventID to register: ");
        int eventID = Integer.parseInt(scanner.nextLine());
        Registration reg = new Registration();
        reg.setStudentID(studentID);
        reg.setEventID(eventID);
        reg.setRegistrationDate(LocalDate.now());
        boolean ok = registrationService.registerStudentForEvent(reg);
        System.out.println(ok ? "Registration successful (pending approval)." : "Registration failed.");
    }

    private static void viewMyRegistrations(int studentID) {
        List<Registration> regs = registrationService.getRegistrationsByStudent(studentID);
        if (regs.isEmpty()) {
            System.out.println("No registrations found.");
            return;
        }
        System.out.println("Your Registrations:");
        for (Registration r : regs) {
            System.out.println(r);
        }
    }

    private static void createEvent(int organizerID) {
        System.out.print("Event name: ");
        String name = scanner.nextLine();
        System.out.print("Event date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);
        Event e = new Event();
        e.setEventName(name);
        e.setEventDate(date);
        e.setOrganizerID(organizerID);
        boolean ok = eventService.createEvent(e);
        System.out.println(ok ? "Event created." : "Failed to create event.");
    }

    private static void viewMyEvents(int organizerID) {
        List<Event> events = eventService.getAllEvents();
        boolean found = false;
        for (Event e : events) {
            if (e.getOrganizerID() == organizerID) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found) System.out.println("No events for this organizer.");
    }

    private static void viewPendingRegistrations() {
        List<Registration> pending = approvalService.getPendingRegistrations();
        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
            return;
        }
        System.out.println("Pending Registrations:");
        for (Registration r : pending) {
            System.out.println(r);
        }
    }

    private static void approveRegistration(int adminID) {
        viewPendingRegistrations();
        System.out.print("Enter RegistrationID to approve: ");
        int regID = Integer.parseInt(scanner.nextLine());
        boolean ok = approvalService.approveRegistration(regID, adminID);
        System.out.println(ok ? "Registration approved." : "Failed to approve.");
    }

    private static void rejectRegistration(int adminID) {
        viewPendingRegistrations();
        System.out.print("Enter RegistrationID to reject: ");
        int regID = Integer.parseInt(scanner.nextLine());
        boolean ok = approvalService.rejectRegistration(regID, adminID);
        System.out.println(ok ? "Registration rejected." : "Failed to reject.");
    }

    // New registration flows
    private static void registerStudent() {
        System.out.print("Student name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Student s = new Student();
        s.setStudentName(name);
        s.setEmail(email);
        boolean ok = studentService.createStudent(s);
        if (ok) {
            System.out.println("Student registered with ID: " + s.getStudentID());
        } else {
            System.out.println("Failed to register student.");
        }
    }

    private static void registerOrganizer() {
        System.out.print("Organizer name: ");
        String name = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();
        Organizer o = new Organizer();
        o.setOrganizerName(name);
        o.setContact(contact);
        boolean ok = organizerService.createOrganizer(o);
        if (ok) {
            System.out.println("Organizer registered with ID: " + o.getOrganizerID());
        } else {
            System.out.println("Failed to register organizer.");
        }
    }

    private static void registerAdmin() {
        System.out.print("Admin name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Admin a = new Admin();
        a.setAdminName(name);
        a.setEmail(email);
        boolean ok = adminService.createAdmin(a);
        if (ok) {
            System.out.println("Admin registered with ID: " + a.getAdminID());
        } else {
            System.out.println("Failed to register admin.");
        }
    }
}