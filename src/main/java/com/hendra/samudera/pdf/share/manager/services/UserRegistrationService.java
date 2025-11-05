package com.hendra.samudera.pdf.share.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.hendra.samudera.pdf.share.manager.models.User;

@Service
public class UserRegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    public boolean registerUser(User user) {
        // Cek apakah username sudah ada
        if (userDetailsManager.userExists(user.getUsername())) {
            return false; // Username sudah digunakan
        }

        // Cek apakah password dan confirm password cocok
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return false; // Password tidak cocok
        }

        try {
            // Encode password
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            // Buat user details
            UserDetails userDetails = 
                org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(encodedPassword)
                    .authorities("ROLE_USER") // Beri role USER default
                    .build();

            // Simpan user ke database melalui JdbcUserDetailsManager
            userDetailsManager.createUser(userDetails);
        } catch (DuplicateKeyException e) {
            // Jika terjadi error karena username sudah ada
            return false;
        }

        return true;
    }
}