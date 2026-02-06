package com.dburnwal.springai.controller;

import com.dburnwal.springai.service.RAGService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final RAGService ragService;

    @Autowired
    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    /**
     * Query endpoint for RAG
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, String>> query(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        String response = ragService.query(query);
        return ResponseEntity.ok(Map.of(
            "query", query,
            "response", response
        ));
    }

    /**
     * Add documents to the vector store
     */
    @PostMapping("/documents")
    public ResponseEntity<Map<String, String>> addDocuments(@RequestBody List<Map<String, String>> documents) {
        List<Document> docs = documents.stream()
            .map(doc -> new Document(doc.get("content"), Map.of("metadata", doc.get("metadata"))))
            .toList();

        ragService.addDocuments(docs);
        return ResponseEntity.ok(Map.of("message", "Documents added successfully"));
    }

    /**
     * Similarity search endpoint
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int topK) {
        List<Document> documents = ragService.similaritySearch(query, topK);
        return ResponseEntity.ok(Map.of(
            "query", query,
            "results", documents
        ));
    }
}
