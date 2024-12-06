Feature: Cadastro de agencia

    Scenario: Deve cadastrar uma agencia corretamente quando os dados forem enviados
        Given O BD deve estar limpo
        When eu cadastro uma agencia com a capacidade de 10, cep "02258010", Uf "SP", Bairro "Vila Constança", Lougradouro "Rua Bassi", Região "Sudeste", Cidade "São Paulo"
        Then a agencia com a capacidade de 10, cep "02258010", Uf "SP", Bairro "Vila Constança", Lougradouro "Rua Bassi", Região "Sudeste", Cidade "São Paulo" deve estar salvo no banco de dados