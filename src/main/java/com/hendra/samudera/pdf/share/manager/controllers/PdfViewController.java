package com.hendra.samudera.pdf.share.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hendra.samudera.pdf.share.manager.models.PdfFile;
import com.hendra.samudera.pdf.share.manager.repositories.PdfFileRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping // Opsional, bisa ditambahkan base path jika perlu
public class PdfViewController {

    @Autowired
    private PdfFileRepository pdfFileRepository;

    // Base URL aplikasi, bisa diambil dari konfigurasi atau request
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @GetMapping("/view/{id}") // Sekarang menggunakan ID file dari database
    public String viewPdf(@PathVariable String id, Model model, HttpServletRequest request) {
        PdfFile pdfFile = pdfFileRepository.findById(id);

        if (pdfFile == null) {
            // Jika file tidak ditemukan di database
            model.addAttribute("error", "File not found in database.");
            return "error"; // Asumsikan template 'error.html' ada
        }

        // Ambil data dari objek PdfFile
        String downloadUrl = pdfFile.getDownloadUrl();
        String originalFileName = pdfFile.getOriginalFileName();
        String title = originalFileName.replace(".pdf", "").replace('_', ' ').replace('-', ' ');
        String thumbnailUrl = pdfFile.getThumbnailUrl();

        // Bangun URL absolut halaman ini
        String currentUrl = request.getRequestURL().toString();

        // Tambahkan atribut ke model untuk digunakan di template pdfViewer.html
        model.addAttribute("downloadUrl", downloadUrl); // URL dari Cloud Storage untuk embed
        model.addAttribute("fileName", originalFileName);
        model.addAttribute("title", title);
        model.addAttribute("description", "PDF file: " + originalFileName);
        model.addAttribute("thumbnailUrl", thumbnailUrl); // Bisa null
        model.addAttribute("currentUrl", currentUrl);

        return "pdfViewer"; // Nama template HTML
    }
}