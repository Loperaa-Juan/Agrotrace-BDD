Feature: Registro de usuarios en la plataforma Agrotrace

  Scenario: El usuario intenta registrarse con campos obligatorios vacíos
    Given Un nuevo usuario se encuentra en la página de registro
    When Intenta registrarse sin diligenciar los campos obligatorios
    Then Ve mensajes de campos obligatorios

  Scenario: Registro exitoso de un agricultor
    When Se registra como "agricultor" con datos válidos
    Then Es redirigido a su perfil
    And Valida su rol de "Agricultor"

  Scenario: Un usuario intenta registrarse con el correo del agricultor recién registrado
    Given Cierra sesión y vuelve a la página de registro
    When Intenta registrarse con el correo del "agricultor" recién registrado
    Then Valida el mensaje "Ya existe una cuenta con este correo"

  Scenario: Registro exitoso de un negocio
    When Se registra como "negocio" con datos válidos
    Then Ve la lista de cosechas disponibles en la plataforma

  Scenario: Un negocio intenta registrarse con el NIT del negocio recién registrado
    Given Cierra sesión y vuelve a la página de registro
    When Intenta registrarse como "negocio" con el NIT del negocio recién registrado
    Then Ve el mensaje "Ya existe una cuenta con este número de documento"

  Scenario: Un usuario intenta registrarse con un correo con formato inválido
    When Intenta registrarse como "persona" con el correo "correo-sin-arroba.com"
    Then Valida el mensaje "Ingresa un correo válido"

  Scenario: Registro exitoso de una persona
    When Se registra como "persona" con datos válidos
    Then Ve la lista de cosechas disponibles en la plataforma
    And Cierra sesión