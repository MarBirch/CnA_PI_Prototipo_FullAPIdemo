package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Gastos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private Double custo;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id", nullable = false)
    @JsonIgnore
    private Marmiteria marmiteria;


}
