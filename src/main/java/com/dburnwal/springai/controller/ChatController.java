
package com.dburnwal.springai.controller;

import com.dburnwal.springai.service.OllamaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final OllamaService ollamaService;

    @PostMapping
    public String chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            return "Please provide a message";
        }
        return ollamaService.chat(message);
    }

    @PostMapping("/with-tools")
    public String chatWithTools(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            return "Please provide a message";
        }
        return ollamaService.chatWithTools(message);
    }
}
