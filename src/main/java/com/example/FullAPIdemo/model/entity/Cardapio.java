package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ingrediente1;

    @Column
    private String quantidadeIngrediente1;

    @Column
    private String ingrediente2;

    @Column
    private String quantidadeIngrediente2;

    @Column
    private String ingrediente3;

    @Column
    private String quantidadeIngrediente3;

    @Column
    private String ingrediente4;

    @Column
    private String quantidadeIngrediente4;

    @Column
    private String ingrediente5;

    @Column
    private String quantidadeIngrediente6;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id")
    @JsonIgnore
    private Marmiteria marmiteria;


}
