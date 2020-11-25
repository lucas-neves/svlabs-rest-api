Feature: Consulta de Endereço do Cliente
    Validação do endpoint de consulta de endereço de clientes

  Scenario: Buscar endereço do cliente no sistema
    Given busco um cliente cadastrado
    Then recebo status "200"
    And valido endereço cadastrado

  Scenario: Buscar endereço sem informar CPF
    Given busco um cliente com CPF nulo
    Then recebo status "400"
    And valido a mensagem de erro "Campo cpf não informado"

  Scenario: Buscar endereço com CPF incompleto
    Given busco um cliente com CPF incompleto
    Then recebo status "400"
    And valido a mensagem de erro "Campo CPF deve conter 11 dígitos"

  Scenario: Buscar endereço com CPF não cadastrado
    Given busco um cliente com CPF não cadastrado
    Then recebo status "404"
    And valido a mensagem de erro "Cliente não encontrado"