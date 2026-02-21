package com.example.FullAPIdemo.ai;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/apiOllama")
public class ollamaController {
    private final ChatClient chatClient;

    public ollamaController(OllamaChatModel chatModel){
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        this.chatClient = builder
                .defaultSystem("Voce é uma ia de assistente nutricional")
                .build();
    }

    @GetMapping("/ollama")
    public Flux<String> ollama(){
        return chatClient.prompt().
                user("how many calories are there in one strawberry").
                stream().
                content();
    }

    @PostMapping(value="/prompt")
    public String ollamaPrompt(@RequestBody String prompt){
        //get user history
        //get prompt
        //add to user history
        //add to prompt context
        //return answer as Message json
        return chatClient.prompt().
                user(prompt).
                call().
                content();
    }

    //Mapping /chat/CHATID/messages
    //https://codezup.com/building-real-time-chat-app-with-flutter/
}
