-- Comida & Afeto - estrutura para o Web comercial/administrativo (MySQL 8)
-- Executar uma vez antes de subir esta versao da API (spring.jpa.hibernate.ddl-auto=none).

ALTER TABLE marmiteria ADD COLUMN status_acesso VARCHAR(30) NULL;
-- Empresas existentes continuam com acesso (NULL = ATIVO). Senhas legadas viram BCrypt no proximo login.
ALTER TABLE marmiteria MODIFY COLUMN senha VARCHAR(100);
ALTER TABLE `user` MODIFY COLUMN senha VARCHAR(100);

CREATE TABLE IF NOT EXISTS lead_comercial (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome_loja VARCHAR(150) NOT NULL,
  cnpj VARCHAR(18) NULL,
  email VARCHAR(150) NOT NULL,
  telefone VARCHAR(20) NULL,
  tipo_estabelecimento VARCHAR(60) NULL,
  responsavel VARCHAR(120) NULL,
  cidade VARCHAR(100) NULL,
  uf CHAR(2) NULL,
  mensagem VARCHAR(1000) NULL,
  status_comercial VARCHAR(30) NOT NULL DEFAULT 'LEAD_RECEBIDO',
  status_pagamento VARCHAR(30) NOT NULL DEFAULT 'NAO_INICIADO',
  status_acesso VARCHAR(30) NOT NULL DEFAULT 'AGUARDANDO_APROVACAO',
  marmiteria_id BIGINT NULL UNIQUE,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_lead_email (email),
  INDEX idx_lead_status (status_comercial),
  CONSTRAINT fk_lead_marmiteria FOREIGN KEY (marmiteria_id) REFERENCES marmiteria(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS lead_historico (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  lead_id BIGINT NOT NULL,
  tipo VARCHAR(60) NOT NULL,
  descricao VARCHAR(1000) NULL,
  autor_email VARCHAR(150) NULL,
  created_at DATETIME(6) NOT NULL,
  INDEX idx_hist_lead (lead_id),
  CONSTRAINT fk_hist_lead FOREIGN KEY (lead_id) REFERENCES lead_comercial(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS admin_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(150) NOT NULL UNIQUE,
  nome VARCHAR(120) NOT NULL,
  senha_hash VARCHAR(100) NOT NULL,
  ativo BIT(1) NOT NULL DEFAULT 1,
  created_at DATETIME(6) NOT NULL
);
