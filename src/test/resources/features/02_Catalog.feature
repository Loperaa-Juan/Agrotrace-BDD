Feature: Publicación de cosechas en Agrotrace

  Scenario: El usuario intenta registrar un producto con campos obligatorios vacíos
    Given El "agricultor" registrado anteriormente inicia sesión
    When Intenta publicar una cosecha sin los campos obligatorios
    Then Ve mensajes de error por los campos obligatorios faltantes

  #Scenario: Publicación Exitosa de una cosecha de calidad A
  #  Given El agricultor se encuentra en el formulario de publicación
  #  When Publica una cosecha con datos validos y la imagen "imagen A"
  #  Then La cosecha debería ser clasificada con "Calidad Buena"
  #  And debería ver el mensaje de "¡Su cosecha quedó publicada!"
#
  #Scenario: Publicación Exitosa de una cosecha de calidad B
  #  Given El agricultor se encuentra en el formulario de publicación
  #  When Publica una cosecha con datos validos y la imagen "imagen B"
  #  Then La cosecha debería ser clasificada con "Calidad Regular"
  #  And debería ver el mensaje de "¡Su cosecha quedó publicada!"
#
  #Scenario: Rechazo de una cosecha de calidad C
  #  Given El agricultor se encuentra en el formulario de publicación
  #  When Publica una cosecha con datos validos y la imagen "imagen C"
  #  Then Se debe mostar el mensaje de "La fruta se clasificó en mal estado y no se puede crear la oferta"
  #  And El botón para publicar la cosecha debe estar desactivado
#
  #Scenario: Publicación rechazada por imagen que no corresponde a una fruta
  #  Given El agricultor se encuentra en el formulario de publicación
  #  When Publica una cosecha con datos validos y la imagen "imagen D"
  #  Then debería ver el mensaje "No se detectó ninguna fruta en la imagen"
#
  #Scenario: Las cosechas publicadas son visibles en el catálogo
  #  Given El "negocio" registrado anteriormente inicia sesión
  #  When consulta el catálogo de productos
  #  Then debería ver las cosechas publicadas por el agricultor
#
  #Scenario: Un usuario que no es agricultor no puede publicar cosechas
  #  When Despliega el menú de opciones del usuario
  #  Then No debe ver la opción de publicar cosecha
#