package com.example.FullAPIdemo.model.dto;

import com.example.FullAPIdemo.model.StatusPedido;
import com.example.FullAPIdemo.model.entity.Ingrediente;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {
    private BigInteger id;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataPedido;
    private LocalDateTime dataCriada;
    private String nomeMarmiteria;
    private List<PedidoIngrediente> ingredientes;

    public PedidoResponse(Pedido pedido) {
        this.id = BigInteger.valueOf(pedido.getId());
        this.valor = pedido.getValor();
        this.status = pedido.getStatus().name();
        this.dataPedido = pedido.getDataPedido();
        this.dataCriada = pedido.getCreatedAt();
        this.nomeMarmiteria = pedido.getMarmiteria().getNome();
        this.ingredientes = pedido.getIngredientes();}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataCriada() {
        return dataCriada;
    }

    public void setDataCriada(LocalDateTime dataCriada) {
        this.dataCriada = dataCriada;
    }

    public String getNomeMarmiteria() {
        return nomeMarmiteria;
    }

    public void setNomeMarmiteria(String nomeMarmiteria) {
        this.nomeMarmiteria = nomeMarmiteria;
    }

    public List<PedidoIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<PedidoIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
