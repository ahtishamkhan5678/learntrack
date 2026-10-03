package com.mohammadahtisham.learntrack.ui;

import com.mohammadahtisham.learntrack.entity.Course;
import com.mohammadahtisham.learntrack.entity.Person;
import com.mohammadahtisham.learntrack.entity.Student;
import com.mohammadahtisham.learntrack.entity.Trainer;

public class Main {
    public static void main(String[] args) {

        Person p1 = new Person(1, "Ali", "Khan", "ali@test.com");
        Person p2 = new Student(2, "Sara", "Ahmed", "sara@test.com", "Jan-2026");
        Person p3 = new Trainer(3, "John", "Doe", "john@test.com", "Java");

        Course c = new Course(1, "Java Basics", "Core Java for Beginners", 6);
        System.out.println(c);
        c.setActive(false);
        System.out.println(c);

        System.out.println(p1.getDisplayName());
        System.out.println(p2.getDisplayName());
        System.out.println(p3.getDisplayName());

    }
}