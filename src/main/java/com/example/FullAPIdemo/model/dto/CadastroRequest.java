package com.example.FullAPIdemo.model.dto;

import javax.validation.constraints.Size;

public class CadastroRequest {

    @Size(max = 45)
    private String username;

    @Size(max = 45)
    private String senha;

    private String  nome;
    private String  email;
    private Long    celular;
    private String  endereco;
    private String  cep;
    private Double  peso;
    private Double  altura;

    public String getUsername() { return username; }
    public String getSenha()    { return senha;    }
    public String getNome()     { return nome;     }
    public String getEmail()    { return email;    }
    public Long   getCelular()  { return celular;  }
    public String getEndereco() { return endereco; }
    public String getCep()      { return cep;      }
    public Double getPeso()     { return peso;     }
    public Double getAltura()   { return altura;   }

    public void setUsername(String username) { this.username = username; }
    public void setSenha(String senha)       { this.senha    = senha;    }
    public void setNome(String nome)         { this.nome     = nome;     }
    public void setEmail(String email)       { this.email    = email;    }
    public void setCelular(Long celular)     { this.celular  = celular;  }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setCep(String cep)           { this.cep      = cep;      }
    public void setPeso(Double peso)         { this.peso     = peso;     }
    public void setAltura(Double altura)     { this.altura   = altura;   }
}

