package com.mohammadahtisham.learntrack.ui;

import com.mohammadahtisham.learntrack.service.CourseService;
import com.mohammadahtisham.learntrack.service.EnrollmentService;
import com.mohammadahtisham.learntrack.service.StudentService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

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
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== LearnTrack =====");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
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
        System.out.print("Enter Choice : ");
    }
}