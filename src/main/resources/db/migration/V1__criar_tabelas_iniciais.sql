CREATE TABLE unidades
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(255) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE produtos
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(255)   NOT NULL,
    descricao   VARCHAR(255),
    preco_atual DECIMAL(10, 2) NOT NULL
);

CREATE TABLE usuarios
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    login      VARCHAR(255),
    senha      VARCHAR(255),
    role       VARCHAR(50),
    unidade_id BIGINT,
    FOREIGN KEY (unidade_id) REFERENCES unidades (id)
);

CREATE TABLE item_estoque
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT,
    unidade_id BIGINT,
    produto_id BIGINT,
    FOREIGN KEY (unidade_id) REFERENCES unidades (id),
    FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

CREATE TABLE clientes
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(255) NOT NULL,
    cpf         VARCHAR(11),
    data_nasc   DATE,
    aceite_lgpd BOOLEAN,
    pontos      INT,
    usuario_id  BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

CREATE TABLE pedidos
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    canal            VARCHAR(50),
    metodo_pagamento VARCHAR(50),
    status_pedido    VARCHAR(50),
    exige_troco      BOOLEAN,
    valor_total      DECIMAL(10, 2),
    data_criacao     TIMESTAMP,
    canal_pedido     VARCHAR(50),
    cliente_id       BIGINT,
    unidade_id       BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES clientes (id),
    FOREIGN KEY (unidade_id) REFERENCES unidades (id)
);

CREATE TABLE item_pedido
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade     INT            NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    pedido_id      BIGINT,
    produto_id     BIGINT,
    observacoes    VARCHAR(255),
    FOREIGN KEY (pedido_id) REFERENCES pedidos (id),
    FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

CREATE TABLE pagamentos
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_pagamento   TIMESTAMP,
    valor            DECIMAL(10, 2),
    codigo_gateway   VARCHAR(255),
    metodo_pagamento VARCHAR(50),
    status_pagamento VARCHAR(50),
    pedido_id        BIGINT,
    FOREIGN KEY (pedido_id) REFERENCES pedidos (id)
);