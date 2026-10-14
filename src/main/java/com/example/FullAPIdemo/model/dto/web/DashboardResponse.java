package com.example.FullAPIdemo.model.dto.web;

import java.util.List;
import java.util.Map;

public record DashboardResponse(long total, Map<String, Long> porStatusComercial, Map<String, Long> porStatusPagamento,
        Map<String, Long> porStatusAcesso, List<EmpresaResponse> recentes) {}
