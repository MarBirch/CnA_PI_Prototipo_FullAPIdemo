package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Fetches history for a specific chat, ordered chronologically
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.createdAt ASC")
    ArrayList<Message> findByChatIdOrderByCreatedAtAsc(@Param("chatId") Long chatId);
}