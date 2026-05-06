package com.example.FullAPIdemo.model.entity;

import com.example.FullAPIdemo.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nomeCliente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(nullable = true, updatable = false)
    private LocalDateTime dataPedido;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "marmiteria_id", nullable = false)
    @JsonIgnore
    private Marmiteria marmiteria;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoIngrediente> ingredientes;

    @ManyToOne
    @JoinColumn(name = "cardapio_id", nullable = false)
    @JsonIgnore
    private Cardapio cardapio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Marmiteria getMarmiteria() { return marmiteria; }
    public void setMarmiteria(Marmiteria marmiteria) { this.marmiteria = marmiteria; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<PedidoIngrediente> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<PedidoIngrediente> ingredientes) { this.ingredientes = ingredientes; }

    public Cardapio getCardapio() { return cardapio; }
    public void setCardapio(Cardapio cardapio) { this.cardapio = cardapio; }
}