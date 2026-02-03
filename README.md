# Ollama Demo

A Spring Boot application that demonstrates integration with Ollama AI using Spring AI framework. This application provides a simple chat interface where users can ask questions and receive AI-generated responses.

## Features

- Interactive web-based chat interface
- Integration with Ollama AI models through Spring AI
- Chat memory support for maintaining conversation context
- Markdown rendering for AI responses
- Clean and responsive UI built with Thymeleaf

## Technology Stack

- Java 21
- Spring Boot 3.3.5
- Spring AI 1.0.3
- Spring Web
- Thymeleaf (Server-side template engine)
- Spring AI Ollama Starter
- Maven (Build tool)
- Showdown.js (Markdown to HTML converter)

## Prerequisites

Before running this application, ensure you have:

- Java Development Kit (JDK) 21 or higher
- Maven 3.6 or higher
- Ollama installed and running on your machine
- An Ollama model pulled and ready to use (e.g., llama2, mistral, etc.)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ollama-demo
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

# Ollama configuration
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama2
```

Note: Replace `llama2` with your preferred Ollama model.

### 4. Build and Run the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Alternatively, you can run the JAR file:

```bash
java -jar target/ollama-demo-0.0.1-SNAPSHOT.jar
```

## Using the Application

1. Open your web browser and navigate to: `http://localhost:8080/showAskAnything`
2. Type your question in the input field
3. Click the "Ask" button
4. The AI response will be displayed below your question with proper Markdown formatting

## Project Structure

```
ollama-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dburnwal/springai/
│   │   │       ├── OllamaDemoApplication.java    # Main application class
│   │   │       ├── controller/
│   │   │       │   └── AnswerAnyThingController.java  # Web controller
│   │   │       └── service/
│   │   │           └── OllamaService.java        # AI service layer
│   │   └── resources/
│   │       └── templates/
│   │           └── askAnything.html              # Chat interface template
│   └── test/
│       └── java/
└── pom.xml                                       # Maven configuration
```

## Key Components

### OllamaDemoApplication
The main Spring Boot application class that initializes the application context.

### AnswerAnyThingController
A Spring MVC controller that handles HTTP requests:
- GET `/showAskAnything` - Displays the chat interface
- POST `/askAnything` - Processes user questions and returns AI responses

### OllamaService
Service class that interacts with Ollama AI:
- Uses Spring AI's ChatClient for generating responses
- Implements chat memory using InMemoryChatMemory
- Maintains conversation context across multiple requests

### askAnything.html
Thymeleaf template for the chat interface:
- Provides a clean UI for asking questions
- Displays AI responses with Markdown rendering
- Uses Showdown.js for client-side Markdown to HTML conversion

## Configuration Options

You can customize the application behavior through `application.properties`:

```properties
# Change the server port
server.port=8080

# Configure Ollama connection
spring.ai.ollama.base-url=http://localhost:11434

# Select a different model
spring.ai.ollama.chat.options.model=mistral

# Adjust response parameters
spring.ai.ollama.chat.options.temperature=0.7
spring.ai.ollama.chat.options.max-tokens=1000
```

## Troubleshooting

### Ollama Connection Issues
- Ensure Ollama service is running: `ollama serve`
- Verify the base URL in application.properties matches your Ollama installation
- Check that the specified model is available: `ollama list`

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
- Showdown.js for the Markdown to HTML conversion library
