package com.myboot.Courses;

import com.myboot.Students.Student;
import com.myboot.Students.StudentRepository;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {

    private CoursesRepo coursesRepo;
    private StudentRepository studentRepository;
    public CourseServices(CoursesRepo coursesRepo, StudentRepository studentRepository) {
        this.coursesRepo = coursesRepo;
        this.studentRepository = studentRepository;
    }

    public Page<Courses> findAllCourses(Pageable pageable) {
        return coursesRepo.findAll(pageable);
    }

    public void saveCourses(Courses courses) {
        coursesRepo.save(courses);
    }

    @Transactional
    public void removeStudentFromCourse(int studentId, int courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Courses course = coursesRepo.findById(courseId).orElse(null);
        student.getAssignedCourses().remove(course);
        course.getStudents().remove(student);
        studentRepository.save(student);

    }

    public Courses CourseAndStudentsById(Integer id) {
        return coursesRepo.CourseAndStudentsById(id).orElse(null);
    }

    public void deleteCourse(Integer id) {
        coursesRepo.deleteById(id);
    }

    public Courses findCourseById(Integer id) {

        return coursesRepo.findById(id).orElse(null);
    }


}
