/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagement;

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

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!authService.login(username, password)) {
            System.out.println("Invalid credentials.");
            return;
        }

        int choice;
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
                default -> System.out.println("❌ Invalid choice.");
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

        // Table header
        System.out.printf("%-5s %-25s %-5s %-30s %-10s %-30s%n",
                "ID", "Name", "Age", "Course", "Year", "Email");
        System.out.println("-------------------------------------------------------------------------------");

        // Rows
        for (Student s : students) {
            System.out.printf("%-5d %-25s %-5d %-30s %-10d %-30s%n",
                s.getId(), s.getName(), s.getAge(), s.getCourse(), s.getYearLevel(), s.getEmail());
        }
    }

    private static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        
        if (name.isEmpty()){
            System.out.println("Name cannot be empty.");
            return;
        }
        
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        if (age <= 0){
            System.out.println("Age must be a positive number.");
            return;
        }
        
        System.out.print("Enter new course: ");
        String course = scanner.nextLine();
        
        if (course.isEmpty()){
            System.out.println("Course cannot be empty.");
            return;
        }
        
        System.out.print("Enter new year level: ");
        int yearLevel = scanner.nextInt();
        
        if (yearLevel < 1 || yearLevel > 5){
            System.out.println("Year level must be between 1 and 5.");
            return;
        }
        
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        
        if (!email.contains("@") || !email.contains(".")) {
        System.out.println("Invalid email format.");
            return;
        }

        Student student = new Student(id, name, age, course, yearLevel, email);
        studentDAO.updateStudent(student);
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        studentDAO.deleteStudent(id);
    }
    
    private static void searchStudents() {
    System.out.print("Enter keyword to search (name, course, or email): ");
    String keyword = scanner.nextLine();

    List<Student> results = studentDAO.searchStudents(keyword);

    if (results.isEmpty()) {
        System.out.println("No students found.");
    } else {
            System.out.printf("%-5s %-25s %-5s %-30s %-10s %-30s%n",
                "ID", "Name", "Age", "Course", "Year", "Email");
            System.out.println("-------------------------------------------------------------------------------");

        for (Student s : results) {
            System.out.printf("%-5d %-25s %-5d %-30s %-10d %-30s%n",
                s.getId(), s.getName(), s.getAge(), s.getCourse(), s.getYearLevel(), s.getEmail());
        }
      }
    }
}
