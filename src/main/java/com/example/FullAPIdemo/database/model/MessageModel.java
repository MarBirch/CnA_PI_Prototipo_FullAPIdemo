package com.example.FullAPIdemo.database.model;

import java.time.Instant;

public class MessageModel {
    private String role;     // "user" or "assistant"
        private String content;
        private Instant timestamp;

        public MessageModel(String role, String content) {
            this.role = role;
            this.content = content;
            this.timestamp = Instant.now();
        }
}
