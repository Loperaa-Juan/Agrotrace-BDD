Feature: Filtros del catálogo de cosechas en AgroTrace

  Scenario: Filtrar cosechas por calidad A
    Given El negocio entra a la página de busqueda por filtros
    When filtra las cosechas por "calidad A"
    Then debería ver solo cosechas de calidad "A"
    And no debería ver la cosecha de calidad "B"

  Scenario: Filtrar cosechas por calidad B
    When filtra las cosechas por "calidad B"
    Then debería ver solo cosechas de calidad "B"
    And no debería ver la cosecha de calidad "A"

  Scenario: Buscar una cosecha por nombre del producto
    Given El negocio limpia los filtros de calidad
    When busca el último producto publicado por el agricultor con "calidad A"
    Then debería ver la cosecha de ese producto en los resultados

  Scenario: Búsqueda sin resultados
    When busca el producto "Producto inexistente"
    Then debería ver el mensaje "0 ofertas"

  Scenario: Filtrar cosechas por rango de precio
    Given el negocio limpia la búsqueda anterior
    When filtra las cosechas con precio hasta "4000" pesos
    Then debería ver solo cosechas con precio hasta "4000" pesos
    And no debería ver las cosechas publicadas en los resultados

  Scenario: Las cosechas con precio igual al límite sí aparecen
    When filtra las cosechas con precio hasta "5000" pesos
    Then debería ver las cosechas publicadas en los resultados
    And debería ver solo cosechas con precio hasta "5000" pesos

  Scenario: Combinar los filtros de calidad y precio
    When filtra las cosechas por "calidad B"
    Then debería ver solo cosechas de calidad "B"
    And no debería ver la cosecha de calidad "A"
    And debería ver solo cosechas con precio hasta "5000" pesos

  Scenario: Buscar con parte del nombre y en minúsculas
    Given el negocio limpia la búsqueda anterior
    When busca el producto "aguacate"
    Then debería ver las cosechas publicadas en los resultados