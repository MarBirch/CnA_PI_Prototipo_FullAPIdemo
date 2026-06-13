package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoIngredienteRepository extends JpaRepository<PedidoIngrediente, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM pedido_ingrediente WHERE pedido_id = ?1", nativeQuery = true)
    void deleteByPedidoId(Long pedido_id);
}
