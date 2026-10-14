package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.LeadComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LeadComercialRepository extends JpaRepository<LeadComercial, Long>, JpaSpecificationExecutor<LeadComercial> {
    Optional<LeadComercial> findFirstByEmailIgnoreCaseOrderByCreatedAtDesc(String email);
    Optional<LeadComercial> findByMarmiteriaId(Long marmiteriaId);
}
