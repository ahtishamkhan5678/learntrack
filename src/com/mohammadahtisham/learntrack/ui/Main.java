package com.mohammadahtisham.learntrack.ui;

import com.mohammadahtisham.learntrack.entity.Student;
import com.mohammadahtisham.learntrack.exception.EntityNotFoundException;
import com.mohammadahtisham.learntrack.exception.InvalidInputException;
import com.mohammadahtisham.learntrack.service.CourseService;
import com.mohammadahtisham.learntrack.service.EnrollmentService;
import com.mohammadahtisham.learntrack.service.StudentService;
import com.mohammadahtisham.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    findStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deactivateStudent();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Batch: ");
        String batch = scanner.nextLine();

        try {
            InputValidator.requireNonEmpty(firstName, "First name");
            InputValidator.requireNonEmpty(lastName, "Last name");
            InputValidator.requireNonEmpty(batch, "Batch");
            InputValidator.validateEmail(email);
            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("Student added with id " + student.getId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            printStudent(s);
        }
    }

    private static void printStudent(Student s) {
        System.out.println(s.getId() + " | " + s.getDisplayName() + " | " + s.getEmail() + " | " + (s.isActive() ? "ACTIVE" : "INACTIVE"));
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static void findStudent() {
        try {
            int id = readInt("Student id: ");
            Student student = studentService.findStudentById(id);
            printStudent(student);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        try {
            int id = readInt("Student id: ");
            studentService.findStudentById(id);
            System.out.print("First name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Batch: ");
            String batch = scanner.nextLine();
            InputValidator.requireNonEmpty(firstName, "First name");
            InputValidator.requireNonEmpty(lastName, "Last name");
            InputValidator.requireNonEmpty(batch, "Batch");
            InputValidator.validateEmail(email);
            studentService.updateStudent(id, firstName, lastName, email, batch);
            System.out.println("Student updated.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deactivateStudent() {
        try {
            int id = readInt("Student id: ");
            studentService.deactivateStudent(id);
            System.out.println("Student " + id + " deactivated.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("===== LearnTrack =====");
        System.out.println("1. Add student");
        System.out.println("2. List students");
        System.out.println("3. Find student by id");
        System.out.println("4. Update student");
        System.out.println("5. Deactivate student");
        System.out.println("6. Add course");
        System.out.println("7. List courses");
        System.out.println("8. Activate/Deactivate course");
        System.out.println("9. Enroll student in course");
        System.out.println("10. View enrollments for student");
        System.out.println("11. Update enrollment status");
        System.out.println("0. Exit");
        System.out.print("Enter Choice: ");
    }
}