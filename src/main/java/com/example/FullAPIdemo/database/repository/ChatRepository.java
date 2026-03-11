package com.example.FullAPIdemo.database.repository;

import com.example.FullAPIdemo.database.model.CadastroRequest;
import com.example.FullAPIdemo.database.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
