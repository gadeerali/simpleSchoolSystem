package com.myboot;

import com.myboot.config.JwtAuth;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtReproductionTest {

    @Autowired
    private JwtAuth jwtAuth;

    @Test
    void testSignatureExceptionWithBrackets() {
        String token = jwtAuth.generateToken("admin");
        // Mimic the behavior in Request.http where the token might be wrapped in <>
        String wrappedToken = "<" + token + ">";
        
        assertThrows(SignatureException.class, () -> {
            jwtAuth.extractUsername(wrappedToken);
        });
    }

    @Test
    void testSuccessWithoutBrackets() {
        String token = jwtAuth.generateToken("admin");
        assertDoesNotThrow(() -> {
            jwtAuth.extractUsername(token);
        });
    }
}
