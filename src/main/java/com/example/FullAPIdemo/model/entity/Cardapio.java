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
    private Long valor_ingrediente1;

    @Column
    private String ingrediente2;
    @Column
    private Long valor_ingrediente2;

    @Column
    private String ingrediente3;
    @Column
    private Long valor_ingrediente3;

    @Column
    private String ingrediente4;
    @Column
    private Long valor_ingrediente4;

    @Column
    private String ingrediente5;
    @Column
    private Long valor_ingrediente5;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id")
    @JsonIgnore
    private Marmiteria marmiteria;


}
