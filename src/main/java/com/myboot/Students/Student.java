package com.myboot.Students;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.myboot.Courses.Courses;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.*;


import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENTS")
@JsonPropertyOrder({"id", "name", "age", "assignedCourses"})
@SQLDelete(sql = "UPDATE STUDENTS SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedStudentFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedStudentFilter" , condition = "deleted = :isDeleted")
public class Student {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private int id;
    private String name;
    @Min(value = 18, message = "Student must be at least 18 years old")
    private int age;
    private boolean deleted = false;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "students_courese",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "id")
    )
    private Set<Courses> assignedCourses = new HashSet<>();


    public Student() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Courses> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(Set<Courses> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



