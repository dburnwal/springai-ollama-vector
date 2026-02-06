# OllamaVectorHub

A Spring Boot application that demonstrates integration with Ollama AI using Spring AI framework. This application provides multiple interfaces for interacting with AI models, including chat, RAG (Retrieval Augmented Generation), and document querying capabilities.

## Features

- Interactive web-based chat interface
- RAG (Retrieval Augmented Generation) with ChromaDB vector store
- Integration with Ollama AI models through Spring AI
- Chat memory support for maintaining conversation context
- Markdown rendering for AI responses
- Clean and responsive UI built with Thymeleaf
- Automatic document initialization with duplicate prevention
- REST API endpoints for document management and querying

## Technology Stack

- Java 21
- Spring Boot 3.3.5
- Spring AI 1.1.2
- Spring Web
- Thymeleaf (Server-side template engine)
- Spring AI Ollama Starter
- Spring AI Chroma Vector Store Starter
- ChromaDB (Vector database)
- Maven (Build tool)
- Showdown.js (Markdown to HTML converter)

## Prerequisites

Before running this application, ensure you have:

- Java Development Kit (JDK) 21 or higher
- Maven 3.6 or higher
- Ollama installed and running on your machine
- ChromaDB installed and running (for RAG features)
- An Ollama model pulled and ready to use (e.g., llama2, mistral, nomic-embed-text)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd spring-ollama-vector
```

### 2. Install and Configure Ollama

If you haven't installed Ollama yet:

```bash
# On macOS
brew install ollama

# Start Ollama service
ollama serve
```

Pull a model (for example, llama2):

```bash
ollama pull llama2
```

### 3. Configure Application Properties

Create `src/main/resources/application.properties` with the following configuration:

```properties
# Server configuration
server.port=8080

# ChromaDB Vector Store Configuration
spring.ai.vectorstore.chroma.client.host=http://localhost
spring.ai.vectorstore.chroma.client.port=8000
spring.ai.vectorstore.chroma.collection-name=documents
spring.ai.vectorstore.chroma.initialize-schema=true

# Ollama configuration
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=mistral
spring.ai.ollama.embedding.options.model=nomic-embed-text
```

Note: Replace `mistral` and `nomic-embed-text` with your preferred Ollama models.

### 4. Build and Run the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Alternatively, you can run the JAR file:

```bash
java -jar target/spring-ollama-vector-0.0.1-SNAPSHOT.jar
```

## Using the Application

The application provides multiple interfaces:

### Chat Interface
1. Open your web browser and navigate to: `http://localhost:8080/`
2. Type your question in the input field
3. Click the "Send" button
4. The AI response will be displayed below your question with proper Markdown formatting

### RAG Demo Interface
1. Open your web browser and navigate to: `http://localhost:8080/rag-demo`
2. Type your question in the input field
3. Click the "Submit" button
4. The AI will retrieve relevant documents from ChromaDB and generate a response based on that context

### Ask Anything Interface
1. Open your web browser and navigate to: `http://localhost:8080/showAskAnything`
2. Type your question in the input field
3. Click the "Ask" button
4. The AI response will be displayed below your question with proper Markdown formatting

## Project Structure

```
spring-ollama-vector/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dburnwal/springai/
│   │   │       ├── OllamaDemoApplication.java    # Main application class
│   │   │       ├── config/
│   │   │       │   ├── ChatClientConfig.java     # Chat client configuration
│   │   │       │   ├── ChromaConfig.java         # ChromaDB configuration
│   │   │       │   ├── DataInitializer.java     # Initializes sample documents with duplicate prevention
│   │   │       │   └── DocumentLoaderConfig.java # Document loader configuration
│   │   │       ├── controller/
│   │   │       │   ├── AnswerAnyThingController.java  # Ask Anything web controller
│   │   │       │   ├── ChatController.java      # Chat API controller
│   │   │       │   ├── RAGController.java       # RAG API controller
│   │   │       │   └── WebController.java       # Web page controller
│   │   │       └── service/
│   │   │           ├── OllamaService.java       # Ollama AI service
│   │   │           └── RAGService.java          # RAG service with document management
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── askAnything.html             # Ask Anything interface
│   │       │   ├── chat.html                    # Chat interface
│   │       │   └── rag-demo.html                # RAG demo interface
│   │       └── application.properties           # Application configuration
│   └── test/
│       └── java/
└── pom.xml                                       # Maven configuration
```

## Key Components

### OllamaDemoApplication
The main Spring Boot application class that initializes the application context.

### Controllers

#### WebController
Handles web page navigation:
- GET `/` - Displays the chat interface
- GET `/rag-demo` - Displays the RAG demo interface

#### RAGController
REST API controller for RAG operations:
- POST `/api/rag/query` - Query with RAG
- POST `/api/rag/documents` - Add documents to the vector store
- GET `/api/rag/search` - Perform similarity search

#### ChatController
REST API controller for chat operations:
- POST `/api/chat` - Send a chat message
- POST `/api/chat/with-tools` - Send a chat message with tool support

#### AnswerAnyThingController
Web controller for the Ask Anything feature:
- GET `/showAskAnything` - Displays the Ask Anything interface
- POST `/askAnything` - Processes user questions and returns AI responses

### Services

#### OllamaService
Service class that interacts with Ollama AI:
- Uses Spring AI's ChatClient for generating responses
- Implements chat memory using InMemoryChatMemory
- Maintains conversation context across multiple requests

#### RAGService
Service class that implements RAG functionality:
- Manages documents in ChromaDB vector store
- Checks for existing documents to prevent duplicates
- Performs similarity search and context-aware generation

### Configuration

#### ChromaConfig
Configures ChromaDB vector store:
- Creates the collection if it doesn't exist
- Sets up connection parameters

#### DataInitializer
Initializes the application with sample documents:
- Checks if documents already exist before adding
- Prevents duplicate data on application restart

### Templates

#### chat.html
Thymeleaf template for the chat interface:
- Provides a clean UI for chat interactions
- Displays AI responses with Markdown rendering

#### rag-demo.html
Thymeleaf template for the RAG demo interface:
- Provides a UI for RAG queries
- Shows retrieved documents alongside AI responses

#### askAnything.html
Thymeleaf template for the Ask Anything interface:
- Provides a clean UI for asking questions
- Displays AI responses with Markdown rendering
- Uses Showdown.js for client-side Markdown to HTML conversion

## Configuration Options

You can customize the application behavior through `application.properties`:

```properties
# Change the server port
server.port=8080

# ChromaDB Vector Store Configuration
spring.ai.vectorstore.chroma.client.host=http://localhost
spring.ai.vectorstore.chroma.client.port=8000
spring.ai.vectorstore.chroma.collection-name=documents
spring.ai.vectorstore.chroma.initialize-schema=true

# Configure Ollama connection
spring.ai.ollama.base-url=http://localhost:11434

# Select a different model
spring.ai.ollama.chat.options.model=mistral
spring.ai.ollama.embedding.options.model=nomic-embed-text

# Adjust response parameters
spring.ai.ollama.chat.options.temperature=0.7
spring.ai.ollama.chat.options.max-tokens=1000
```

## Troubleshooting

### ChromaDB Connection Issues
- Ensure ChromaDB is running on port 8000
- Verify the host and port configuration in application.properties
- Check if the collection exists in ChromaDB

### Ollama Connection Issues
- Ensure Ollama service is running: `ollama serve`
- Verify the base URL in application.properties matches your Ollama installation
- Check that the specified model is available: `ollama list`

### Document Initialization Issues
- If documents are not being initialized, check the application logs
- The application will skip initialization if documents already exist
- To force re-initialization, clear the collection in ChromaDB

### Build Issues
- Ensure you're using Java 21 or higher: `java -version`
- Verify Maven is properly installed: `mvn -version`
- Try cleaning the project: `mvn clean install`

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.

## Acknowledgments

- Spring AI team for the amazing AI integration framework
- Ollama for providing an easy-to-use local LLM platform
- ChromaDB for the open-source vector database
- Showdown.js for the Markdown to HTML conversion library
