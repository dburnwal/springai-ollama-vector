package com.dburnwal.springai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    @Value("classpath:/prompts/rag-prompt-template.st")
    private Resource ragPromptTemplate;


    /**
     * Add documents to the vector store with automatic chunking
     */
    public void addDocuments(List<Document> documents) {
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(100, 10, 5, 1000, true);
        List<Document> splitDocuments = tokenTextSplitter.apply(documents);
        vectorStore.add(splitDocuments);
    }

    /**
     * Perform RAG (Retrieval Augmented Generation) query
     */
    public String query(String userQuery) {
        // Retrieve relevant documents
        List<Document> similarDocuments = vectorStore.similaritySearch(userQuery);

        // Prepare the prompt with retrieved context
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);

        Map<String, Object> promptVariables = new HashMap<>();
        promptVariables.put("input", userQuery);
        promptVariables.put("documents", similarDocuments);

        Prompt prompt = promptTemplate.create(promptVariables);

        // Generate response using the chat model

        return chatClient.prompt(prompt).call().content();
    }

    /**
     * Simple similarity search without generation
     */
    public List<Document> similaritySearch(String query, int topK) {
        return vectorStore.similaritySearch(
            SearchRequest.builder().query(query).topK(topK).build()
        );
    }

    /**
     * Check if documents exist in the vector store
     */
    public boolean hasDocuments() {
        // Try to search for documents to check if the store is populated
        List<Document> results = vectorStore.similaritySearch(
            SearchRequest.builder().query("test").topK(1).build()
        );
        return !results.isEmpty();
    }
}
