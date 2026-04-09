package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PedidoIngrediente")
public class PedidoIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal valorPorGramas;

    @Column(nullable = false)
    private BigDecimal gramas;

    @Column(nullable = false)
    private Integer posicao;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    public PedidoIngrediente() {
    }

    public PedidoIngrediente(Long id, String nome, BigDecimal valorPorGramas, BigDecimal gramas, Integer posicao, Cardapio cardapio, Pedido pedido) {
        this.id = id;
        this.nome = nome;
        this.valorPorGramas = valorPorGramas;
        this.gramas = gramas;
        this.posicao = posicao;
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

    public BigDecimal getValorPorGramas() {
        return valorPorGramas;
    }

    public void setValorPorGramas(BigDecimal valorPorGramas) {
        this.valorPorGramas = valorPorGramas;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getGramas() {
        return gramas;
    }

    public void setGramas(BigDecimal gramas) {
        this.gramas = gramas;
    }
}