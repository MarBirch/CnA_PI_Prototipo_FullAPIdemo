package com.example.FullAPIdemo.ai.control;


import com.example.FullAPIdemo.ai.model.NewChatRequest;
import com.example.FullAPIdemo.ai.model.PromptRequest;
import com.example.FullAPIdemo.database.model.Chat;
import com.example.FullAPIdemo.database.model.User;
import com.example.FullAPIdemo.database.repo.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/apiOllama")
public class OllamaController {
    @Autowired
    private UserRepository uRepo;

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

    @PostMapping("/newChat")
    public ResponseEntity<Output> newChat(@RequestBody @Valid NewChatRequest newChatRequest){
        String username = newChatRequest.getUsername();
        String prompt = newChatRequest.getPrompt();

        List<User> userList = this.uRepo.findByUsernameIs(request.getUsername());
        try{
            Long id = userList.getFirst().getId();
            Chat chat = new Chat();
            chat.setUser(id);

        } catch (NoSuchElementException e){
            System.out.printf("Username doesn't exist");
            return ResponseEntity.ok(new Output("username errorrr"));
        }
    }

    @PostMapping("/prompt")
    public ResponseEntity<Output> ollamaPrompt(@RequestBody @Valid Input input, @CookieValue (name = "X-CONV-ID", required = false) String convId){

        String conversationId = convId == null ? UUID.randomUUID().toString() : convId;
        System.out.println("generating...");

        var response = this.chatClient.prompt().
                user(input.prompt()).
                advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).
                call().
                content();

        ResponseCookie cookie = ResponseCookie.from("X-CONV-ID", conversationId)
                .path("/")
                .maxAge(3600)
                .build();

        System.out.println("finished generation\nresponse:\n\n" + response);

        Output output = new Output(response);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(output);
    }

    record Input(@NotNull String prompt){}
    record Output(String content) {}


}
