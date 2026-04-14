package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoIngredienteRepository extends JpaRepository<PedidoIngrediente, Long> {
}
