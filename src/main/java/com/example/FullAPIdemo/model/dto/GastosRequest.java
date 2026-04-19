package com.example.FullAPIdemo.model.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class GastosRequest {
    private Double custo;

    private String categoria;

    private LocalDate data;

    private String observacao;

    @NotNull
    private Long marmiteriaId;

    public Long getMarmiteriaId() {
        return marmiteriaId;
    }

    public void setMarmiteriaId(Long marmiteriaId) {
        this.marmiteriaId = marmiteriaId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }
}
