package com.myboot.Courses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.myboot.Students.Student;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "COURSES")
public class Courses { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
    private String Name;
    private int Level;


    @JsonIgnore
    @ManyToMany(mappedBy = "assignedCourses" , fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    public Courses() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses courses = (Courses) o;
        return id == courses.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


