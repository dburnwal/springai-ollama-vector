# OllamaVectorHub - Spring AI RAG Implementation with ChromaDB

This project demonstrates how to implement Retrieval Augmented Generation (RAG) using Spring AI 1.1.2 with ChromaDB, an open-source vector database.

## Overview

This implementation includes:
- RAG service for querying documents with context-aware responses
- ChromaDB vector store for efficient document retrieval
- Ollama integration for local LLM and embeddings
- REST API endpoints for document management and querying
- Automatic initialization with sample documents
- Duplicate prevention on application restart

## Prerequisites

1. **Java 21** or higher
2. **Maven** for dependency management
3. **ChromaDB Vector Database** - Install and run Milvus locally or use Docker:
   ```bash
   docker run -d --name milvus-standalone      -p 19530:19530      -p 9091:9091      -v ${PWD}/volumes/milvus:/var/lib/milvus      milvusdb/milvus:latest
   ```

4. **Ollama** - Install Ollama and pull the required models:
   ```bash
   # Install Ollama from https://ollama.ai
   ollama pull mistral
   ollama pull nomic-embed-text
   ```

## Configuration

The application is configured in `src/main/resources/application.properties`:

```properties
# Milvus Vector Store Configuration
spring.ai.vectorstore.milvus.client.host=localhost
spring.ai.vectorstore.milvus.client.port=19530
spring.ai.vectorstore.milvus.collection-name=documents
spring.ai.vectorstore.milvus.initialize-schema=true

# Ollama Configuration
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=mistral
spring.ai.ollama.embedding.options.model=nomic-embed-text
```

## Project Structure

```
src/main/java/com/dburnwal/springai/
├── config/
│   ├── DataInitializer.java      # Initializes sample documents
│   └── SpringAIConfig.java       # Spring AI configuration
├── controller/
│   └── RAGController.java         # REST API endpoints
└── service/
    ├── DocumentLoaderService.java # Document loading utilities
    └── RAGService.java           # Core RAG implementation
```

## API Endpoints

### 1. Query with RAG
```bash
POST /api/rag/query
Content-Type: application/json

{
  "query": "What is Spring AI?"
}
```

### 2. Add Documents
```bash
POST /api/rag/documents
Content-Type: application/json

[
  {
    "content": "Your document content here",
    "metadata": "Optional metadata"
  }
]
```

### 3. Similarity Search
```bash
GET /api/rag/search?query=Spring%20AI&topK=5
```

## How RAG Works

1. **Document Ingestion**: Documents are split into chunks and converted to embeddings using Ollama's embedding model
2. **Vector Storage**: Embeddings are stored in Milvus vector database
3. **Query Processing**: When a query is received:
   - The query is converted to an embedding
   - Similar documents are retrieved from Milvus
   - Retrieved documents are used as context for the LLM
   - The LLM generates a response based on the context

## Running the Application

1. Start Milvus:
   ```bash
   docker run -d --name milvus-standalone      -p 19530:19530      -p 9091:9091      milvusdb/milvus:latest
   ```

2. Start Ollama:
   ```bash
   ollama serve
   ```

3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. Test the API:
   ```bash
   curl -X POST http://localhost:8080/api/rag/query      -H "Content-Type: application/json"      -d '{"query": "What is Spring AI?"}'
   ```

## Customization

### Adding Custom Documents

You can add your own documents through the REST API or by modifying the `DataInitializer` class:

```java
List<Document> customDocuments = List.of(
    new Document("Your custom content here", 
        Map.of("type", "custom", "category", "general"))
);
ragService.addDocuments(customDocuments);
```

### Adjusting Retrieval Parameters

Modify the search parameters in `RAGService.java`:

```java
List<Document> similarDocuments = vectorStore.similaritySearch(
    SearchRequest.query(userQuery)
        .withTopK(4)  // Number of documents to retrieve
        .withSimilarityThreshold(0.75)  // Minimum similarity score
);
```

## Troubleshooting

### Milvus Connection Issues
- Ensure Milvus is running on port 19530
- Check firewall settings
- Verify the host and port configuration

### Ollama Model Issues
- Ensure models are pulled: `ollama pull mistral` and `ollama pull nomic-embed-text`
- Check Ollama is running: `ollama serve`

### Memory Issues
- Adjust JVM heap size: `-Xmx2g`
- Reduce batch size for document ingestion

## Additional Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Milvus Documentation](https://milvus.io/docs)
- [Ollama Documentation](https://github.com/ollama/ollama)

## License

This project is provided as-is for educational purposes.
