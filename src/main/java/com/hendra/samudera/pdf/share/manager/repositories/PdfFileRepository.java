package com.hendra.samudera.pdf.share.manager.repositories;

import com.hendra.samudera.pdf.share.manager.models.PdfFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class PdfFileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<PdfFile> pdfFileRowMapper = new RowMapper<PdfFile>() {
        @Override
        public PdfFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            PdfFile pdfFile = new PdfFile();
            pdfFile.setId(rs.getString("id"));
            pdfFile.setFileName(rs.getString("file_name"));
            pdfFile.setOriginalFileName(rs.getString("original_file_name"));
            pdfFile.setContentType(rs.getString("content_type"));
            pdfFile.setSize(rs.getLong("file_size"));
            pdfFile.setDownloadUrl(rs.getString("download_url"));
            pdfFile.setThumbnailUrl(rs.getString("thumbnail_url")); // Ambil thumbnail_url dari hasil query
            pdfFile.setUploadDate(rs.getTimestamp("upload_date").toString());
            pdfFile.setUploadedBy(rs.getString("uploaded_by"));
            return pdfFile;
        }
    };

    public void save(PdfFile pdfFile) {
        // Perbarui query INSERT untuk memasukkan thumbnail_url
        String sql = "INSERT INTO pdf_files (id, file_name, original_file_name, content_type, file_size, download_url, thumbnail_url, uploaded_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                pdfFile.getId(),
                pdfFile.getFileName(),
                pdfFile.getOriginalFileName(),
                pdfFile.getContentType(),
                pdfFile.getSize(),
                pdfFile.getDownloadUrl(),
                pdfFile.getThumbnailUrl(), // Tambahkan thumbnail_url
                pdfFile.getUploadedBy());
    }

    public List<PdfFile> findByUploadedBy(String username) {
        String sql = "SELECT * FROM pdf_files WHERE uploaded_by = ? ORDER BY upload_date DESC";
        return jdbcTemplate.query(sql, pdfFileRowMapper, username);
    }

    public PdfFile findById(String id) {
        String sql = "SELECT * FROM pdf_files WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, pdfFileRowMapper, id);
    }
    
    public void deleteByFileName(String fileName) {
        String sql = "DELETE FROM pdf_files WHERE file_name = ?";
        jdbcTemplate.update(sql, fileName);
    }
}