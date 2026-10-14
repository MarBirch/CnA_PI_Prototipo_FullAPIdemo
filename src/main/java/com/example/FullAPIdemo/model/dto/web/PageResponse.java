package com.example.FullAPIdemo.model.dto.web;

import java.util.List;

public record PageResponse<T>(List<T> itens, int pagina, int tamanho, long total, int totalPaginas) {}
