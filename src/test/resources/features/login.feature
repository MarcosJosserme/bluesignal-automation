Feature: Autenticación de usuarios en BlueSignal
  Como visitante de la plataforma
  Quiero acceder a la pantalla de inicio de sesión
  Para utilizar las funciones de autenticación disponibles

  Background:
    Given el visitante accede a la página de inicio de sesión

  @smoke
  @login
  Scenario: Visualización de la página de inicio de sesión
    Then la página de inicio de sesión se muestra correctamente

  @regression
  @login
  Scenario: Visualización de los controles del formulario
    Then el formulario de inicio de sesión muestra todos sus controles

  @regression
  @login
  @negative
  Scenario: Inicio de sesión con credenciales inválidas
    When el visitante ingresa credenciales inválidas
    Then permanece en la página de inicio de sesión
    And visualiza un mensaje de error de autenticación