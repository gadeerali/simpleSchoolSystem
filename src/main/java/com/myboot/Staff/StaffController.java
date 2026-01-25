package com.myboot.Staff;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Staff")
public class StaffController {
    private final StaffRepo staffRepo;
    private final StaffServices staffServices;

    public StaffController(StaffRepo staffRepo, StaffServices staffServices) {
        this.staffRepo = staffRepo;
        this.staffServices = staffServices;
    }
@GetMapping
    public List<Staff> findAllStaff(){
    return staffRepo.findAll();
    }

@GetMapping("{id}")
    public Staff findStaffbyId(@PathVariable Integer id){
        return staffServices.findStaffById(id);
    }
@PostMapping
    public Staff PutStaff(@RequestBody Staff staff){
        return staffRepo.save(staff);
}

@PatchMapping("{id}")
    public Staff PatchStaff(@PathVariable Integer id, @RequestBody Staff staff){
        Staff updatedStaff = staffRepo.findById(id).orElse(staff);

        if (updatedStaff.getName() != null){
            updatedStaff.setName(staff.getName());
        }
        if (updatedStaff.getTitle() != null){
            updatedStaff.setTitle(staff.getTitle());
        }
        staffRepo.save(updatedStaff);
        return staffServices.findStaffById(id);
}
}
