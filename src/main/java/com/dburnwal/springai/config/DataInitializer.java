package com.dburnwal.springai.config;

import com.dburnwal.springai.service.RAGService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RAGService ragService;

    @Override
    public void run(String... args) {
        // Check if documents already exist to avoid duplicates
        if (ragService.hasDocuments()) {
            log.info("Documents already exist in the vector store. Skipping initialization.");
            return;
        }

        // Sample documents to initialize the vector store
        List<Document> documents = List.of(
            new Document("Spring AI is an application framework for AI engineering. Its goal is to help you build AI-powered applications using Spring.", 
                Map.of("type", "framework", "category", "introduction")),

            new Document("Spring AI provides abstractions that serve as the foundation for developing AI applications. It offers portable API support for major AI model providers.", 
                Map.of("type", "framework", "category", "features")),

            new Document("Spring AI supports various AI models including chat, text-to-image, embedding, and transcription models. It integrates with providers like OpenAI, Azure OpenAI, and Ollama.", 
                Map.of("type", "framework", "category", "models")),

            new Document("Vector stores in Spring AI provide a way to store and search embeddings. They support various vector databases including Milvus, Pinecone, and Chroma.", 
                Map.of("type", "vector-store", "category", "introduction")),

            new Document("RAG (Retrieval Augmented Generation) is a technique that combines retrieval-based and generative AI. It retrieves relevant documents and uses them as context for generating responses.", 
                Map.of("type", "technique", "category", "rag")),

            new Document("Chroma is an open-source vector database built for scalable similarity search and AI applications. It supports various index types and can handle billions of vectors.", 
                Map.of("type", "vector-db", "category", "chroma")),

            new Document("Ollama is a tool for running large language models locally. It supports models like Llama 2, Mistral, and can be integrated with Spring AI through the Ollama starter.", 
                Map.of("type", "llm", "category", "ollama"))
        );

        // Add documents to the vector store
        ragService.addDocuments(documents);
        log.info("Successfully initialized vector store with sample documents.");
    }
}
