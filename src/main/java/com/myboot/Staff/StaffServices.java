package com.myboot.Staff;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServices {

    private StaffRepo staffRepo;


    public StaffServices(StaffRepo staffRepo) {
        this.staffRepo = staffRepo;
    }
    public List<Staff> findAll() {
        return staffRepo.findAll();
    }

    public void saveStaffRepo(Staff staff) {
        staffRepo.save(staff);
    }

    public void deleteStaffRepo(Staff staff) {
        staffRepo.delete(staff);
    }
    public Staff findStaffById(Integer id) {
        return staffRepo.findById(id).orElse(null);
}

}
