CREATE TABLE ENDERECO (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    CEP INT NOT NULL,
    LOGRADOURO VARCHAR(255),
    BAIRRO VARCHAR(255),
    CIDADE VARCHAR(255),
    UF VARCHAR(255),
    REGIAO VARCHAR(255)
);
