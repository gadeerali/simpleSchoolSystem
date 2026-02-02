package com.myboot;

import com.myboot.config.JwtAuth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthService {
    private final JwtAuth jwtAuth;
    public AuthService(JwtAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> userCredentials) {
        System.out.println("debugging" + userCredentials.get("username"));
        String username = userCredentials.get("username");
        String password = userCredentials.get("password");

        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtAuth.generateToken(username);
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid username or password");
    }
}
