-- MySQL 8: selecione o banco correto e faça backup. Execute apos 001 e 002, antes de reiniciar a API.
SELECT DATABASE() AS banco_selecionado;
SET @tipo = (SELECT COLUMN_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='marmiteria' AND COLUMN_NAME='rua');
SET @ddl = IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='lead_comercial' AND COLUMN_NAME='rua'), 'SELECT 1', CONCAT('ALTER TABLE lead_comercial ADD COLUMN rua ', @tipo, ' NULL'));
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @tipo = (SELECT COLUMN_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='marmiteria' AND COLUMN_NAME='bairro');
SET @ddl = IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='lead_comercial' AND COLUMN_NAME='bairro'), 'SELECT 1', CONCAT('ALTER TABLE lead_comercial ADD COLUMN bairro ', @tipo, ' NULL'));
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;
UPDATE lead_comercial l JOIN marmiteria m ON m.id=l.marmiteria_id
SET l.rua=COALESCE(NULLIF(l.rua,''),m.rua), l.bairro=COALESCE(NULLIF(l.bairro,''),m.bairro),
 l.cidade=COALESCE(NULLIF(l.cidade,''),m.cidade), l.uf=COALESCE(NULLIF(l.uf,''),m.uf);
-- A API vincula as marmiterias existentes ao iniciar, preservando senhas e acesso.
-- Contas importadas sem comprovacao comercial: CADASTRO_EXISTENTE / NAO_INFORMADO.
