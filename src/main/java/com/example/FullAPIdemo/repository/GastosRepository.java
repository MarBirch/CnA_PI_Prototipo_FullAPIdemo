package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Gastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastosRepository extends JpaRepository<Gastos, Long> {
}
