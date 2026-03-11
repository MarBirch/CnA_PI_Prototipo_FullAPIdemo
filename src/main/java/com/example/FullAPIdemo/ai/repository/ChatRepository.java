package com.example.FullAPIdemo.ai.repository;

import com.example.FullAPIdemo.ai.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.ArrayList;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    //order this by created at later
    @NativeQuery(value = "SELECT * FROM chat WHERE user_id = ?1")
    ArrayList<Chat> findByUserId(Long chatId);

}
