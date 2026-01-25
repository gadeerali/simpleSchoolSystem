package com.myboot.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepo extends JpaRepository<Staff, Integer> {

    List<Staff> findById(int id);


}
