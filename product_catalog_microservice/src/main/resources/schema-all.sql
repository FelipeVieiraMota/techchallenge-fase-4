DROP TABLE IF EXISTS tb_produto;

CREATE TABLE tb_produto (
    id                              BIGSERIAL PRIMARY KEY,
    nome                            VARCHAR(255) NOT NULL,
    descricao                       VARCHAR(255) NOT NULL,
    preco                           DECIMAL(10,2) NOT NULL,
    quantidade_estoque              INT NOT NULL,
    categoria                       VARCHAR(50) NOT NULL
);