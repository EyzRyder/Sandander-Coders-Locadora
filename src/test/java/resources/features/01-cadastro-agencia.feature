Feature: Gerenciamento de agencia

    Scenario: 1. Limpar o BD
        Given O BD deve estar limpo

    Scenario: 2. Deve cadastrar uma agencia corretamente quando os dados forem enviados
        When eu cadastro uma agencia com a capacidade de 10 veiculos, cep "02258010", Uf "SP", Bairro "Vila Constança", Lougradouro "Rua Bassi", Região "Sudeste", Cidade "São Paulo"
        Then a agencia com a capacidade de 10, cep "02258010", Uf "SP", Bairro "Vila Constança", Lougradouro "Rua Bassi", Região "Sudeste", Cidade "São Paulo" deve estar salvo no banco de dados

    Scenario: 3. Deve atualizar uma agencia corretamente quando os dados forem enviados
        When eu atualizo a capacidade para 100 da agencia
        Then a agencia com capacidade atualizadad com 100 deve estar salvo no banco de dados

    Scenario: 4. Deve deletar uma agencia
        When eu deleto a agencia do BD
        Then o BD não deve conter mais a agencia
