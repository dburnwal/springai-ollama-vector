package com.dburnwal.springai.config;

import com.dburnwal.springai.service.DocumentLoaderService;
import com.dburnwal.springai.service.RAGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Slf4j
public class DocumentLoaderConfig {

    @Value("classpath:documents/spring-ai-guide.txt")
    private Resource springAIGuide;

    @Bean
    public CommandLineRunner loadExternalDocuments(RAGService ragService, DocumentLoaderService documentLoaderService) {
        return args -> {
            if (ragService.hasDocuments()) {
                log.info("Vector store already contains documents. Skipping external document loading.");
                return;
            }

            if (springAIGuide.exists()) {
                log.info("Loading external documents from: {}", springAIGuide.getFilename());
                documentLoaderService.loadDocumentsFromResource(springAIGuide);
            } else {
                log.warn("External document resource not found at the specified path.");
            }
        };
    }
}
