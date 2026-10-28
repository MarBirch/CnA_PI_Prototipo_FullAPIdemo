package com.example.FullAPIdemo.model.entity;
import com.example.FullAPIdemo.model.StatusAcesso;
import com.example.FullAPIdemo.model.StatusComercial;
import com.example.FullAPIdemo.model.StatusPagamento;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="lead_comercial")
public class LeadComercial {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="nome_loja",nullable=false,length=150) private String nomeLoja;
 @Column(length=18) private String cnpj;
 @Column(nullable=false,length=150) private String email;
 @Column(length=20) private String telefone;
 @Column(length=120) private String responsavel;
 @Column private String cep;
 @Column private String numero;
 @Column private String rua;
 @Column private String bairro;
 @Column(length=100) private String cidade;
 @Column(length=2) private String uf;
 @Column(length=1000) private String mensagem;
 @Enumerated(EnumType.STRING) @Column(name="status_comercial",nullable=false,length=30) private StatusComercial statusComercial=StatusComercial.LEAD_RECEBIDO;
 @Enumerated(EnumType.STRING) @Column(name="status_pagamento",nullable=false,length=30) private StatusPagamento statusPagamento=StatusPagamento.NAO_INICIADO;
 @Enumerated(EnumType.STRING) @Column(name="status_acesso",nullable=false,length=30) private StatusAcesso statusAcesso=StatusAcesso.AGUARDANDO_APROVACAO;
 @OneToOne @JoinColumn(name="marmiteria_id") private Marmiteria marmiteria;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt;
 @Column(name="updated_at",nullable=false) private LocalDateTime updatedAt;
 @PrePersist protected void onCreate(){createdAt=LocalDateTime.now();updatedAt=createdAt;}
 @PreUpdate protected void onUpdate(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;}
 public LocalDateTime getCreatedAt(){return createdAt;}
 public LocalDateTime getUpdatedAt(){return updatedAt;}
 public String getNomeLoja(){return nomeLoja;} public void setNomeLoja(String v){nomeLoja=v;}
 public String getCnpj(){return cnpj;} public void setCnpj(String v){cnpj=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getTelefone(){return telefone;} public void setTelefone(String v){telefone=v;}
 public String getResponsavel(){return responsavel;} public void setResponsavel(String v){responsavel=v;}
 public String getCep(){return cep;} public void setCep(String v){cep=v;}
 public String getNumero(){return numero;} public void setNumero(String v){numero=v;}
 public String getRua(){return rua;} public void setRua(String v){rua=v;}
 public String getBairro(){return bairro;} public void setBairro(String v){bairro=v;}
 public String getCidade(){return cidade;} public void setCidade(String v){cidade=v;}
 public String getUf(){return uf;} public void setUf(String v){uf=v;}
 public String getMensagem(){return mensagem;} public void setMensagem(String v){mensagem=v;}
 public StatusComercial getStatusComercial(){return statusComercial;} public void setStatusComercial(StatusComercial v){statusComercial=v;}
 public StatusPagamento getStatusPagamento(){return statusPagamento;} public void setStatusPagamento(StatusPagamento v){statusPagamento=v;}
 public StatusAcesso getStatusAcesso(){return statusAcesso;} public void setStatusAcesso(StatusAcesso v){statusAcesso=v;}
 public Marmiteria getMarmiteria(){return marmiteria;} public void setMarmiteria(Marmiteria v){marmiteria=v;}
}
