package com.mohammadahtisham.learntrack.service;

import com.mohammadahtisham.learntrack.entity.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
}
