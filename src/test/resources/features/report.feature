Feature: Formulario de reporte de avistajes
  Como visitante de BlueSignal
  Quiero acceder al formulario de reporte
  Para registrar una observación de fauna marina

  Background:
    Given el visitante no se encuentra autenticado
    When accede a la página principal de BlueSignal
    And selecciona Mar del Plata como localidad del reporte
    And accede al formulario de reporte

  @smoke
  @report
  Scenario: Acceso exitoso al formulario de reporte
    Then el formulario de reporte se muestra correctamente

  @regression
  @report
  Scenario: Formulario vacío sin posibilidad de envío
    Then el botón de envío del reporte permanece deshabilitado

  @regression
  @report
  @data-table
  Scenario: Completar parcialmente el formulario con datos estructurados
    When el visitante completa los datos básicos del reporte
      | especie    | Ballena Franca Austral       |
      | cantidad   | 2                            |
      | distancia  | En el mar: cerca (< 1 km)   |
      | comentario | Avistaje automatizado de QA  |
    Then los datos básicos quedan cargados correctamente

  @regression
  @report
  @external-data
  Scenario: Completar parcialmente el formulario con datos externos
    When el visitante completa los datos básicos desde el archivo JSON
    Then los datos externos quedan cargados correctamente
