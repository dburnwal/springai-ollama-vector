package com.dburnwal.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpController {

    private final ChatClient chatClient;

    public McpController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/mcp")
    public String mcp(@RequestParam(value = "prompt", defaultValue = "Tell me a joke") String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
