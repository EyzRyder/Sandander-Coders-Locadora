# language: pt
Funcionalidade: Gestão de Clientes

  Cenário: Cadastrar um novo cliente
    Dado que o usuário deseja cadastrar um novo cliente
    Quando ele envia os dados do cliente com nome "João Silva", CPF "12345678901", e e-mail "joao@gmail.com", e telefone "32948102938"
    Então o sistema deve criar o cliente e retornar status 200

  Cenário: Listar todos os clientes
    Dado que existem clientes cadastrados
    Quando o usuário acessa a rota para listar todos os clientes
    Então o sistema deve retornar uma lista com os clientes cadastrados

  Cenário: Atualizar informações de um cliente
    Dado que existe um cliente com ID 1
    Quando o usuário atualiza as informações do cliente com nome "João Silva Atualizado"
    Então o sistema deve retornar as informações do cliente atualizadas

  Cenário: Deletar um cliente
    Dado que existe um cliente com ID 1
    Quando o usuário deleta o cliente com ID 1
    Então o sistema deve retornar status 200
