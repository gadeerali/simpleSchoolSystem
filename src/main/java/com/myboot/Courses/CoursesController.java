package com.myboot.Courses;


import com.myboot.Students.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/Courses")
public class CoursesController {
    private final CourseServices courseServices;
    private final CoursesRepo coursesRepo;

    public CoursesController(CourseServices courseServices, CoursesRepo coursesRepo) {
        this.courseServices = courseServices;
        this.coursesRepo = coursesRepo;
    }

    @GetMapping
    public ResponseEntity<List<Courses>> findAllCourses()
    {
        List<Courses> list = coursesRepo.findAll();
        int total = list.size();

        return ResponseEntity
                .ok()
                .header("Content-Range", "Staff 0-" + (total - 1) + "/" + total)
                .body(list);

    }

    @PostMapping
    public void  saveCourse(@RequestBody Courses courses)
    {
        courseServices.saveCourses(courses);
    }

    @PutMapping("{id}")
    public Courses updateCourses(@PathVariable Integer id, @RequestBody Courses courses)
    {
        Courses toUpdate = courseServices.CourseAndStudentsById(id);
        toUpdate.setName(courses.getName());
        toUpdate.setLevel(courses.getLevel());
        courseServices.saveCourses(toUpdate);
        return toUpdate;
    }
    @DeleteMapping("{id}")
    public void deleteCourses(@PathVariable Integer id)
    {
        courseServices.deleteCourse(id);
    }
    @PatchMapping("{id}")
    public Courses patchCourse(@PathVariable Integer id, @RequestBody Courses courses)
    {
        Courses toPatch = courseServices.CourseAndStudentsById(id);
        if (toPatch.getName() != null)
        {
            toPatch.setName(courses.getName());
        }
        if (toPatch.getLevel() != 0) {
            toPatch.setLevel(courses.getLevel());
        }
        courseServices.saveCourses(toPatch);
        return toPatch;
    }
    @GetMapping("{id}")
    public Courses findCourseById(@PathVariable Integer id) {
       Courses courses = courseServices.findCourseById(id);
       if (courses == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
       }
       return courses;
    }

    public Courses CourseAndStudentsById(Integer id) {
        return courseServices.CourseAndStudentsById(id);
    }


}