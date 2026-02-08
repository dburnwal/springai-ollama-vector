package com.dburnwal.springai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @GetMapping("/rag-demo")
    public String ragDemo() {
        return "rag-demo";
    }
}
