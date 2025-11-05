package com.hendra.samudera.pdf.share.manager.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class StorageConfig {

    @Value("${gcp.storage.project-id:#{null}}")
    private String projectId;

    @Bean
    public Storage storage() {
        StorageOptions.Builder builder = StorageOptions.newBuilder();
        
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        
        return builder.build().getService();
    }
}
