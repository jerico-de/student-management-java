# Student Management System

A simple Java Swing-based application for managing students, users, and enrollments.  
This project includes automatic user creation for new students and integration with an enrollment system.

## Features

- Add, edit, and delete students.
- Automatically create a user account when a new student is added.
- Link students to user accounts.
- Manage enrollment and view student enrollment status.
- Gender and birthdate tracking.

## Technologies Used

- Java (Swing for GUI)
- MySQL (Database)
- JDBC (Database connectivity)
- JDateChooser (Date selection in Swing)

## Database Structure

- `users` table: stores login info (username, password, role, status).  
- `students` table: stores student info and links to `users`.  
- `enrollment` table: manages student enrollment status.

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/jerico-de/student-management-java.git