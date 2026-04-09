package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Ingrediente;
import com.example.FullAPIdemo.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    // Fetches history for a specific chat, ordered chronologically
    @NativeQuery(value = "SELECT * FROM ingrediente WHERE pedido_id = ?1")
    ArrayList<Ingrediente> findByPedidoId(Long pedidoId);
}