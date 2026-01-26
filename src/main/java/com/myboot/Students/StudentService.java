package com.myboot.Students;

import com.myboot.Courses.Courses;
import com.myboot.Courses.CoursesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CoursesRepo coursesRepo;
    @Scheduled (cron = "0 30 11 * * ?")
    public void scheduledStudents(){
        long conut = studentRepository.count();
        System.out.println("Total Students: "+conut);
    }


    @Autowired
    public StudentService(StudentRepository studentRepository, CoursesRepo coursesRepo) {
    this.studentRepository = studentRepository;
    this.coursesRepo = coursesRepo;
    }
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }


    public void saveStudent(Student students) {
      studentRepository.save(students);
    }
    public List<Student> findStudentsOlderthan(int age) {
        Specification<Student> spec = SpecsFilters.ageGreaterThan(age);
        return studentRepository.findAll(spec);


    }
    @Transactional
    public Student findStudentById(Integer id) {

        return studentRepository.findByIdWithCourses(id).orElse(null);
    }

    @Transactional
    public Student assingCourseStudent(Integer stdId, Integer crsId) {
        Student student = studentRepository.findById(stdId).orElse(null);
        if (student == null) {
            return null;
        }
        Courses courses = coursesRepo.findById(crsId).orElse(null);
        if (courses == null) {
            return null;
        }
        Set<Courses> coursesSet = student.getAssignedCourses();
        if (coursesSet == null) {
            coursesSet = new HashSet<>();
        }
        coursesSet.add(courses);
        courses.getStudents().add(student);
        student.setAssignedCourses(coursesSet);
        return studentRepository.save(student);
    }

    public void deleteStudent() {
        studentRepository.deleteAll();
    }
}
