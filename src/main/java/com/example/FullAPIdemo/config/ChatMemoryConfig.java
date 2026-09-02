package com.example.FullAPIdemo.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatMemoryConfig {

    /**
     * Define ChatMemory em memória para a assistente AI (Ollama).
     * O histórico persistente de conversas é mantido com segurança no MySQL
     * através das entidades Chat e Message no OllamaService.
     */
    @Bean
    @Primary
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
    }

    /**
     * Limpa a tabela temporária legada SPRING_AI_CHAT_MEMORY no MySQL
     * para eliminar erros de chave duplicada ou colunas corrompidas.
     */
    @Bean
    public CommandLineRunner resetSpringAiChatMemoryTable(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                jdbcTemplate.execute("DROP TABLE IF EXISTS SPRING_AI_CHAT_MEMORY");
                jdbcTemplate.execute("""
                    CREATE TABLE SPRING_AI_CHAT_MEMORY (
                        sequence_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        conversation_id VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,
                        type VARCHAR(255) NOT NULL,
                        timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                """);
                System.out.println("[ChatMemoryConfig] Tabela SPRING_AI_CHAT_MEMORY resetada com sucesso.");
            } catch (Exception e) {
                System.err.println("[ChatMemoryConfig] Erro ao ajustar SPRING_AI_CHAT_MEMORY: " + e.getMessage());
            }
        };
    }
}
