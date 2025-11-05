package com.hendra.samudera.pdf.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        String rawPassword = "password"; // Replace with the desired password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
