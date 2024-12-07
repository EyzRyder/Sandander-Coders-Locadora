Feature: Gestão de Veículos
  Para gerenciar veículos em uma agência
  Como usuário do sistema
  Quero criar, atualizar, buscar e deletar veículos.

  Scenario: Criar um novo veículo com sucesso
    Given uma agência com capacidade disponível
    When eu criar um veículo com os dados válidos
    Then o veículo deve ser salvo com sucesso

  Scenario: Buscar veículo por ID existente
    Given um veículo existente no sistema
    When eu buscar o veículo pelo ID
    Then o sistema deve retornar os detalhes do veículo

  Scenario: Atualizar veículo existente
    Given um veículo existente no sistema
    When eu atualizar o veículo com novos dados
    Then o sistema deve refletir as mudanças no veículo

  Scenario: Deletar veículo existente
    Given um veículo existente no sistema
    When eu deletar o veículo pelo ID
    Then o veículo deve ser removido do sistema
