package com.myboot.Students;

import org.springframework.data.jpa.domain.Specification;

public class SpecsFilters {

    public static Specification<Student> ageGreaterThan(int age){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThan(root.get("age"), age);

    }

}
