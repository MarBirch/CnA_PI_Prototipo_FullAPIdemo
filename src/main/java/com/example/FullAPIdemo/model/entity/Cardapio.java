package com.example.FullAPIdemo.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ingrediente> ingredientes;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id", nullable = false )
    @JsonIgnore
    private Marmiteria marmiteria;
}
