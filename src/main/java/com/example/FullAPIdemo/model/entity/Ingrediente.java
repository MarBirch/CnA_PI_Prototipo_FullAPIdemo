package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Long valorPorGramas;

    @Column(nullable = false)
    private Integer posicao;

    @ManyToOne
    @JoinColumn(name = "cardapio_id", nullable = false)
    @JsonIgnore
    private Cardapio cardapio;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    public Ingrediente() {
    }

    public Ingrediente(Long id, String nome, Long valorPorGramas, Integer posicao, Cardapio cardapio, Pedido pedido) {
        this.id = id;
        this.nome = nome;
        this.valorPorGramas = valorPorGramas;
        this.posicao = posicao;
        this.cardapio = cardapio;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getValorPorGramas() {
        return valorPorGramas;
    }

    public void setValorPorGramas(Long valorPorGramas) {
        this.valorPorGramas = valorPorGramas;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}