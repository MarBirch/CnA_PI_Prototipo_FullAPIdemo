# Integração com o Web comercial/administrativo

## O que mudou
- Spring Security stateless com JWT (`POST /api/auth/empresa/login`, `POST /api/auth/admin/login`).
- Senhas com BCrypt. Senhas antigas em texto puro continuam funcionando e são convertidas no próximo login válido.
- Formulário comercial (`POST /api/cadastro-marmiteria` e o legado `/enviar-email`) grava o lead no banco e envia o e-mail.
- Pipeline: `LEAD_RECEBIDO -> CONTATO_REALIZADO -> EM_NEGOCIACAO -> VENDIDO`, ou `PERDIDO` / `CANCELADO`.
- Pagamento: `NAO_INICIADO, PENDENTE, CONFIRMADO, ATRASADO, CANCELADO`.
- Acesso: `AGUARDANDO_APROVACAO, ATIVO, BLOQUEADO, CANCELADO`. ATIVO só com venda + pagamento confirmado.
  Ao liberar, a marmiteria é criada e a API devolve uma senha provisória uma única vez.
- Marmiteria BLOQUEADA/CANCELADA não consegue entrar (Web e Desktop). Registros antigos (NULL) seguem ATIVOS.
- `/api/admin/**` (ROLE_ADMIN): dashboard, lista com busca/filtros/paginação, detalhe, status, pagamento, acesso e histórico.
- `/api/conta` (ROLE_EMPRESA): dados da própria empresa; o ID vem sempre do token.
- Erros padronizados `{status, mensagem, campos}`; senha não aparece mais nas respostas; CORS por allowlist.

## Antes de subir
1. Rodar `src/main/resources/db/001_web_comercial.sql` no MySQL.
2. Definir no `env.properties` ou no ambiente:
```
JWT_SECRET=<no mínimo 32 caracteres aleatórios>
ADMIN_EMAIL=admin@exemplo.com
ADMIN_PASSWORD=<mínimo 10 caracteres; cria o 1º admin se não existir nenhum>
CORS_ALLOWED_ORIGINS=http://localhost:5173,https://seu-site
COMERCIAL_EMAIL_DESTINO=cl203257@g.unicamp.br
```

## Compatibilidade
Os endpoints do Desktop e Mobile (`/apiUser`, `/apiMarmiteria`, `/apiCardapio`, `/apiGastos`, `/apiPedido`, `/apiOllama`)
mantêm URLs e formatos e continuam sem exigir token, para não quebrar esses apps.
Próximo passo recomendado: fazer esses apps usarem o login JWT e então proteger essas rotas.
