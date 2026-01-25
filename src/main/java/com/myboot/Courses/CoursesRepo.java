package com.myboot.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface CoursesRepo extends JpaRepository<Courses, Integer> {
    List<Courses> id(Integer id);
}
