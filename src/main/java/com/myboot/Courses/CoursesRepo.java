package com.myboot.Courses;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CoursesRepo extends JpaRepository<Courses, Integer> {
    @Query("select c from Courses c left join fetch c.students where c.id = :id")
    Optional<Courses> CourseAndStudentsById(@Param("id") Integer id);

}