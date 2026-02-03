
package com.dburnwal.springai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ChatClient chatClient;

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
                .advisors(a -> a
                        .param("temperature", 0.7)
                )
                .call()
                .content();
    }
}
