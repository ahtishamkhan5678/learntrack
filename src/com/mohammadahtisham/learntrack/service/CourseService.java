package com.mohammadahtisham.learntrack.service;

import com.mohammadahtisham.learntrack.entity.Course;
import com.mohammadahtisham.learntrack.exception.EntityNotFoundException;
import com.mohammadahtisham.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses = new ArrayList<>();

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        courses.add(course);
        return course;
    }

    public List<Course> listCourses() {
        return courses;
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course c : courses) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new EntityNotFoundException("Course with id " + id + " not found");
    }


}
