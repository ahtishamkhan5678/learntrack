package com.mohammadahtisham.learntrack.ui;

import com.mohammadahtisham.learntrack.entity.*;
import com.mohammadahtisham.learntrack.exception.EntityNotFoundException;
import com.mohammadahtisham.learntrack.util.IdGenerator;

public class Main {
    public static void main(String[] args) {

        Person p1 = new Person(1, "Ali", "Khan", "ali@test.com");
        Person p2 = new Student(2, "Sara", "Ahmed", "sara@test.com", "Jan-2026");
        Person p3 = new Trainer(3, "John", "Doe", "john@test.com", "Java");

        Course c = new Course(1, "Java Basics", "Core Java for Beginners", 6);
        System.out.println(c);
        c.setActive(false);
        System.out.println(c);

        Enrollment e = new Enrollment(1, 2, 1);
        System.out.println(e);
        e.setStatus(EnrollmentStatus.COMPLETED);
        System.out.println(e);

        System.out.println(p1.getDisplayName());
        System.out.println(p2.getDisplayName());
        System.out.println(p3.getDisplayName());

        System.out.println(IdGenerator.getNextStudentId());
        System.out.println(IdGenerator.getNextStudentId());
        System.out.println(IdGenerator.getNextCourseId());
        System.out.println(IdGenerator.getNextStudentId());
        System.out.println(IdGenerator.getNextEnrollmentId());

        try {
            throw new EntityNotFoundException("Student with id 99 not found");
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}