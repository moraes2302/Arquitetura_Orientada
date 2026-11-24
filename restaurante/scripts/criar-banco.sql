-- Execute como usuário com permissão de criar bancos no MySQL 8 ou superior.
-- As tabelas serão criadas pelo Flyway na primeira inicialização do servidor.
CREATE DATABASE IF NOT EXISTS restaurante_soap CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS restaurante_teste CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
