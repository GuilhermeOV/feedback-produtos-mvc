CREATE DATABASE IF NOT EXISTS feedback_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE feedback_db;

-- =========================================
-- TABELA DE PRODUTOS
-- =========================================

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

-- =========================================
-- TABELA DE USUÁRIOS
-- =========================================

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- =========================================
-- TABELA DE FEEDBACK
-- =========================================

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    produto_id INT,
    nota INT NOT NULL,
    comentario TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- =========================================
-- DADOS PARA TESTE
-- =========================================

INSERT INTO produtos (nome, descricao, preco)
VALUES
    ('Fone de Ouvido Bluetooth', 'Fone sem fio com cancelamento de ruido.', 199.90),
    ('Teclado Mecanico', 'Teclado mecanico com iluminacao RGB.', 349.00),
    ('Mouse Gamer', 'Mouse com 6 botoes e sensor de alta precisao.', 129.50);

INSERT INTO usuarios (nome, email)
VALUES
    ('Guilherme Vital', 'guilherme@email.com'),
    ('Vitor Gomes', 'vitor@email.com'),
    ('Maria Souza', 'maria@email.com');

INSERT INTO feedback (usuario_id, produto_id, nota, comentario)
VALUES
    (1, 1, 5, 'Som excelente e bateria dura bastante.'),
    (2, 2, 4, 'Teclado otimo, mas um pouco barulhento.'),
    (3, 3, 3, 'Mouse cumpre o basico, nada demais.');
