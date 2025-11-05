package com.hendra.samudera.pdf.share.manager.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.hendra.samudera.pdf.share.manager.models.PdfFile;
import com.hendra.samudera.pdf.share.manager.repositories.PdfFileRepository;

@Service
public class PdfStorageService {

    @Autowired
    private Storage storage;

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @Value("${gcp.storage.bucket-name}")
    private String bucketName;

    @Value("${gcp.storage.public-access:true}")
    private boolean publicAccess;

    public PdfFile uploadPdf(MultipartFile file) throws IOException {
        // Validasi file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        // Dapatkan nama file asli
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        // Generate unique file name using UUID
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        String fileId = fileName.split("_")[0]; // Ambil UUID sebagai ID

        // Upload file ke GCS
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(file.getContentType())
                .build();

        // Upload file ke GCS
        com.google.cloud.storage.Blob blob = storage.create(blobInfo, file.getBytes());
        
        // Jika public access diaktifkan, beri akses publik ke file
        if (publicAccess) {
            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        }

        // Dapatkan informasi user yang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uploadedBy = authentication != null ? authentication.getName() : "anonymous";

        // Format tanggal upload
        String uploadDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Buat URL untuk mengakses file
        String downloadUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);

        // Buat objek PdfFile
        PdfFile pdfFile = new PdfFile();
        pdfFile.setId(fileId);
        pdfFile.setFileName(fileName);
        pdfFile.setOriginalFileName(originalFileName);
        pdfFile.setContentType(file.getContentType());
        pdfFile.setSize(file.getSize());
        pdfFile.setDownloadUrl(downloadUrl);
        pdfFile.setUploadDate(uploadDate);
        pdfFile.setUploadedBy(uploadedBy);

        // Simpan metadata ke database
        pdfFileRepository.save(pdfFile);

        return pdfFile;
    }

    public String getDownloadUrl(String fileName) {
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
    
    public void deletePdf(String fileName) {
        // Hapus file dari GCS
        boolean deletedFromGcs = storage.delete(bucketName, fileName);
        
        if (deletedFromGcs) {
            // Jika berhasil dihapus dari GCS, hapus juga dari database
            pdfFileRepository.deleteByFileName(fileName);
        } else {
            throw new RuntimeException("Failed to delete file from Google Cloud Storage: " + fileName);
        }
    }
}