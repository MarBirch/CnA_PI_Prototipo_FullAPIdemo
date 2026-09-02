package com.example.FullAPIdemo.model.dto;

public class AlterarSenhaRequest {
    private String username;
    private String novaSenha;

    public AlterarSenhaRequest() {}

    public AlterarSenhaRequest(String username, String novaSenha) {
        this.username = username;
        this.novaSenha = novaSenha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
