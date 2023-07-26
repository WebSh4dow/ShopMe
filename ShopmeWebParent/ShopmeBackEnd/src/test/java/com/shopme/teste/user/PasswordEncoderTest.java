package com.shopme.teste.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
        String rawPasswd = "jenks2023";

        String encodedPaswd = passwdEncoder.encode(rawPasswd);
        System.out.println("Password encoded:"+ encodedPaswd);

        boolean matches = passwdEncoder.matches(rawPasswd,encodedPaswd);
        assertThat(matches).isTrue();
    }


}
