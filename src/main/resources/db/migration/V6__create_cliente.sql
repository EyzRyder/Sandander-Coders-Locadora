CREATE TABLE CLIENTE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NOME VARCHAR(255) NOT NULL,
    CPF VARCHAR(11) NOT NULL UNIQUE,
    EMAIL VARCHAR(255) NOT NULL,
    TELEFONE VARCHAR(15)
);
