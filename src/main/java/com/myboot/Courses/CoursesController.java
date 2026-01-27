package com.myboot.Courses;


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
    public List<Courses> findAllCourses()
    {
        return courseServices.findAllCourses();
    }

    @PostMapping
    public void  saveCourse(@RequestBody Courses courses)
    {
        courseServices.saveCourses(courses);
    }

    @PutMapping("{id}")
    public Courses updateCourses(@PathVariable Integer id, @RequestBody Courses courses)
    {
        Courses toUpdate = courseServices.findCourseById(id);
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
        Courses toPatch = courseServices.findCourseById(id);
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


}