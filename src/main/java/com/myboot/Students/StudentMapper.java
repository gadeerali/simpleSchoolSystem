package com.myboot.Students;


import com.myboot.Courses.Courses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper (componentModel = "spring")
public interface StudentMapper {

    //creation method
    //ignoring the target to NOT call the setters for it
    @Mapping(target = "assignedCourses", ignore = true)
    Student toEntity(StudentEnrollmentDTO dto);//from DTO, came as an input and needs to go to the entity"target"

    //updateMethod
    @Mapping(target = "assignedCourses", ignore = true)
    void updateStudent(StudentEnrollmentDTO dto, @MappingTarget Student entity);

    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "maskedPhone")
    @Mapping(source = "assignedCourses", target = "courseIds")
    StudentEnrollmentDTO toDto(Student entity);//to DTO, came from the entity"source" to DTO"target"

    //"helper method"
    default List<Integer> map(Set<Courses> courses){
        return courses == null ? null : courses.stream()
                .map(Courses::getId)
                .toList();

    }

    //masking method logic
    @Named("maskedPhone")
    static String maskPhoneNumber(String phoneNumber){
        if (phoneNumber == null || phoneNumber.length() < 4){
            return "not Available";
        }
       return "******" + phoneNumber.substring(10);

    }

}
