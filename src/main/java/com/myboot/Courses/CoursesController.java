package com.myboot.Courses;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Courses")
public class CoursesController {
    private final CourseServices courseServices;
    public CoursesController(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    @GetMapping
    public ResponseEntity<List<Courses>> findAllCourses()
    {
        List<Courses> list = courseServices.findAllCourses();

        int  total = list.size();
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

    public Courses CourseAndStudentsById(Integer id) {
        return courseServices.CourseAndStudentsById(id);
    }


}