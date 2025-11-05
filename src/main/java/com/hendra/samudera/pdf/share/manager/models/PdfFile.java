package com.hendra.samudera.pdf.share.manager.models;

import java.time.LocalDateTime;

public class PdfFile {

    private String id;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private long size;
    private String downloadUrl;
    private String uploadDate;
    private String uploadedBy;

    // Default constructor
    public PdfFile() {
    }

    // Constructor
    public PdfFile(String id, String fileName, String originalFileName, String contentType, long size, String downloadUrl, String uploadDate, String uploadedBy) {
        this.id = id;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}