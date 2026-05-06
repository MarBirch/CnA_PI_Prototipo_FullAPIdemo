# Mari - Sistema de Gestão de Marmiteria

O projeto **Mari** é uma solução completa para gestão de marmiterias, integrando um backend robusto em Java Spring Boot com inteligência artificial e um frontend desktop moderno desenvolvido em Python com o framework Flet.

---

## 🚀 Estrutura do Projeto

O repositório está dividido em duas partes principais:

1.  **`Mariana_3DSN_API/`**: Backend (API REST).
2.  **`MC_3DSN_desktop/`**: Frontend Desktop.

---

## 🛠️ Tecnologias Utilizadas

### Backend (`Mariana_3DSN_API`)
- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.0.2 (Starter Web, Data JPA)
- **Banco de Dados:** MySQL
- **Inteligência Artificial:** Spring AI com integração local via **Ollama** (Modelo: `deepseek-r1:1.5b`)
- **Gerenciamento de Dependências:** Maven

### Frontend (`MC_3DSN_desktop`)
- **Linguagem:** Python 3.x
- **Framework de UI:** [Flet](https://flet.dev/) (Baseado em Flutter)
- **Visualização de Dados:** Matplotlib (Gráficos de análise de gastos)
- **Comunicação:** Requests (Consumo da API REST)

---

## 📦 Funcionalidades

- **Gestão de Usuários:** Cadastro, login e perfil.
- **Cardápio:** Controle completo de pratos e ingredientes.
- **Gestão de Gastos:** Cadastro de despesas por categoria com visualização em gráficos.
- **Pedidos:** Processamento e acompanhamento de status de pedidos.
- **Assistente IA:** Chat integrado para auxílio na gestão e dúvidas, utilizando modelos de IA locais.

---

## 🚦 Como Executar

### 1. Configuração do Backend
1.  Navegue até a pasta: `cd Mariana_3DSN_API`
2.  Certifique-se de ter o **MySQL** rodando e configure as credenciais no arquivo `src/main/resources/application.properties`.
3.  Para a IA, instale o [Ollama](https://ollama.com/) e baixe o modelo: `ollama run deepseek-r1:1.5b`
4.  Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```

### 2. Configuração do Frontend
1.  Navegue até a pasta: `cd MC_3DSN_desktop`
2.  Crie um ambiente virtual (opcional, mas recomendado):
    ```bash
    python -m venv venv
    source venv/bin/activate  # Linux/Mac
    # ou
    .\venv\Scripts\activate     # Windows
    ```
3.  Instale as dependências:
    ```bash
    pip install flet requests matplotlib watchdog
    ```
4.  Execute a aplicação:
    ```bash
    python index.py
    ```
    *Para desenvolvimento com auto-reload, use: `python rodar.py`*

---

## 📖 Documentação da API (Endpoints Principais)

A API responde na porta `8080` por padrão.

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/apiUser/login` | Realiza o login do usuário |
| `POST` | `/apiCardapio/inserir` | Adiciona novo item ao cardápio |
| `GET` | `/apiCardapio/todos` | Lista todos os itens do cardápio |
| `POST` | `/apiGastos/inserir` | Registra uma nova despesa |
| `POST` | `/chat` | Interação com o assistente de IA |

---

## 📂 Organização de Pastas

```text
mari/
├── Mariana_3DSN_API/        # Código fonte do servidor Java
│   ├── src/main/java/       # Lógica de negócio (Controller, Service, Entity)
│   └── src/main/resources/  # Configurações (application.properties)
├── MC_3DSN_desktop/         # Código fonte da aplicação Python
│   ├── assets/              # Imagens e recursos visuais
│   ├── index.py             # Arquivo principal da interface
│   └── rodar.py             # Script de execução com hot-reload
└── GEMINI.md                # Instruções de contexto para IA
```
