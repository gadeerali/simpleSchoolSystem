package com.myboot.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepo extends JpaRepository<Staff, Integer> {

    Optional<Staff> findByName(String name);


}
