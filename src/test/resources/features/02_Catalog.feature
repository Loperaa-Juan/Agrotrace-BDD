Feature: Publicación de cosechas en Agrotrace

  Scenario: El usuario intenta registrar un producto con campos obligatorios vacíos
    Given El "agricultor" registrado anteriormente inicia sesión
    When Intenta publicar una cosecha sin los campos obligatorios
    Then Ve mensajes de error por los campos obligatorios faltantes

  Scenario: El agricultor intenta publicar con cantidad y precio no válidos
    When Intenta continuar con cantidad "0" y precio "0"
    Then Debería ver el error de cantidad "La cantidad debe ser un número entero mayor que cero."
    And Debería ver el error de precio "El precio debe ser un valor en pesos mayor que cero, sin decimales."

  Scenario: Publicación Exitosa de una cosecha de calidad A
    When Publica una cosecha de "Aguacate Hass" con datos validos y la imagen "fresh_avocado.jpg"
    Then La cosecha debería ser clasificada con "Calidad Buena"
    And debería ver el mensaje de "¡Su cosecha quedó publicada!"

  Scenario: Publicación Exitosa de una cosecha de calidad B
    Given El agricultor se encuentra en el formulario de publicación
    When Publica una cosecha de "Aguacate Hass" con datos validos y la imagen "mild_avocado.jpg"
    Then La cosecha debería ser clasificada con "Calidad Regular"
    And debería ver el mensaje de "¡Su cosecha quedó publicada!"

  Scenario: Rechazo de una cosecha de calidad C
    Given El agricultor se encuentra en el formulario de publicación
    When Publica una cosecha de "Banano" con datos validos y la imagen "rotten_banana.jpeg"
    Then Se debe mostar el mensaje de "La fruta se clasificó en mal estado y no se puede crear la oferta."
    And El botón para publicar la cosecha debe estar desactivado

  Scenario: Publicación rechazada por imagen que no corresponde a una fruta
    Given El agricultor vuelve a la primera vista del formulario de publicación
    When Intenta publicar una cosecha de "Pepino" con datos validos y la imagen "perro.jpg"
    Then Se debe mostar el mensaje de "No pudimos revisar su cosecha: No se detectó ninguna fruta en la imagen"
    And Cierra sesión

  Scenario: Un usuario sin sesión no puede entrar al formulario de publicación
    When Intenta abrir el formulario de publicación
    Then Es redirigido al inicio de sesión

  Scenario: Las cosechas publicadas son visibles en el catálogo
    When El "negocio" registrado anteriormente inicia sesión
    Then debería ver las cosechas publicadas por el agricultor

  Scenario: Un usuario que no es agricultor no puede publicar cosechas
    When Despliega el menú de opciones del usuario
    Then No debe ver la opción de publicar cosecha
