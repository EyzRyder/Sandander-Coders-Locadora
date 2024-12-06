# language: pt
Funcionalidade: Autenticação de usuário

  # Cenário de Login com credenciais válidas
  Cenário: Realizar login com credenciais válidas
    Dado que o usuário deseja realizar login
    Quando ele envia um pedido de login com as credenciais "testuser" e "password123"
    Então o sistema retorna um token JWT válido

  # Cenário de Registro de Novo Usuário
  Cenário: Registrar um novo usuário com sucesso
    Dado que o usuário deseja se registrar
    Quando ele envia os dados de registro com login "newuser", senha "password123", e outras informações válidas
    Então o sistema cria o usuário e retorna status 200
