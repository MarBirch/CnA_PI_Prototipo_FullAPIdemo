package com.example.FullAPIdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

/** Erros padronizados e sem detalhes internos (stack trace, SQL, classes). */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(f -> campos.putIfAbsent(f.getField(), f.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("status", 400, "mensagem", "Dados invalidos.", "campos", campos));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> status(ResponseStatusException ex) {
        int code = ex.getStatusCode().value();
        String msg = ex.getReason() != null ? ex.getReason() : HttpStatus.valueOf(code).getReasonPhrase();
        return ResponseEntity.status(code).body(Map.of("status", code, "mensagem", msg));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> negado(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(Map.of("status", 403, "mensagem", "Acesso negado."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generico(Exception ex) {
        log.error("Erro nao tratado", ex);
        return ResponseEntity.status(500).body(Map.of("status", 500, "mensagem", "Erro interno. Tente novamente."));
    }
}
