package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.dto.ai.ChatRequest;
import com.example.FullAPIdemo.model.dto.ai.PromptRequest;
import com.example.FullAPIdemo.model.entity.Chat;
import com.example.FullAPIdemo.model.entity.Message;
import com.example.FullAPIdemo.model.entity.User;
import com.example.FullAPIdemo.repository.ChatRepository;
import com.example.FullAPIdemo.repository.MessageRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import tools.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class OllamaService {
    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ChatRepository cRepo;

    @Autowired
    private MessageRepository mRepo;

    @PostMapping("/chat/messages")
    public ResponseEntity<String> listChat(@RequestBody @Valid ChatRequest chatRequest){
        ArrayList<Message> list = mRepo.findByChatIdOrderByCreatedAtAsc(chatRequest.getChatId());
        
        for(Message m : list){
            //list.add(m.getContent());
            System.out.println(m.getContent());
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonList = mapper.writeValueAsString(list);

        System.out.println(jsonList);

        return ResponseEntity.ok().body(jsonList);
    }

    @PostMapping("/chatlist")
    public ResponseEntity<String> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        ArrayList<Chat> list = cRepo.findByUserId(uRepo.findIdByUsername(chatRequest.getUsername()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonList = mapper.writeValueAsString(list);

        System.out.println(jsonList);

        return ResponseEntity.ok().body(jsonList);
    }

    @PostMapping("/chat")
    public ResponseEntity<String> newChat(@RequestBody @Valid ChatRequest chatRequest, ChatClient chatClient){
        String username = chatRequest.getUsername();
        String prompt = chatRequest.getPrompt();
        Long chatId = chatRequest.getChatId();
        LocalDateTime time = LocalDateTime.now();

        User u = this.uRepo.findByUsername(chatRequest.getUsername());
        try {
            if(chatId == null) {
                Chat chat = new Chat();
                chat.setUser(u);
                cRepo.save(chat);
                Message msg = new Message();
                msg.setCreatedAt(time);
                msg.setRole("USER");
                msg.setChat(chat);
                msg.setContent(prompt);
                mRepo.save(msg);

                var response = chatClient.prompt().
                        user(msg.getContent()).
                        advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chat.getId())).
                        call().
                        content();

                System.out.println("finished generation\nresponse:\n\n" + response);

                Message res = new Message();
                res.setCreatedAt(time);
                res.setChat(chat);
                res.setRole("ASSISTANT");
                res.setContent(response);
                mRepo.save(msg);


                return ResponseEntity.ok().body(response);
            } else {
                Chat chat = cRepo.getReferenceById(chatId);
                Message msg = new Message();
                msg.setCreatedAt(time);
                msg.setRole("USER");
                msg.setChat(chat);
                msg.setContent(prompt);
                mRepo.save(msg);

                var response = chatClient.prompt().
                        user(msg.getContent()).
                        advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chat.getId())).
                        call().
                        content();

                System.out.println("finished generation\nresponse:\n\n" + response);


                return ResponseEntity.ok().body(response);
            }

        } catch (NoSuchElementException e) {
            System.out.printf("Username doesn't exist");
            return ResponseEntity.ok().body("username errorrr");
        }
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> ollamaPrompt(@RequestBody @Valid PromptRequest promptRequest, ChatClient chatClient){

        Long conversationId = promptRequest.getChatId();
        String prompt = promptRequest.getPrompt();
        LocalDateTime time = LocalDateTime.now();

        Chat chat = cRepo.getReferenceById(conversationId);
        Message msg = new Message();
        msg.setCreatedAt(time);
        msg.setRole("USER");
        msg.setChat(chat);
        msg.setContent(prompt);
        mRepo.save(msg);

        System.out.println("generating...");

        var response = chatClient.prompt().
                user(prompt).
                advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).
                call().
                content();

        Message res = new Message();
        msg.setCreatedAt(time);
        msg.setRole("ASSISTANT");
        msg.setChat(chat);
        msg.setContent(prompt);
        mRepo.save(res);

        System.out.println("finished generation\nresponse:\n\n" + response);

        return ResponseEntity.ok().body(response);
    }

}
