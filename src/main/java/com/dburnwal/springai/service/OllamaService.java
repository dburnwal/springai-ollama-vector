
package com.dburnwal.springai.service;

import com.dburnwal.springai.tools.CalculatorService;
import com.dburnwal.springai.tools.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ChatClient chatClient;
    private final WeatherService weatherService;
    private final CalculatorService calculatorService;

    public ChatResponse generateAnswer(String question) {
        return chatClient.prompt(question).call().chatResponse();
    }

    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public String chatWithTools(String message) {
        return chatClient.prompt()
                .user(message)
                .tools(weatherService, calculatorService)
                .call()
                .content();
    }
}
