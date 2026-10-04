package com.mohammadahtisham.learntrack.service;

import com.mohammadahtisham.learntrack.entity.Student;
import com.mohammadahtisham.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch);
        students.add(student);
        return student;
    }
}
