package com.hendra.samudera.pdf.share.manager.controllers;

import com.hendra.samudera.pdf.share.manager.models.PdfFile;
import com.hendra.samudera.pdf.share.manager.repositories.PdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PdfFileRepository pdfFileRepository;

    // Endpoint untuk halaman home
    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        // Ambil daftar file PDF yang diupload oleh user saat ini
        List<PdfFile> pdfFiles = pdfFileRepository.findByUploadedBy(principal.getName());
        model.addAttribute("pdfFiles", pdfFiles);
        
        return "home"; // Menampilkan halaman home
    }
    
    // Redirect root ke home
    @GetMapping("/")
    public String root(Principal principal, Model model) {
        if (principal != null) {
            // Jika user sudah login, arahkan ke home
            return home(principal, model);
        } else {
            // Jika belum login, arahkan ke login
            return "redirect:/login";
        }
    }
}