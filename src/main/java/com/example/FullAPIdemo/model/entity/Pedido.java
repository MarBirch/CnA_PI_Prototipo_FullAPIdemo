package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long valor;
    @Column
    private String quantidadeIngrediente1;

    @Column
    private String quantidadeIngrediente2;

    @Column
    private String quantidadeIngrediente3;
    @Column
    private String quantidadeIngrediente4;
    @Column
    private String quantidadeIngrediente5;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id")
    @JsonIgnore
    private Marmiteria marmiteria;




}
