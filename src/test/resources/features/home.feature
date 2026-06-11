Feature: Página principal de BlueSignal
  Como visitante de la plataforma
  Quiero acceder a la página principal
  Para consultar información sobre avistajes de fauna marina

  Background:
    Given el visitante no se encuentra autenticado
    When accede a la página principal de BlueSignal

  @smoke
  @home
  Scenario: Acceso exitoso a la página principal
    Then la página principal se muestra correctamente

  @regression
  @home
  @map
  Scenario: Visualización del mapa principal
    Then el mapa principal de avistajes se muestra correctamente