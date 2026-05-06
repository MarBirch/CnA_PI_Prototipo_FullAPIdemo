package com.example.FullAPIdemo.model.dto;

import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {

    private Long id;
    private String nomeCliente;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataCriada;
    private Long cardapioId;
    private String cardapioNome;
    private List<PedidoIngrediente> ingredientes;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getNomeCliente();
        this.valor = pedido.getValor();
        this.status = pedido.getStatus().name();
        this.dataCriada = pedido.getCreatedAt();
        this.cardapioId = pedido.getCardapio() != null ? pedido.getCardapio().getId() : null;
        this.cardapioNome = pedido.getCardapio() != null ? pedido.getCardapio().getNome() : null;
        this.ingredientes = pedido.getIngredientes();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataCriada() { return dataCriada; }
    public void setDataCriada(LocalDateTime dataCriada) { this.dataCriada = dataCriada; }

    public Long getCardapioId() { return cardapioId; }
    public void setCardapioId(Long cardapioId) { this.cardapioId = cardapioId; }

    public String getCardapioNome() { return cardapioNome; }
    public void setCardapioNome(String cardapioNome) { this.cardapioNome = cardapioNome; }

    public List<PedidoIngrediente> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<PedidoIngrediente> ingredientes) { this.ingredientes = ingredientes; }
}