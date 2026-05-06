# Análise Arquitetural - Projeto Mari

Esta análise foi realizada sob a perspectiva de um Arquiteto de Software, avaliando a robustez, escalabilidade, manutenibilidade e adesão às boas práticas de desenvolvimento (Clean Code, SOLID, etc.).

---

## 1. Backend (Java Spring Boot)

### ✅ Pontos Positivos
- **Uso de DTOs:** O projeto utiliza `Request` e `Response` objects para comunicação, evitando a exposição direta de entidades JPA na API.
- **Integração com IA:** A implementação do Spring AI com Ollama é moderna e permite processamento local de linguagem natural.
- **Separação de Repositories:** Uso correto de interfaces `JpaRepository`.

### ⚠️ Pontos Críticos e Oportunidades de Melhoria

#### A. Quebra de Camadas (Layered Architecture)
- **Problema:** O `CardapioController` acessa diretamente o `CardapioRepository`. 
- **Impacto:** Isso viola a arquitetura em camadas. Toda lógica de persistência e negócio deve passar por uma classe `@Service`.
- **Recomendação:** Criar `CardapioService` e mover a lógica para lá.

#### B. Injeção de Dependências
- **Problema:** Uso extensivo de `@Autowired` em campos (Field Injection).
- **Impacto:** Dificulta testes unitários e mascara dependências circulares.
- **Recomendação:** Utilizar **Constructor Injection** (obrigatório para garantir imutabilidade e facilidade de teste).

#### C. Validação de Dados
- **Problema:** Embora o `@Valid` seja utilizado em alguns Controllers, os DTOs (como `CadastroRequest`) carecem de anotações do Bean Validation (`@NotNull`, `@NotBlank`, `@Size`).
- **Impacto:** A API pode processar dados inconsistentes ou nulos, gerando erros de runtime (NullPointerException) ou estados inválidos no banco.

#### D. Vazamento de Responsabilidades (Anti-pattern)
- **Problema:** `OllamaService` e `PedidoService` possuem anotações de Web (`@PostMapping`, `@RequestBody`) e retornam `ResponseEntity`.
- **Impacto:** Services não devem saber nada sobre o protocolo HTTP. Eles devem receber objetos puros e retornar dados ou exceções de negócio.
- **Recomendação:** Limpar os Services de qualquer referência a `ResponseEntity` ou anotações de mapeamento web.

---

## 2. Frontend (Python Flet)

### ⚠️ Pontos Críticos

#### A. Código Monolítico (God Object)
- **Problema:** O arquivo `index.py` possui quase 700 linhas, misturando definição de UI, lógica de estado e chamadas de rede.
- **Impacto:** Extremamente difícil de manter e testar.
- **Recomendação:** Separar a lógica em componentes (View), serviços de API (Client) e modelos de dados.

#### B. Tratamento de Erros e Feedback
- **Problema:** A aplicação faz requisições via `requests` sem um bloco robusto de `try-except` visível para falhas de conexão.
- **Impacto:** Se o backend estiver offline, a interface pode travar ou não dar feedback claro ao usuário.

---

## 3. Segurança e Infraestrutura

### 🚨 Riscos de Segurança
- **Credenciais Expostas:** O arquivo `application.properties` contém credenciais de banco de dados em texto puro, incluindo IP, usuário e senha.
- **Recomendação:** Utilizar variáveis de ambiente ou um cofre de segredos (Spring Cloud Config/Vault).

### ⚙️ Banco de Dados
- **Configuração:** O uso de `ddl-auto=update` é aceitável para desenvolvimento, mas perigoso para produção.
- **Recomendação:** Migrar para uma ferramenta de versionamento de banco de dados como **Flyway** ou **Liquibase**.

---

## 4. Conclusão

O projeto demonstra um bom domínio tecnológico (Spring AI + Flet), mas falha em fundamentos de arquitetura corporativa. A aplicação é funcional ("MVP"), mas exigiria uma refatoração significativa para se tornar um produto de nível de produção (*production-ready*).

**Prioridade de Refatoração:**
1. Isolar Services da camada Web.
2. Adicionar validações reais nos DTOs.
3. Modularizar o frontend Python.
4. Remover credenciais do código fonte.
