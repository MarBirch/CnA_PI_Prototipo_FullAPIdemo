package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Message;
import com.example.FullAPIdemo.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Fetches history for a specific chat, ordered chronologically
    @NativeQuery(value = "SELECT * FROM pedido WHERE user_id = ?1 ORDER BY created_at ASC")
    List<Pedido> findByUserIdOrderByCreatedAtAsc(Long userId);

}