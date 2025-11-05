package com.hendra.samudera.pdf.share.manager.controllers;

import com.hendra.samudera.pdf.share.manager.models.User;
import com.hendra.samudera.pdf.share.manager.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Akan ditampilkan di halaman register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        boolean isRegistered = userRegistrationService.registerUser(user);

        if (!isRegistered) {
            model.addAttribute("error", "Username already exists or passwords do not match");
            return "register";
        }

        model.addAttribute("success", "Registration successful! You can now login.");
        return "login"; // Alihkan ke halaman login setelah registrasi berhasil
    }
}