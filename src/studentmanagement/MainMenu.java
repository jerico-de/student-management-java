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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        boolean loggedIn = false;
        
        System.out.println("===== Welcome =====");
        
        while (!loggedIn) {
            System.out.println("1. Login");
            System.out.println("2. Sign Up (Admin)");
            System.out.print("Enter choice: ");
            int authChoice = scanner.nextInt();
            scanner.nextLine();
            
            if (authChoice == 1 || authChoice == 2) {
                
                if (authChoice == 2){ // Sign up
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    authService.signUp(newUsername, newPassword);
                    System.out.println("Please log in.");
                  }
                
                while (!loggedIn) { // Log in, choice 1 or after sign up
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    loggedIn = authService.login(username, password);
                
                if (!loggedIn) {
                    System.out.println("Invalid credentials. Try again.");
                   }
                }
            } else {
                    System.out.println("Invalid Choice. Try again.");
                }
        }
        
        int choice; // Main Menu
        do {
            System.out.println("\n===== Main Menu =====");
            System.out.printf("%-3s %s%n", "1.", "Add Student");
            System.out.printf("%-3s %s%n", "2.", "View All Students");
            System.out.printf("%-3s %s%n", "3.", "Update Student");
            System.out.printf("%-3s %s%n", "4.", "Delete Student");
            System.out.printf("%-3s %s%n", "5.", "Search Students");
            System.out.printf("%-3s %s%n", "6.", "Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> searchStudents();
                case 6 -> System.out.println("Goodbye!");
                default -> System.out.println("nvalid choice.");
            }
        } while (choice != 6);
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        if (name.isEmpty()){
            System.out.println("Name cannot be empty.");
            return;
        }
        
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        if (age <= 0){
            System.out.println("Age must be a positive number.");
            return;
        }
        
        System.out.print("Enter course: ");
        String course = scanner.nextLine();
        
        if (course.isEmpty()){
            System.out.println("Course cannot be empty.");
            return;
        }
        
        System.out.print("Enter year level: ");
        int yearLevel = scanner.nextInt();
        
        if (yearLevel < 1 || yearLevel > 5){
            System.out.println("Year level must be between 1 and 5.");
            return;
        }
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        if (!email.contains("@") || !email.contains(".")) {
        System.out.println("Invalid email format.");
            return;
        }

        Student student = new Student(name, age, course, yearLevel, email);
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
    System.out.printf("%-3s %s%n", "3.", "Course");
    System.out.printf("%-3s %s%n", "4.", "Year Level");
    System.out.printf("%-3s %s%n", "5.", "No Sorting");
    System.out.print("Enter choice: ");
    int sortChoice = scanner.nextInt();
    scanner.nextLine();

    switch (sortChoice) {
        case 1 -> students.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        case 2 -> students.sort(Comparator.comparingInt(Student::getAge));
        case 3 -> students.sort(Comparator.comparing(Student::getCourse, String.CASE_INSENSITIVE_ORDER));
        case 4 -> students.sort(Comparator.comparingInt(Student::getYearLevel));
        case 5 -> {/* no sorting */}
        default -> System.out.println("Invalid choice, showing unsorted list.");
    }

    // Table header
    System.out.printf("%-5s %-25s %-5s %-30s %-8s %-30s%n",
            "ID", "Name", "Age", "Course", "Year", "Email");
    System.out.println("---------------------------------------------------------------------------------------------------");

    // Rows
    for (Student s : students) {
        System.out.printf("%-5d %-25s %-5d %-30s %-8d %-30s%n",
                s.getId(), s.getName(), s.getAge(),
                s.getCourse(), s.getYearLevel(), s.getEmail());
    }

    // Show total students
    System.out.println("\n📊 Total number of students: " + students.size());
}

    private static void updateStudent() {
        System.out.print("Enter student ID or name to update: ");
    String input = scanner.nextLine();

    // Use list so we can handle multiple matches
    List<Student> matches = studentDAO.findStudent(input);

    if (matches.isEmpty()) {
        System.out.println("❌ No students found.");
        return;
    }

    // Handle multiple matches
    Student existingStudent;
    if (matches.size() > 1) {
        System.out.println("\nMultiple students found:");
        System.out.printf("%-5s %-25s %-5s %-30s %-8s %-30s%n",
            "ID", "Name", "Age", "Course", "Year", "Email");
            System.out.println("---------------------------------------------------------------------------------------------------");
        for (Student s : matches) {
            System.out.printf("%-5d %-25s %-5d %-30s %-8d %-30s%n",
                s.getId(), s.getName(), s.getAge(),
                s.getCourse(), s.getYearLevel(), s.getEmail());
        }
        System.out.print("Enter the ID of the student you want to update: ");
        int chosenId = scanner.nextInt();
        scanner.nextLine();

        existingStudent = matches.stream()
                                 .filter(s -> s.getId() == chosenId)
                                 .findFirst()
                                 .orElse(null);

        if (existingStudent == null) {
            System.out.println("❌ Invalid ID selected.");
            return;
        }
    } else {
        existingStudent = matches.get(0);
    }

    // Show current details
    System.out.println("\nCurrent details:");
    System.out.println("1. Name: " + existingStudent.getName());
    System.out.println("2. Age: " + existingStudent.getAge());
    System.out.println("3. Course: " + existingStudent.getCourse());
    System.out.println("4. Year Level: " + existingStudent.getYearLevel());
    System.out.println("5. Email: " + existingStudent.getEmail());

    System.out.println("\nPress Enter to keep the current value.");

    // === Edit fields ===
    System.out.print("Enter new name (" + existingStudent.getName() + "): ");
    String name = scanner.nextLine().trim();
    if (!name.isEmpty()) existingStudent.setName(name);

    System.out.print("Enter new age (" + existingStudent.getAge() + "): ");
    String ageInput = scanner.nextLine().trim();
    if (!ageInput.isEmpty()) {
        try {
            int age = Integer.parseInt(ageInput);
            if (age > 0) existingStudent.setAge(age);
            else System.out.println("⚠️ Invalid age. Keeping old value.");
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Keeping old value.");
        }
    }

    System.out.print("Enter new course (" + existingStudent.getCourse() + "): ");
    String course = scanner.nextLine().trim();
    if (!course.isEmpty()) existingStudent.setCourse(course);

    System.out.print("Enter new year level (" + existingStudent.getYearLevel() + "): ");
    String yearInput = scanner.nextLine().trim();
    if (!yearInput.isEmpty()) {
        try {
            int yearLevel = Integer.parseInt(yearInput);
            if (yearLevel >= 1 && yearLevel <= 5) existingStudent.setYearLevel(yearLevel);
            else System.out.println("⚠️ Invalid year level. Keeping old value.");
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Keeping old value.");
        }
    }

    System.out.print("Enter new email (" + existingStudent.getEmail() + "): ");
    String email = scanner.nextLine().trim();
    if (!email.isEmpty()) {
        if (email.contains("@") && email.contains(".")) {
            existingStudent.setEmail(email);
        } else {
            System.out.println("⚠️ Invalid email format. Keeping old value.");
        }
    }

    // Confirm update
    System.out.print("\nDo you want to save these changes? (Y/N): ");
    String confirm = scanner.nextLine();
    if (confirm.equalsIgnoreCase("Y")) {
        boolean success = studentDAO.updateStudent(existingStudent);
        if (success) {
            System.out.println("✅ Student updated successfully!");
        } else {
            System.out.println("❌ Update failed.");
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
        System.out.println("❌ No students found.");
        return;
    }

    // Handle multiple matches
    Student studentToDelete;
    if (matches.size() > 1) {
        System.out.println("\nMultiple students found:");
        System.out.printf("%-5s %-25s %-5s %-30s %-8s %-30s%n",
            "ID", "Name", "Age", "Course", "Year", "Email");
            System.out.println("---------------------------------------------------------------------------------------------------");
        for (Student s : matches) {
            System.out.printf("%-5d %-25s %-5d %-30s %-8d %-30s%n",
                s.getId(), s.getName(), s.getAge(),
                s.getCourse(), s.getYearLevel(), s.getEmail());
        }
        System.out.print("Enter the ID of the student you want to delete: ");
        int chosenId = scanner.nextInt();
        scanner.nextLine();

        studentToDelete = matches.stream()
                                 .filter(s -> s.getId() == chosenId)
                                 .findFirst()
                                 .orElse(null);

        if (studentToDelete == null) {
            System.out.println("❌ Invalid ID selected.");
            return;
        }
    } else {
        studentToDelete = matches.get(0);
    }

    // Show details before deleting
    System.out.println("\nFound student:");
    System.out.printf("%-5s %-25s %-5s %-30s %-8s %-30s%n",
        "ID", "Name", "Age", "Course", "Year", "Email");
    System.out.println("---------------------------------------------------------------------------------------------------");
    System.out.printf("%-5d %-25s %-5d %-30s %-8d %-30s%n",
        studentToDelete.getId(), studentToDelete.getName(), studentToDelete.getAge(),
        studentToDelete.getCourse(), studentToDelete.getYearLevel(), studentToDelete.getEmail());

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
    System.out.print("Enter keyword to search (name, course, or email): ");
    String keyword = scanner.nextLine();

    List<Student> results = studentDAO.searchStudents(keyword);

    if (results.isEmpty()) {
        System.out.println("No students found.");
        return;
    } 
    
        //Sorting menu
        System.out.println("\nSort by: ");
        System.out.printf("%-3s %s%n", "1.", "Name");
        System.out.printf("%-3s %s%n", "2.", "Age");
        System.out.printf("%-3s %s%n", "3.", "Course");
        System.out.printf("%-3s %s%n", "4.", "Year Level");
        System.out.printf("%-3s %s%n", "5.", "No Sorting");
        System.out.print("Enter choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (sortChoice) {
            case 1 -> results.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
            case 2 -> results.sort(Comparator.comparingInt(Student::getAge));
            case 3 -> results.sort(Comparator.comparing(Student::getCourse, String.CASE_INSENSITIVE_ORDER));
            case 4 -> results.sort(Comparator.comparingInt(Student::getYearLevel));
            case 5 -> {/*no sorting */}
            default -> System.out.println("Invalid choice, showing unsorted list.");
        }
        
            System.out.printf("%-5s %-25s %-5s %-30s %-8s %-30s%n",
                "ID", "Name", "Age", "Course", "Year", "Email");
            System.out.println("---------------------------------------------------------------------------------------------------");

        for (Student s : results) {
            System.out.printf("%-5d %-25s %-5d %-30s %-8d %-30s%n",
                s.getId(), s.getName(), s.getAge(), s.getCourse(), s.getYearLevel(), s.getEmail());
        }
    }
}
