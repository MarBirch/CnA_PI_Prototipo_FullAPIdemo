package com.example.FullAPIdemo.model.ai.dto;

public class PromptRequest {
    private String prompt;
    private Long chatId;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
