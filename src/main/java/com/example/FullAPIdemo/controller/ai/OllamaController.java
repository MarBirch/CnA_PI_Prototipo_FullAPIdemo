package com.example.FullAPIdemo.controller.ai;

import com.example.FullAPIdemo.model.dto.ai.ChatRequest;
import com.example.FullAPIdemo.model.dto.ai.PromptRequest;
import com.example.FullAPIdemo.model.entity.Chat;
import com.example.FullAPIdemo.model.entity.Message;
import com.example.FullAPIdemo.model.entity.User;
import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.repository.ChatRepository;
import com.example.FullAPIdemo.repository.MessageRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import com.example.FullAPIdemo.service.OllamaService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import tools.jackson.databind.ObjectMapper;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/apiOllama")
public class OllamaController {
    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ChatRepository cRepo;

    @Autowired
    private MessageRepository mRepo;

    @Autowired
    private OllamaService ollamaService;

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder builder, ChatMemory chatMemory){

        this.chatClient = builder
                .defaultSystem("Voce é uma IA de assostente nutricional")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @GetMapping("/ollama")
    public Flux<String> ollama(){
        return chatClient.prompt().
                user("how many calories are there in one strawberry").
                stream().
                content();
    }

    @PostMapping("/chat/messages")
    public ResponseEntity<String> listChat(@RequestBody @Valid ChatRequest chatRequest){
        return ollamaService.listChat(chatRequest);
    }

    @PostMapping("/chatlist")
    public ResponseEntity<String> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        return ollamaService.listUserChats(chatRequest); }

    @PostMapping("/chat")
    public ResponseEntity<String> newChat(@RequestBody @Valid ChatRequest chatRequest){
        return ollamaService.newChat(chatRequest, this.chatClient);
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> ollamaPrompt(@RequestBody @Valid PromptRequest promptRequest){
        return ollamaService.ollamaPrompt(promptRequest, this.chatClient);
     }

    record Input(@NotNull String prompt){}
    record Output(String content) {}


}
