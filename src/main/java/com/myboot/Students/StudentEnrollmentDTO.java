package com.myboot.Students;

import java.util.List;



public record StudentEnrollmentDTO(
        int id,
        String name,
        Integer age,
        String email,
        String phoneNumber,
        List<Integer> courseIds
) {}
