package com.example.FullAPIdemo.model.pojo;

public class CadastroRequest extends LoginUser{
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String action;
}
