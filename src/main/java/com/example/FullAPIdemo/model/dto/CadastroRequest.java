package com.example.FullAPIdemo.model.dto;

public class CadastroRequest extends LoginRequest {
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String action;
}
