-- Comida & Afeto: executar no MySQL 8 depois de 001_web_comercial.sql.
-- Selecione o banco no painel SCHEMAS antes de executar.
-- Ou execute: USE `nome_real_do_seu_banco`;
-- Faça backup: tipo_estabelecimento sera excluido definitivamente.
-- Este script pode ser reexecutado. Cidade/UF legadas NAO sao excluidas.
SELECT DATABASE() AS banco_selecionado;

-- Copia exatamente o tipo e tamanho das colunas existentes de marmiteria.
SET @cep_type = (SELECT COLUMN_TYPE FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'marmiteria' AND COLUMN_NAME = 'cep');
SET @numero_type = (SELECT COLUMN_TYPE FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'marmiteria' AND COLUMN_NAME = 'numero');

SET @ddl = IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'lead_comercial' AND COLUMN_NAME = 'cep'),
  'SELECT 1', CONCAT('ALTER TABLE lead_comercial ADD COLUMN cep ', @cep_type, ' NULL'));
PREPARE statement_cep FROM @ddl;
EXECUTE statement_cep;
DEALLOCATE PREPARE statement_cep;

SET @ddl = IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'lead_comercial' AND COLUMN_NAME = 'numero'),
  'SELECT 1', CONCAT('ALTER TABLE lead_comercial ADD COLUMN numero ', @numero_type, ' NULL'));
PREPARE statement_numero FROM @ddl;
EXECUTE statement_numero;
DEALLOCATE PREPARE statement_numero;

-- Recupera endereco de leads ja vinculados sem inventar CEP ou sobrescrever dados.
UPDATE lead_comercial l
JOIN marmiteria m ON m.id = l.marmiteria_id
SET l.cep = COALESCE(NULLIF(TRIM(l.cep), ''), m.cep),
    l.numero = COALESCE(NULLIF(TRIM(l.numero), ''), m.numero);

SET @ddl = IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'lead_comercial' AND COLUMN_NAME = 'tipo_estabelecimento'),
  'ALTER TABLE lead_comercial DROP COLUMN tipo_estabelecimento', 'SELECT 1');
PREPARE statement_tipo FROM @ddl;
EXECUTE statement_tipo;
DEALLOCATE PREPARE statement_tipo;

SHOW COLUMNS FROM lead_comercial;
