package com.example.FullAPIdemo.model.dto.web;

/** A senha provisoria aparece uma unica vez e nao e armazenada em texto puro. */
public record LiberacaoResponse(EmpresaResponse empresa, String emailAcesso, String senhaProvisoria) {}
