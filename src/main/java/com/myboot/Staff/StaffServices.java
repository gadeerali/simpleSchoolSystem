package com.myboot.Staff;


import com.myboot.Students.Student;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServices {

    private final PasswordEncoder passwordEncoder;
    private StaffRepo staffRepo;
    private final EntityManager entityManager;


    public StaffServices(StaffRepo staffRepo, EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.staffRepo = staffRepo;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Staff> findAll(boolean softDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedStaffFilter");
        filter.setParameter("isDeleted", softDeleted);
        List<Staff> staff = staffRepo.findAll();
        session.disableFilter("deletedStaffFilter");
        return staff;
    }

    public void saveStaffRepo(Staff staff) {
        String encodedpassword = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodedpassword);
        staffRepo.save(staff);
    }

    public void deleteStaffRepo(Staff staff) {
        staffRepo.delete(staff);
    }
    public Staff findStaffById(Integer id) {
        return staffRepo.findById(id).orElse(null);
}
    public void softdeleteStaff(Integer id) {
        staffRepo.deleteById(id);
    }


}
