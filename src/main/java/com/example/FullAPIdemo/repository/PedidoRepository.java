package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByMarmiteriaId(Long marmiteriaId);
    List<Pedido> findByCardapioId(Long cardapioId);
    List<Pedido> findByUserIdOrderByCreatedAtAsc(Long userId);

}