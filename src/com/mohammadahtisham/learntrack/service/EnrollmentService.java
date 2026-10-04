package com.mohammadahtisham.learntrack.service;

import com.mohammadahtisham.learntrack.entity.Course;
import com.mohammadahtisham.learntrack.entity.Enrollment;
import com.mohammadahtisham.learntrack.entity.EnrollmentStatus;
import com.mohammadahtisham.learntrack.entity.Student;
import com.mohammadahtisham.learntrack.exception.EntityNotFoundException;
import com.mohammadahtisham.learntrack.exception.InvalidInputException;
import com.mohammadahtisham.learntrack.util.IdGenerator;

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

    public Enrollment enrollStudent(int studentId, int courseId) throws EntityNotFoundException, InvalidInputException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is not active");
        }

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is not active");
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        enrollments.add(enrollment);
        return enrollment;
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId) {
                result.add(e);
            }
        }
        return result;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        for (Enrollment e : enrollments) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new EntityNotFoundException("Enrollment with id " + id + " not found");
    }

    public void updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status) throws EntityNotFoundException {
        Enrollment enrollmentById = findEnrollmentById(enrollmentId);
        enrollmentById.setStatus(status);
    }
}

