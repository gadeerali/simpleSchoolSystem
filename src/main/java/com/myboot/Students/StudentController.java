package com.myboot.Students;


import com.myboot.Courses.CourseServices;
import com.myboot.Courses.Courses;
import com.myboot.Courses.CoursesRepo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController

@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final CourseServices courseServices;
    private final StudentRepository studentRepository;
    private final CoursesRepo coursesRepo;

    public StudentController(StudentService studentService, CourseServices courseServices,
                             StudentRepository studentRepository, CoursesRepo coursesRepo) {
        this.studentService = studentService;
        this.courseServices = courseServices;
        this.studentRepository = studentRepository;
        this.coursesRepo = coursesRepo;
    }

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

   @GetMapping("{id}")
    public Student findStudentById(@PathVariable Integer id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return student;

    }

    @PostMapping
    public Student saveStudent(@Validated @RequestBody Student student)
    {    return  studentRepository.save(student);
    }


    @GetMapping("{id}/courses")
    public Set<Courses> findStudentCourses(@PathVariable Integer id) {
        Student students = studentService.findStudentById(id);
        if (students == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return students.getAssignedCourses();
    }
    @GetMapping("/summary/{id}")
    public Optional<StudentSummary> findStudentSummaryById(@PathVariable Integer id) {
        return Optional.ofNullable(studentRepository.findStudentSummaryById(id).orElse(null));
    }

    @GetMapping("/specs/{age}")
    public List<Student> findStudentsOlderthan(@PathVariable int age){
        return studentService.findStudentsOlderthan(age);
    }


       @PutMapping("/{sid}/{cid}")
      public Student assingCourseStudent(
              @PathVariable Integer sid,
              @PathVariable Integer cid) {
        Student updated = studentService.assingCourseStudent(sid, cid);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student or course not found");
        }
        return updated;

    }

   @DeleteMapping
    public void deleteAllStudent() {
        studentService.deleteStudent();
    }


  /*  @PatchMapping("{id}")
    public Student patchStudent(@PathVariable Integer id, @RequestBody Student student) {
    Student toPatch = studentService.findStudentById(id);
    if (toPatch.getName() != null){
        toPatch.setName(student.getName());
    }
    if (toPatch.getAge() != 0){
        toPatch.setAge(student.getAge());
    }
    if (student.getCourses() != null){
        toPatch.setCourses(student.getCourses());
    }
    studentService.saveStudent(toPatch);
    return studentService.findStudentById(id);
    } */

}
