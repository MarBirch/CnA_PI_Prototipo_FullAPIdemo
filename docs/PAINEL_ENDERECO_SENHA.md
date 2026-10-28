# Atualizacao do painel Web
Execute 003_endereco_dashboard.sql depois de 001 e 002, selecionando o banco correto no Workbench e fazendo backup antes. Reinicie a API atualizada.

- Contas Marmiteria existentes sao vinculadas ao painel no inicio da API. ADMIN pode executar POST /api/admin/empresas/sincronizar para importar novas contas do Desktop.
- A conta existente nao e duplicada e sua senha/acesso nao sao alterados. Comercial e pagamento desconhecidos aparecem como CADASTRO_EXISTENTE / NAO_INFORMADO.
- Email historico ambiguo nao escolhe lead arbitrario; a conta recebe registro vinculado proprio.
- POST /api/admin/empresas/{leadId}/redefinir-senha exige ADMIN e conta vinculada; salva apenas BCrypt em marmiteria e retorna senha provisoria de 14 caracteres uma vez, com Cache-Control no-store. Site e Desktop usam a mesma senha. Nao registrar resposta em logs.
- A senha anterior deixa de funcionar, mas JWTs emitidos continuam ate expirar. Nao existe expiracao automatica da senha provisoria ou troca forcada no primeiro login.
- PATCH /api/admin/empresas/{leadId}/endereco e PATCH /api/conta/endereco recebem cep,numero,rua,bairro,cidade,uf como conjunto completo. Campos em branco removem valores; telas nao enviam PATCH parcial. Conta deriva ID do token e sincroniza lead.
- Cadastro comercial adiciona rua,bairro,cidade,uf opcionais ao payload anterior. ViaCEP preenche no Web, mas permite correcao manual; Java persiste.
- Endpoints legados permanecem sem autenticacao por compatibilidade; essa lacuna preexistente nao e eliminada pelas rotas novas.
