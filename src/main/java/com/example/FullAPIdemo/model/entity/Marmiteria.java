package com.example.FullAPIdemo.model.entity;

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

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    private List<Cardapio> cardapios;

    @OneToMany(mappedBy = "marmiteria", cascade = CascadeType.ALL)
    private List<Gastos> gastos;

}
