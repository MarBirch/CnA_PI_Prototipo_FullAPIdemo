package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    @Query("SELECT i FROM Ingrediente i WHERE i.cardapio.id = :cardapioId")
    ArrayList<Ingrediente> findByCardapioId(@Param("cardapioId") Long cardapioId);
}