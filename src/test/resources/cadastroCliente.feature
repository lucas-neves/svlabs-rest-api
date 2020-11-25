Feature: Cadastro de Clientes
    Validação do endpoint de cadastro de novos clientes

  Scenario: Registrar novo cliente no sistema
    Given tento cadastrar um novo cliente
    Then recebo status "201"
    And valido cliente cadastrado

  Scenario: Registrar cliente com CPF já cadastrado
    Given tento cadastrar um cliente com CPF já cadastrado
    Then recebo status "412"
    And valido a mensagem de erro "Cliente já cadastrado"

  Scenario: Registrar cliente com ZipCode inválido
    Given tento cadastrar um cliente com zipcode inválido
    Then recebo status "400"
    And valido a mensagem de erro "Campo zipCode inválido"

  Scenario: Registrar cliente sem CPF
    Given tento cadastrar um cliente sem CPF
    Then recebo status "400"
    And valido a mensagem de erro "Campo cpf não informado"

  Scenario: Registrar cliente sem Nome
    Given tento cadastrar um cliente sem Nome
    Then recebo status "400"
    And valido a mensagem de erro "Campo name não informado"

  Scenario: Registrar cliente sem Data de nascimento
    Given tento cadastrar um cliente sem Data de nascimento
    Then recebo status "400"
    And valido a mensagem de erro "Campo birthDate não informado"

  Scenario: Registrar cliente com CPF incompleto
    Given tento cadastrar um cliente com CPF incompleto
    Then recebo status "400"
    And valido a mensagem de erro "Campo CPF deve conter 11 dígitos"

  Scenario: Registrar cliente menor de idade
    Given tento cadastrar um cliente menor de idade
    Then recebo status "412"
    And valido a mensagem de erro "Permitido o cadastro somente para maiores de 16 anos"

  Scenario: Registrar cliente com data de nascimento inválida
    Given tento cadastrar um cliente com data de nascimento inválida
    Then recebo status "412"
    And valido a mensagem de erro "Data de Nascimento não deve ser maior que a atual"