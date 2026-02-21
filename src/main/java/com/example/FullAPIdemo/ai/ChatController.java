package com.example.FullAPIdemo.ai;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final RestTemplate restTemplate = new RestTemplate();

//    @PostMapping
//    public Map<String, Object> chat (@RequestBody Map<String, String> body){
//        String userMessage = body.get("message");
//
//        Map<String, Object> request = new HashMap<>();
//                request.put("model", "deepseek");
//                request.put("prompt", userMessage);
//
//        String response = ollamaPrompt(userMessage);
//    }
}
