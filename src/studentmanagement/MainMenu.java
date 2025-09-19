/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagement;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        AuthService authService = new AuthService();
        AuthResult authResult = null;

        System.out.println("===== Welcome =====");

        // Only login, no sign-up
        while (authResult == null || !authResult.isSuccess()) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            authResult = authService.login(username, password);
            if (authResult == null || !authResult.isSuccess()) {
                System.out.println("Invalid credentials. Try again.\n");
            }
        }

        // Redirect based on role
        switch (authResult.getRole().toUpperCase()) {
            case "ADMIN" -> showAdminMenu(authResult.getLinkedId());
            case "FACULTY" -> showFacultyMenu(authResult.getLinkedId());
            case "STUDENT" -> showStudentMenu(authResult.getLinkedId());
            default -> System.out.println("Unknown role.");
        }
    }

    // ================= ADMIN MENU =================
    private static void showAdminMenu(int adminId) {
        int choice;
        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.printf("%-3s %s%n", "1.", "Add Student");
            System.out.printf("%-3s %s%n", "2.", "View All Students");
            System.out.printf("%-3s %s%n", "3.", "Update Student");
            System.out.printf("%-3s %s%n", "4.", "Delete Student");
            System.out.printf("%-3s %s%n", "5.", "Search Students");
            System.out.printf("%-3s %s%n", "6.", "Create User (Admin/Faculty/Student)");
            System.out.printf("%-3s %s%n", "0.", "Logout");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> searchStudents();
                case 6 -> createUser();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= FACULTY MENU =================
    private static void showFacultyMenu(int facultyId) {
        int choice;
        do {
            System.out.println("\n===== Faculty Menu =====");
            System.out.printf("%-3s %s%n", "1.", "View Assigned Students");
            System.out.printf("%-3s %s%n", "2.", "Input / Update Grades");
            System.out.printf("%-3s %s%n", "0.", "Logout");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> System.out.println("Viewing assigned students (facultyId: " + facultyId + ")");
                case 2 -> System.out.println("Input/update grades (facultyId: " + facultyId + ")");
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= STUDENT MENU =================
    private static void showStudentMenu(int studentId) {
        int choice;
        do {
            System.out.println("\n===== Student Menu =====");
            System.out.printf("%-3s %s%n", "1.", "View My Information");
            System.out.printf("%-3s %s%n", "2.", "View My Grades");
            System.out.printf("%-3s %s%n", "0.", "Logout");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> System.out.println("Showing student info (studentId: " + studentId + ")");
                case 2 -> System.out.println("Showing grades (studentId: " + studentId + ")");
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ================= USER CREATION (ADMIN ONLY) =================
    private static void createUser() {
        System.out.println("\n--- Create New User ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMIN/FACULTY/STUDENT): ");
        String role = scanner.nextLine().toUpperCase();

        int linkedId = 0;
        if (role.equals("FACULTY")) {
            System.out.print("Enter faculty_id to link: ");
            linkedId = Integer.parseInt(scanner.nextLine());
        } else if (role.equals("STUDENT")) {
            System.out.print("Enter student_id to link: ");
            linkedId = Integer.parseInt(scanner.nextLine());
        }

        boolean success = new AuthService().createUser(username, password, role, linkedId);
        if (success) {
            System.out.println("✅ User created successfully.");
        } else {
            System.out.println("❌ Failed to create user.");
        }
    }

    private static void addStudent() {
    System.out.print("Enter first name: ");
    String firstName = scanner.nextLine();
    if (firstName.isEmpty()) {
        System.out.println("First name cannot be empty.");
        return;
    }

    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();
    if (lastName.isEmpty()) {
        System.out.println("Last name cannot be empty.");
        return;
    }

    System.out.print("Enter age: ");
    if (!scanner.hasNextInt()) {
        System.out.println("Age must be a valid number.");
        scanner.nextLine(); // clear invalid input
        return;
    }
    int age = scanner.nextInt();
    scanner.nextLine();
    if (age <= 0) {
        System.out.println("Age must be positive.");
        return;
    }

    System.out.print("Enter prior school: ");
    String priorSchool = scanner.nextLine();
    if (priorSchool.isEmpty()) {
        System.out.println("Prior school cannot be empty.");
        return;
    }

    System.out.print("Enter grade level (7–12): ");
    if (!scanner.hasNextInt()) {
        System.out.println("Grade level must be a valid number.");
        scanner.nextLine();
        return;
    }
    int gradeLevel = scanner.nextInt();
    scanner.nextLine();
    if (gradeLevel < 7 || gradeLevel > 12) {
        System.out.println("Grade level must be between 7 and 12.");
        return;
    }

    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    if (!email.contains("@") || !email.contains(".")) {
        System.out.println("Invalid email format.");
        return;
    }

    System.out.print("Enter address: ");
    String address = scanner.nextLine();
    if (address.isEmpty()) {
        System.out.println("Address cannot be empty.");
        return;
    }

    System.out.print("Enter phone: ");
    String phone = scanner.nextLine();
    if (phone.isEmpty()) {
        System.out.println("Phone number cannot be empty.");
        return;
    }

    System.out.print("Enter section ID: ");
    if (!scanner.hasNextInt()) {
        System.out.println("Section ID must be a valid number.");
        scanner.nextLine();
        return;
    }
    int sectionId = scanner.nextInt();
    scanner.nextLine();

    // Create student object with full table schema
    Student student = new Student(firstName, lastName, age, priorSchool, gradeLevel, email, address, phone, sectionId);
    studentDAO.addStudent(student);
}

    private static void viewStudents() {
        List<Student> students = studentDAO.getAllStudents();

    if (students.isEmpty()) {
        System.out.println("No students found.");
        return;
    }

    // Sorting menu
    System.out.println("\nSort by: ");
    System.out.printf("%-3s %s%n", "1.", "Name");
    System.out.printf("%-3s %s%n", "2.", "Age");
    System.out.printf("%-3s %s%n", "3.", "Prior School");
    System.out.printf("%-3s %s%n", "4.", "Grade Level");
    System.out.printf("%-3s %s%n", "5.", "No Sorting");
    System.out.print("Enter choice: ");
    int sortChoice = scanner.nextInt();
    scanner.nextLine();

    switch (sortChoice) {
        case 1 -> students.sort(Comparator.comparing(
                s -> (s.getFirstName() + " " + s.getLastName()).toLowerCase()
        ));
        case 2 -> students.sort(Comparator.comparingInt(Student::getAge));
        case 3 -> students.sort(Comparator.comparing(Student::getPriorSchool, String.CASE_INSENSITIVE_ORDER));
        case 4 -> students.sort(Comparator.comparingInt(Student::getGradeLevel));
        case 5 -> {/* no sorting */}
        default -> System.out.println("Invalid choice, showing unsorted list.");
    }

    // Table header
    System.out.printf("%-5s %-15s %-15s %-5s %-25s %-10s %-30s %-30s %-15s %-10s%n",
            "ID", "First Name", "Last Name", "Age", "Prior School", "Grade",
            "Email", "Address", "Phone", "Section");
    System.out.println("--------------------------------------------------------------------------------------------------------------------------");

    // Rows
    for (Student s : students) {
        System.out.printf("%-5d %-15s %-15s %-5d %-25s %-10d %-30s %-30s %-15s %-10d%n",
                s.getId(), s.getFirstName(), s.getLastName(), s.getAge(),
                s.getPriorSchool(), s.getGradeLevel(),
                s.getEmail(), s.getAddress(), s.getPhone(), s.getSectionId());
    }

    // Show total students
    System.out.println("\nTotal number of students: " + students.size());
}

    private static void updateStudent() {
        
    System.out.print("Enter student ID or name to update: ");
    String input = scanner.nextLine();

    // Use list so we can handle multiple matches
    List<Student> matches = studentDAO.findStudent(input);

    if (matches.isEmpty()) {
        System.out.println("No students found.");
        return;
    }

    // Handle multiple matches
    Student existingStudent;
    if (matches.size() > 1) {
        System.out.println("\nMultiple students found:");
        System.out.printf("%-5s %-15s %-15s %-5s %-20s %-8s %-25s%n",
                "ID", "First Name", "Last Name", "Age", "Prior School", "Grade", "Email");
        System.out.println("----------------------------------------------------------------------------------");
        for (Student s : matches) {
            System.out.printf("%-5d %-15s %-15s %-5d %-20s %-8d %-25s%n",
                    s.getId(), s.getFirstName(), s.getLastName(),
                    s.getAge(), s.getPriorSchool(),
                    s.getGradeLevel(), s.getEmail());
        }
        System.out.print("Enter the ID of the student you want to update: ");
        int chosenId = scanner.nextInt();
        scanner.nextLine();

        existingStudent = matches.stream()
                .filter(s -> s.getId() == chosenId)
                .findFirst()
                .orElse(null);

        if (existingStudent == null) {
            System.out.println("Invalid ID selected.");
            return;
        }
    } else {
        existingStudent = matches.get(0);
    }

    // Show current details
    System.out.println("\nCurrent details:");
    System.out.println("1. First Name: " + existingStudent.getFirstName());
    System.out.println("2. Last Name: " + existingStudent.getLastName());
    System.out.println("3. Age: " + existingStudent.getAge());
    System.out.println("4. Prior School: " + existingStudent.getPriorSchool());
    System.out.println("5. Grade Level: " + existingStudent.getGradeLevel());
    System.out.println("6. Email: " + existingStudent.getEmail());
    System.out.println("7. Address: " + existingStudent.getAddress());
    System.out.println("8. Phone: " + existingStudent.getPhone());
    System.out.println("9. Section ID: " + existingStudent.getSectionId());

    System.out.println("\nPress Enter to keep the current value.");

    // === Edit fields ===
    System.out.print("Enter new first name (" + existingStudent.getFirstName() + "): ");
    String firstName = scanner.nextLine().trim();
    if (!firstName.isEmpty()) existingStudent.setFirstName(firstName);

    System.out.print("Enter new last name (" + existingStudent.getLastName() + "): ");
    String lastName = scanner.nextLine().trim();
    if (!lastName.isEmpty()) existingStudent.setLastName(lastName);

    System.out.print("Enter new age (" + existingStudent.getAge() + "): ");
    String ageInput = scanner.nextLine().trim();
    if (!ageInput.isEmpty()) {
        try {
            int age = Integer.parseInt(ageInput);
            if (age > 0) existingStudent.setAge(age);
            else System.out.println("Invalid age. Keeping old value.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Keeping old value.");
        }
    }

    System.out.print("Enter new prior school (" + existingStudent.getPriorSchool() + "): ");
    String priorSchool = scanner.nextLine().trim();
    if (!priorSchool.isEmpty()) existingStudent.setPriorSchool(priorSchool);

    System.out.print("Enter new grade level (" + existingStudent.getGradeLevel() + "): ");
    String gradeInput = scanner.nextLine().trim();
    if (!gradeInput.isEmpty()) {
        try {
            int gradeLevel = Integer.parseInt(gradeInput);
            if (gradeLevel >= 1 && gradeLevel <= 12) existingStudent.setGradeLevel(gradeLevel);
            else System.out.println("Invalid grade level. Keeping old value.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Keeping old value.");
        }
    }

    System.out.print("Enter new email (" + existingStudent.getEmail() + "): ");
    String email = scanner.nextLine().trim();
    if (!email.isEmpty()) {
        if (email.contains("@") && email.contains(".")) {
            existingStudent.setEmail(email);
        } else {
            System.out.println("Invalid email format. Keeping old value.");
        }
    }

    System.out.print("Enter new address (" + existingStudent.getAddress() + "): ");
    String address = scanner.nextLine().trim();
    if (!address.isEmpty()) existingStudent.setAddress(address);

    System.out.print("Enter new phone (" + existingStudent.getPhone() + "): ");
    String phone = scanner.nextLine().trim();
    if (!phone.isEmpty()) existingStudent.setPhone(phone);

    System.out.print("Enter new section ID (" + existingStudent.getSectionId() + "): ");
    String sectionInput = scanner.nextLine().trim();
    if (!sectionInput.isEmpty()) {
        try {
            int sectionId = Integer.parseInt(sectionInput);
            existingStudent.setSectionId(sectionId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid section ID. Keeping old value.");
        }
    }

    // Confirm update
    System.out.print("\nDo you want to save these changes? (Y/N): ");
    String confirm = scanner.nextLine();
    if (confirm.equalsIgnoreCase("Y")) {
        boolean success = studentDAO.updateStudent(existingStudent);
        if (success) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    } else {
        System.out.println("Update cancelled.");
    }
}

    private static void deleteStudent() {
        System.out.print("Enter student ID or name to delete: ");
    String input = scanner.nextLine();

    List<Student> matches = studentDAO.findStudent(input);

    if (matches.isEmpty()) {
        System.out.println("No students found.");
        return;
    }

    // Handle multiple matches
    Student studentToDelete;
    if (matches.size() > 1) {
        System.out.println("\nMultiple students found:");
        System.out.printf("%-5s %-15s %-15s %-5s %-20s %-8s %-25s%n",
                "ID", "First Name", "Last Name", "Age", "Prior School", "Grade", "Email");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Student s : matches) {
            System.out.printf("%-5d %-15s %-15s %-5d %-20s %-8d %-25s%n",
                    s.getId(), s.getFirstName(), s.getLastName(),
                    s.getAge(), s.getPriorSchool(),
                    s.getGradeLevel(), s.getEmail());
        }
        System.out.print("Enter the ID of the student you want to delete: ");
        int chosenId = scanner.nextInt();
        scanner.nextLine();

        studentToDelete = matches.stream()
                             .filter(s -> s.getId() == chosenId)
                             .findFirst()
                             .orElse(null);

        if (studentToDelete == null) {
            System.out.println("Invalid ID selected.");
            return;
        }
    } else {
        studentToDelete = matches.get(0);
    }

    // Show details before deleting
    System.out.println("\nFound student:");
    System.out.printf("%-5s %-15s %-15s %-5s %-20s %-8s %-25s %-25s %-15s %-10s%n",
            "ID", "First Name", "Last Name", "Age", "Prior School", "Grade",
            "Email", "Address", "Phone", "Section");
    System.out.println("-------------------------------------------------------------------------------------------------------------------");
    System.out.printf("%-5d %-15s %-15s %-5d %-20s %-8d %-25s %-25s %-15s %-10d%n",
            studentToDelete.getId(), studentToDelete.getFirstName(), studentToDelete.getLastName(),
            studentToDelete.getAge(), studentToDelete.getPriorSchool(),
            studentToDelete.getGradeLevel(), studentToDelete.getEmail(),
            studentToDelete.getAddress(), studentToDelete.getPhone(),
            studentToDelete.getSectionId());

    // Confirm deletion
    System.out.print("\nAre you sure you want to delete this student? (Y/N): ");
    String confirm = scanner.nextLine();
    if (confirm.equalsIgnoreCase("Y")) {
        if (studentDAO.deleteStudent(studentToDelete.getId())) {
            System.out.println("✅ Student deleted successfully.");
        } else {
            System.out.println("❌ Error deleting student.");
        }
    } else {
        System.out.println("Delete cancelled.");
    }
}
    
    private static void searchStudents() {
        System.out.print("Enter keyword to search (name, prior school, or email): ");
    String keyword = scanner.nextLine();

    List<Student> results = studentDAO.searchStudents(keyword);

    if (results.isEmpty()) {
        System.out.println("No students found.");
        return;
    }    

    // Sorting menu
    System.out.println("\nSort by: ");
    System.out.printf("%-3s %s%n", "1.", "Name");
    System.out.printf("%-3s %s%n", "2.", "Age");
    System.out.printf("%-3s %s%n", "3.", "Prior School");
    System.out.printf("%-3s %s%n", "4.", "Grade Level");
    System.out.printf("%-3s %s%n", "5.", "No Sorting");
    System.out.print("Enter choice: ");
    int sortChoice = scanner.nextInt();
    scanner.nextLine();

    switch (sortChoice) {
        case 1 -> results.sort(
                Comparator.comparing(
                    (Student s) -> (s.getFirstName() + " " + s.getLastName()),
                    String.CASE_INSENSITIVE_ORDER
                ));
        case 2 -> results.sort(Comparator.comparingInt(Student::getAge));
        case 3 -> results.sort(Comparator.comparing(Student::getPriorSchool, String.CASE_INSENSITIVE_ORDER));
        case 4 -> results.sort(Comparator.comparingInt(Student::getGradeLevel));
        case 5 -> {/* no sorting */}
        default -> System.out.println("Invalid choice, showing unsorted list.");
    }

    // Table header
    System.out.printf("%-5s %-15s %-15s %-5s %-25s %-8s %-30s%n",
            "ID", "First Name", "Last Name", "Age", "Prior School", "Grade", "Email");
    System.out.println("---------------------------------------------------------------------------------------------------");

    // Rows
    for (Student s : results) {
        System.out.printf("%-5d %-15s %-15s %-5d %-25s %-8d %-30s%n",
                s.getId(), s.getFirstName(), s.getLastName(),
                s.getAge(), s.getPriorSchool(), s.getGradeLevel(), s.getEmail());
    }

    // Show total
    System.out.println("\nTotal results: " + results.size());
}
}
