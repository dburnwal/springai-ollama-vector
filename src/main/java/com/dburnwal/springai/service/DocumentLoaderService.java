package com.dburnwal.springai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentLoaderService {

    private final RAGService ragService;

    public DocumentLoaderService(RAGService ragService) {
        this.ragService = ragService;
    }

    /**
     * Load documents from a text file resource
     */
    public void loadDocumentsFromResource(Resource resource) {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();
        ragService.addDocuments(documents);
    }

    /**
     * Load a list of documents directly
     */
    public void loadDocuments(List<Document> documents) {
        ragService.addDocuments(documents);
    }
}
