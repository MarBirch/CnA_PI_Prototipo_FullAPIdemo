package com.example.FullAPIdemo.service.ai;

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
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
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

    public ResponseEntity<String> listChat(@RequestBody @Valid ChatRequest chatRequest){
        ArrayList<Message> list = mRepo.findByChatIdOrderByCreatedAtAsc(chatRequest.getChatId());
        for(Message m : list){
            //list.add(m.getContent());
            System.out.println(m.getContent());
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(jsonList);

        return ResponseEntity.ok().body(jsonList);
    }

    public ResponseEntity<String> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        ArrayList<Chat> list = cRepo.findByUserId(uRepo.findIdByUsername(chatRequest.getUsername()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(jsonList);

        return ResponseEntity.ok().body(jsonList);
    }

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

                String generatedTitle = generateChatTitle(prompt, chatClient);
                chat.setTitle(generatedTitle);
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
                mRepo.save(res);


                return ResponseEntity.ok().body(response);
            } else {
                Chat chat = cRepo.getReferenceById(chatId);
                if (chat.getTitle() == null || chat.getTitle().isBlank()) {
                    String generatedTitle = generateChatTitle(prompt, chatClient);
                    chat.setTitle(generatedTitle);
                    cRepo.save(chat);
                }

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
                mRepo.save(res);

                return ResponseEntity.ok().body("\"response\":\"" + response+"\",role\":\"ASSISTANT\"");
            }

        } catch (NoSuchElementException e) {
            System.out.printf("Username doesn't exist");
            return ResponseEntity.ok().body("username errorrr");
        }
    }

    public String generateChatTitle(String userPrompt, ChatClient chatClient) {
        try {
            String systemInstruction = "Gere um título muito curto (2 a 5 palavras no máximo) em português brasileiro que resuma o assunto da pergunta a seguir. Responda APENAS com o título, sem aspas, sem pontuação extra ou introdução.";
            var titleResponse = chatClient.prompt()
                    .system(systemInstruction)
                    .user(userPrompt)
                    .call()
                    .content();
            if (titleResponse != null && !titleResponse.isBlank()) {
                String cleanTitle = titleResponse.replaceAll("[\"']", "").replaceAll("\\s+", " ").trim();
                if (cleanTitle.length() > 60) {
                    cleanTitle = cleanTitle.substring(0, 60).trim();
                }
                return cleanTitle;
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar título para o chat: " + e.getMessage());
        }
        return "Conversa Nutri";
    }

    public ResponseEntity<String> generateTitleForChat(@RequestBody @Valid ChatRequest chatRequest, ChatClient chatClient) {
        Long chatId = chatRequest.getChatId();
        if (chatId == null) {
            return ResponseEntity.badRequest().body("chatId é obrigatório");
        }
        try {
            Chat chat = cRepo.findById(chatId).orElseThrow();
            String promptToUse = chatRequest.getPrompt();
            if (promptToUse == null || promptToUse.isBlank() || promptToUse.equals("empty")) {
                ArrayList<Message> msgs = mRepo.findByChatIdOrderByCreatedAtAsc(chatId);
                if (!msgs.isEmpty()) {
                    promptToUse = msgs.get(0).getContent();
                } else {
                    promptToUse = "Dúvida sobre nutrição";
                }
            }
            String title = generateChatTitle(promptToUse, chatClient);
            chat.setTitle(title);
            cRepo.save(chat);
            return ResponseEntity.ok().body("{\"chatId\":" + chatId + ",\"title\":\"" + title + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar título: " + e.getMessage());
        }
    }

    public ResponseEntity<String>
    ollamaPrompt(@RequestBody @Valid PromptRequest promptRequest, ChatClient chatClient){

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

        System.out.println("done generating...");
        Message res = new Message();
        res.setCreatedAt(time);
        res.setRole("ASSISTANT");
        res.setChat(chat);
        res.setContent(response);
        mRepo.save(res);

        System.out.println("finished generation\nresponse:\n\n" + response);

        return ResponseEntity.ok().body(response);
    }

}
