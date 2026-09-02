package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.user.id = :userId")
    ArrayList<Chat> findByUserId(@Param("userId") Long userId);

    /**
     * Repository interface for Cardapio entity.
     */
    @Repository
    interface CardapioRepository extends JpaRepository<Cardapio, Long> {
    }
}
