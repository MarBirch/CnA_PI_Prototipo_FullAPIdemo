package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Gastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastosRepository extends JpaRepository<Gastos, Long> {

    List<Gastos> findByMarmiteriaId(Long marmiteriaId);

    List<Gastos> findByMarmiteriaIdAndCategoriaIgnoreCase(Long marmiteriaId, String categoria);

    List<Gastos> findByMarmiteriaIdAndDataBetween(Long marmiteriaId, LocalDate inicio, LocalDate fim);

    List<Gastos> findByMarmiteriaIdAndCategoriaIgnoreCaseAndDataBetween(
            Long marmiteriaId, String categoria, LocalDate inicio, LocalDate fim);
}