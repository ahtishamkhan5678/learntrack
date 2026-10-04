package com.mohammadahtisham.learntrack.service;

import com.mohammadahtisham.learntrack.entity.Student;
import com.mohammadahtisham.learntrack.exception.EntityNotFoundException;
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

    public List<Student> listStudents() {
        return students;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new EntityNotFoundException("Student with id " + id + " not found");
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student studentById = findStudentById(id);
        studentById.setActive(false);
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch) throws EntityNotFoundException {
        Student studentById = findStudentById(id);
        studentById.setFirstName(firstName);
        studentById.setLastName(lastName);
        studentById.setEmail(email);
        studentById.setBatch(batch);
    }
}
