package com.dburnwal.springai.config;

import com.dburnwal.springai.service.RAGService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class DocumentLoaderConfig {

    @Value("classpath:documents/spring-ai-guide.txt")
    private Resource springAIGuide;

    @Bean
    public List<Document> loadDocuments(RAGService ragService) {
        TextReader textReader = new TextReader(springAIGuide);
        List<Document> documents = textReader.get();
        // Add documents to vector store
        ragService.addDocuments(documents);

        return documents;
    }
}
