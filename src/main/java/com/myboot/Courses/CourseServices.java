package com.myboot.Courses;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {

    private CoursesRepo coursesRepo;
    public CourseServices(CoursesRepo coursesRepo) {
        this.coursesRepo = coursesRepo;
    }

    public List<Courses> findAllCourses() {
        return coursesRepo.findAll();
    }

    public void saveCourses(Courses courses) {
        coursesRepo.save(courses);
    }


    public Courses CourseAndStudentsById(Integer id) {
        return coursesRepo.CourseAndStudentsById(id).orElse(null);
    }

    public void deleteCourse(Integer id) {
        coursesRepo.deleteById(id);
    }


}
