package com.myboot.Students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("select s from Student s left join fetch s.assignedCourses where s.id = :id")
    Optional<Student> findByIdWithCourses(@Param("id") Integer id);
}
