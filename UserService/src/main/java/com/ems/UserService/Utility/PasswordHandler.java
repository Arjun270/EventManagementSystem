package com.ems.UserService.Utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHandler {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hash Password Before Storing
    public static String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public static boolean matches(String rawPassword, String storedHashedPassword) {
        return passwordEncoder.matches(rawPassword, storedHashedPassword);
    }

}
