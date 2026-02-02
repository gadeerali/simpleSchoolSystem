package com.myboot;

import com.myboot.Staff.Staff;
import com.myboot.Staff.StaffRepo;
import com.myboot.config.JwtAuth;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthService {
    private final JwtAuth jwtAuth;
    private final StaffRepo staffRepo;
    private final PasswordEncoder passwordEncoder;
    public AuthService(JwtAuth jwtAuth, StaffRepo staffRepo, PasswordEncoder passwordEncoder) {
        this.jwtAuth = jwtAuth;
        this.staffRepo = staffRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> userCredentials) {
        System.out.println("debugging" + userCredentials.get("username"));
        String username = userCredentials.get("username");
        String password = userCredentials.get("password");

      Staff staff = staffRepo.findByName(username).orElseThrow(() -> new RuntimeException("Invalid username or password"));

      if (passwordEncoder.matches(password, staff.getPassword())) {
          String token = jwtAuth.generateToken(username);
            return Map.of("token", token);
      }

        throw new RuntimeException("Invalid username or password");
    }
}
