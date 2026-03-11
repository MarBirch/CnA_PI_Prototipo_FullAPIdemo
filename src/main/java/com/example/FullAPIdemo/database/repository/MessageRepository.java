package com.example.FullAPIdemo.database.repository;

import com.example.FullAPIdemo.database.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Fetches history for a specific chat, ordered chronologically
    @NativeQuery(value = "SELECT * FROM chat_messages WHERE chat_id = '?1' ORDER BY created_at ASC")
    ArrayList<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);

    // Optional: Delete history if you need to reset a conversation
    void deleteByChatId(Long chatId);
}