package com.example.FullAPIdemo.model.dto;

public class AtualizarPerfilRequest {
    private String username;
    private String nome;
    private String email;
    private Long celular;
    private String endereco;
    private String cep;
    private Double peso;
    private Double altura;

    public AtualizarPerfilRequest() {}

    public AtualizarPerfilRequest(String username, String nome, String email, Long celular, String endereco, String cep, Double peso, Double altura) {
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.endereco = endereco;
        this.cep = cep;
        this.peso = peso;
        this.altura = altura;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getCelular() { return celular; }
    public void setCelular(Long celular) { this.celular = celular; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
}
