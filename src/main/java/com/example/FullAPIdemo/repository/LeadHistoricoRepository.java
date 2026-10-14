package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.entity.LeadHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadHistoricoRepository extends JpaRepository<LeadHistorico, Long> {
    List<LeadHistorico> findByLeadIdOrderByCreatedAtDesc(Long leadId);
}
