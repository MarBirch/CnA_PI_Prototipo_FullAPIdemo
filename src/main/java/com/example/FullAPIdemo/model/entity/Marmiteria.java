package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Marmiteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String nome;

    @Column
    private String password;

    @Column
    private String cep;

    @Column
    private String numero;

    @Column
    private Boolean status;

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cardapio> cardapios;

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Gastos> gastos;

}
