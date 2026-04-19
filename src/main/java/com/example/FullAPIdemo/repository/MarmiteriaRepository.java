package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.Marmiteria;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarmiteriaRepository extends JpaRepository<Marmiteria, Long> {
}
