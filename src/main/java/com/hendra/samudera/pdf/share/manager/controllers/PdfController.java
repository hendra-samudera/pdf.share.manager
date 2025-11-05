package com.hendra.samudera.pdf.share.manager.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hendra.samudera.pdf.share.manager.models.PdfFile;
import com.hendra.samudera.pdf.share.manager.services.PdfStorageService;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfStorageService pdfStorageService;

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        return "upload"; // Menampilkan halaman upload
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Validasi file
            if (file.isEmpty()) {
                model.addAttribute("error", "Please select a file to upload");
                return "upload";
            }

            // Validasi tipe file
            if (!file.getContentType().equals("application/pdf")) {
                model.addAttribute("error", "Only PDF files are allowed");
                return "upload";
            }

            // Upload file
            PdfFile pdfFile = pdfStorageService.uploadPdf(file);

            // Tambahkan pesan sukses
            model.addAttribute("success", "File uploaded successfully!");
            model.addAttribute("pdfFile", pdfFile);

        } catch (IOException e) {
            model.addAttribute("error", "Error occurred while uploading the file: " + e.getMessage());
            return "upload";
        }

        return "upload"; // Tetap di halaman upload setelah upload berhasil
    }
    
    @GetMapping("/delete/{fileName}")
    @ResponseBody
    public String deletePdfPost(@PathVariable("fileName") String fileName) {
        try {
            pdfStorageService.deletePdf(fileName);
            return "home";
        } catch (Exception e) {
            //TODO: Implementasi lagi error handling untuk delet file
            e.printStackTrace();
            return "home";
        }
    }
}